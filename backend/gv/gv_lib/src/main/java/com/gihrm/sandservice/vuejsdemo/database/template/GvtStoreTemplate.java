package com.gihrm.sandservice.vuejsdemo.database.template;

import com.gihrm.sandservice.vuejsdemo.database.entity.GvtStoreEntity;
import com.gihrm.sandservice.vuejsdemo.dto.response.GvtStoreListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class GvtStoreTemplate {
    private final ReactiveMongoTemplate mongoTemplate;

    public Flux<GvtStoreListResponseDto> search() {
        ProjectionOperation projectStage = Aggregation.project();
        projectStage = projectStage.and("id").as("id");
        projectStage = projectStage.and("name").as("name");
        projectStage = projectStage.and("description").as("description");
        projectStage = projectStage.and("contactNumber").as("contactNumber");
        projectStage = projectStage.and("emailAddress").as("emailAddress");
        projectStage = projectStage.and("openTime").as("openTime");
        projectStage = projectStage.and("closeTime").as("closeTime");
        projectStage = projectStage.and("status").as("status");

        var aggPipeLine = Aggregation.newAggregation(GvtStoreEntity.class, projectStage);
        return mongoTemplate.aggregate(aggPipeLine, GvtStoreListResponseDto.class);
    }
}
