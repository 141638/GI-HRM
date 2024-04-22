package com.gihrm.sandservice.vuejsdemo.util;

import com.gihrm.sandservice.vuejsdemo.database.entity.MongoSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class MongoUtilService {
	private final ReactiveMongoOperations operation;

	public Mono<Integer> generateSequence(String seqName) {
		return operation.findAndModify(query(where("_id").is(seqName)),
		        new Update().inc("seq", 1), options().returnNew(true).upsert(true), MongoSequence.class)
		        .map(MongoSequence::getSeq);
	}
}
