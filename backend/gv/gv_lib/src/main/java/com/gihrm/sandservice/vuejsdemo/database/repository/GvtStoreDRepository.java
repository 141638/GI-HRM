package com.gihrm.sandservice.vuejsdemo.database.repository;

import com.gihrm.sandservice.vuejsdemo.database.entity.GvtStoreDEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GvtStoreDRepository extends ReactiveMongoRepository<GvtStoreDEntity, Integer> {
}
