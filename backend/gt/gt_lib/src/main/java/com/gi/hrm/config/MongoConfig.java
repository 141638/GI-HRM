package com.gi.hrm.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

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
        // Get credentials
        final String username = env.getProperty("spring.data.mongodb.username");
        final String authDb = env.getProperty("spring.data.mongodb.authentication-database");
        final String password = env.getProperty("spring.data.mongodb.password");
        assert username != null;
        assert authDb != null;
        assert password != null;
        var mongoCredential = MongoCredential.createCredential(username, authDb, password.toCharArray());

        // Get connection
        final String host = env.getProperty("spring.data.mongodb.host");
        final String port = env.getProperty("spring.data.mongodb.port");
        final String database = env.getProperty("spring.data.mongodb.database");
        String conn = String.format("mongodb://%s:%s/%s", host, port, database);

        // Build & create MongoClients
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(conn))
                .credential(mongoCredential)
                .build());
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
