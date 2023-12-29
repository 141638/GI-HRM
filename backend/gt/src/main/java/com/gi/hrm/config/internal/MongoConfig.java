package com.gi.hrm.config.internal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.gi.hrm.repository")
public class MongoConfig extends AbstractReactiveMongoConfiguration {
	private final Environment env;

	public MongoConfig(Environment env) {
		super();
		this.env = env;
	}

	@Override
	public MongoClient reactiveMongoClient() {
		return MongoClients.create();
	}

	@Override
	protected String getDatabaseName() {
		return env.getProperty("spring.data.mongodb.database");
	}

	@Bean("mongoDatabase")
	MongoDatabase getMongoDatabase() {
		return reactiveMongoClient().getDatabase(getDatabaseName());
	}

	@Bean("reactiveMongoTemplate")
	ReactiveMongoTemplate mongoTemplate() {
		return new ReactiveMongoTemplate(this.reactiveMongoClient(), this.getDatabaseName());
	}
}
