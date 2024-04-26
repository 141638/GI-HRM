package com.gihrm.sandservice.vuejsdemo.database.template;

import com.gihrm.sandservice.vuejsdemo.database.entity.GvtStoreEntity;
import com.gihrm.sandservice.vuejsdemo.dto.response.GvtStoreListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class GvtStoreTemplate {
    private final ReactiveMongoTemplate mongoTemplate;

    public Flux<GvtStoreListResponseDto> search(String key, String status) {
        Criteria conditions = Criteria.where("deleteFlag").is(false);
        if(StringUtils.hasText(key)){
            conditions = conditions.and("name").regex(".*" + key.strip() + ".*", "i");
        }
        if(StringUtils.hasText(status) && !status.equals("0")){
            conditions = conditions.and("status").is(Integer.parseInt(status));
        }
        MatchOperation matchStage = Aggregation.match(conditions);

        ProjectionOperation projectStage = Aggregation.project();
        projectStage = projectStage.and("id").as("id");
        projectStage = projectStage.and("name").as("name");
        projectStage = projectStage.and("description").as("description");
        projectStage = projectStage.and("contactNumber").as("contactNumber");
        projectStage = projectStage.and("emailAddress").as("emailAddress");
        projectStage = projectStage.and("openTime").as("openTime");
        projectStage = projectStage.and("closeTime").as("closeTime");
        projectStage = projectStage.and("status").as("status");

        var aggPipeLine = Aggregation.newAggregation(GvtStoreEntity.class, matchStage, projectStage);
        return mongoTemplate.aggregate(aggPipeLine, GvtStoreListResponseDto.class);
    }
}
