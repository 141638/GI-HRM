package com.gihrm.sandservice.vuejsdemo.database.repository;

import com.gihrm.sandservice.vuejsdemo.database.entity.GvtStoreEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GvtStoreRepository extends ReactiveMongoRepository<GvtStoreEntity, Integer> {
    Mono<GvtStoreEntity> findByIdAndDeleteFlagFalse(Integer id);
}
