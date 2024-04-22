package com.gihrm.sandservice.vuejsdemo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.gihrm.sandservice.vuejsdemo.database")
public class MongoConfig extends AbstractReactiveMongoConfiguration {
    private final Environment env;

    @Autowired
    public MongoConfig(Environment env) {
        super();
        this.env = env;
    }

    @NonNull
    @Override
    public MongoClient reactiveMongoClient() {
        // credentials
        final String username = env.getProperty("spring.data.mongodb.username");
        assert username != null;
        final String authDb = env.getProperty("spring.data.mongodb.authentication-database");
        assert authDb != null;
        final String password = env.getProperty("spring.data.mongodb.password");
        assert password != null;

        var mongoCredentials = MongoCredential.createCredential(username, authDb, password.toCharArray());

        // connection
        final String host = env.getProperty("spring.data.mongodb.host");
        final String port = env.getProperty("spring.data.mongodb.port");
        final String database = env.getProperty("spring.data.mongodb.database");
        String conn = String.format("mongodb://%s:%s/%s", host, port, database);

        // build mongoClient
        return MongoClients.create(
            MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(conn))
                .credential(mongoCredentials).build()
        );
    }

    @NonNull
    @Override
    protected String getDatabaseName() {
        return Objects.requireNonNull(env.getProperty("spring.data.mongodb.database"));
    }

    @Bean("mongoDatabase")
    MongoDatabase getMongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(getDatabaseName());
    }

    @Bean("reactiveMongoTemplate")
    ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(this.reactiveMongoClient(), this.getDatabaseName());
    }
}
