package com.gi.hrm.service.mongo;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.gi.hrm.common.entity.CommonEntityProperties;
import com.gi.hrm.entity.MongoSequence;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MongoUtilService {
	private ReactiveMongoOperations operation;

	public Mono<Integer> generateSequence(String seqName) {
		return operation.findAndModify(query(where(CommonEntityProperties.FIELD_ID).is(seqName)),
		        new Update().inc("seq", 1), options().returnNew(true).upsert(true), MongoSequence.class)
		        .map(MongoSequence::getSeq);
	}
}
