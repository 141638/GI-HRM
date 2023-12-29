package com.gi.hrm.repository.template;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.gi.hrm.exception.BadRequestException;
import com.gi.hrm.exception.ObjectFieldsMismatchException;

public class CommonTemplateOperation {
	private static final String REGEX_LIKE = ".*%s.*";
	protected final ReactiveMongoTemplate mongoTemplate;
	protected Query query;

	public CommonTemplateOperation(ReactiveMongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
	}

	protected void initQuery() {
		this.query = new Query();
	}

	/**
	 * Chaining all others individual criteria into one final criteria. Auto dismiss
	 * null value criteria to the prebuilt query
	 * 
	 * @param criteriaArrays arrays of criteria that need to chain
	 * @return the chained criteria
	 * @author HieuHT
	 */
	protected Criteria criteriaBuilder(Criteria... criteriaArrays) {
		Criteria criteria = new Criteria();
		Stream.of(criteriaArrays).filter(Objects::nonNull).forEach(criteria::andOperator);
		return criteria;
	}

	/**
	 * Create custom criteria that query string 'like'. Auto add the created
	 * criteria to the prebuilt query
	 * 
	 * @param field       fieldName
	 * @param value       value to query
	 * @param caseSensity compare with case sensitivity or not
	 * 
	 * @author HieuHT
	 */
	protected void addCriteriaWhereLike(String field, String value, boolean caseSensity) {
		if (StringUtils.hasText(value)) {
			if (caseSensity) {
				this.query.addCriteria(Criteria.where(field).regex(String.format(REGEX_LIKE, value)));
			} else {
				this.query.addCriteria(Criteria.where(field).regex(String.format(REGEX_LIKE, value), "i"));
			}
		}
	}

	/**
	 * Create custom criteria that query string 'like'. Return the created criteria
	 * for further modification
	 * 
	 * @param field       fieldName
	 * @param value       value to query
	 * @param caseSensity compare with case sensitivity or not
	 * @return criteria value if value has text, null otherwise
	 * @author HieuHT
	 */
	protected Criteria createCriteriaWhereLike(String field, String value, boolean caseSensity) {
		if (StringUtils.hasText(value)) {
			if (caseSensity) {
				return Criteria.where(field).regex(String.format(REGEX_LIKE, value));
			} else {
				return Criteria.where(field).regex(String.format(REGEX_LIKE, value), "i");
			}
		}
		return null;
	}

	/**
	 * Create custom criteria that query object with equal operator(==). Auto add
	 * the created criteria to the prebuilt query
	 * 
	 * @param field fieldName
	 * @param value value to query
	 * @param clazz value class type
	 * 
	 * @author HieuHT
	 */
	protected void addCriteriaWhereEqual(String field, String value, Class<?> clazz) {
		if (Objects.nonNull(value)) {
			Assert.notNull(clazz, "Convert class cannot be null");
			this.query.addCriteria(Criteria.where(field).is(convertObjectClass(value, clazz)));
		}
	}

	/**
	 * Create custom criteria that query object with equal operator(==). return the
	 * created criteria for further modification
	 * 
	 * @param field fieldName
	 * @param value value to query
	 * @param clazz value class type
	 * @return criteria value if value is present, null otherwise
	 * 
	 * @author HieuHT
	 */
	protected Criteria createCriteriaWhereEqual(String field, String value, Class<?> clazz) {
		if (Objects.nonNull(value)) {
			Assert.notNull(clazz, "Convert class cannot be null");
			return Criteria.where(field).is(convertObjectClass(value, clazz));
		}
		return null;
	}

	/**
	 * Common customed for create match aggregation to avoid boilerplate code
	 * 
	 * @param criteria array of criterias need to match
	 * @return final match operation
	 * @author HieuHT
	 */
	protected MatchOperation matchTemplateBuild(Criteria... criteria) {
		var criterias = criteriaBuilder(criteria);
		return Aggregation.match(criterias);
	}

	/**
	 * Common customed for create projection aggregation to avoid boilerplate code
	 * 
	 * @param projectionArray         project fields using their field name
	 * @param projectionExtendMapping project fields using custom name istead, or
	 *                                project fields using other aggregation
	 *                                expression
	 * @return projectStage final projection
	 * 
	 * @exception BadRequestException projectionExtendMapping only accept 2 type of
	 *                                class, String and ArrayOperators.
	 * @author HieuHT
	 */
	protected ProjectionOperation projectionTemplateBuild(String[] projectionArray,
	        Map<String, Object> projectionExtendMapping) {
		Assert.isTrue(projectionArray != null || Objects.nonNull(projectionExtendMapping),
		        "Must have value to project");
		ProjectionOperation projectStage = Aggregation.project();
		if (projectionArray != null && projectionArray.length > 0) {
			projectStage = Aggregation.project(projectionArray);
		}
		if (Objects.nonNull(projectionExtendMapping)) {
			for (var entry : projectionExtendMapping.entrySet()) {
				var value = entry.getValue();
				if (value instanceof String fieldName) {
					projectStage = projectStage.and(fieldName).as(entry.getKey());
				} else if (value instanceof AggregationExpression aggExp) {
					projectStage = projectStage.and(aggExp).as(entry.getKey());
				} else {
					throw new BadRequestException("Not Supported Type", 001);
				}
			}
		}
		return projectStage;
	}

	protected ProjectionOperation projectionTemplateBuild(Map<String, Object> projectionExtendMapping) {
		return projectionTemplateBuild(null, projectionExtendMapping);
	}

	protected ProjectionOperation projectionTemplateBuild(String[] projectionArray) {
		return projectionTemplateBuild(projectionArray, null);
	}

	/**
	 * Create custom criteria that query object with equal operator(==). return the
	 * created criteria for further modification
	 * 
	 * @param value value to convert
	 * @param clazz value class type
	 * @return converted value by clazz
	 * 
	 * @author HieuHT
	 */
	private <T> T convertObjectClass(String value, Class<T> clazz) {
		try {
			return clazz.getConstructor(String.class).newInstance(value);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
		        | NoSuchMethodException | SecurityException e) {
			throw new ObjectFieldsMismatchException();
		}
	}
}
