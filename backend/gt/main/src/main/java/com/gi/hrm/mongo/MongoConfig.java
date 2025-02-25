package com.gi.hrm.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Optional;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.gi.hrm.*.infrastructure")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    private static final String ENV_MONGO_HOST = "spring.data.mongodb.host";
    private static final String ENV_MONGO_PORT = "spring.data.mongodb.port";
    private static final String ENV_MONGO_DB = "spring.data.mongodb.database";
    private static final String ENV_MONGO_AUTH_DB = "spring.data.mongodb.authentication-database";
    private static final String ENV_MONGO_USERNAME = "spring.data.mongodb.username";
    private static final String ENV_MONGO_PASSWORD = "spring.data.mongodb.password";
    public static final String BEAN_NAME_MONGO_DATABASE = "mongoDatabase";
    public static final String BEAN_NAME_MONGO_TEMPLATE = "reactiveMongoTemplate";
    public static final String BEAN_NAME_MONGO_TRANSACTION_MANAGER = "reactiveMongoTransactionManager";

    private final Environment env;

    public MongoConfig(Environment env) {
        super();
        this.env = env;
    }

    @Override
    @NonNull
    public MongoClient reactiveMongoClient() {
        // Get credentials
        final String authDb = env.getProperty(ENV_MONGO_AUTH_DB);
        final String username = env.getProperty(ENV_MONGO_USERNAME);
        final String password = env.getProperty(ENV_MONGO_PASSWORD);
        assert username != null;
        assert authDb != null;
        assert password != null;
        var mongoCredential = MongoCredential.createCredential(username, authDb, password.toCharArray());

        // Get connection
        final String host = env.getProperty(ENV_MONGO_HOST);
        final String port = env.getProperty(ENV_MONGO_PORT);
        final String database = getDatabaseName();
        String conn = String.format("mongodb://%s:%s/%s", host, port, database);

        // Build & create MongoClients
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(conn))
                .credential(mongoCredential)
                .build());
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        return Optional.ofNullable(env.getProperty(ENV_MONGO_DB))
                .orElseThrow(() -> new IllegalStateException(ENV_MONGO_DB));
    }

    @Bean(BEAN_NAME_MONGO_DATABASE)
    MongoDatabase getMongoDatabase() {
        try (MongoClient mongoClient = reactiveMongoClient()) {
            return mongoClient.getDatabase(getDatabaseName());
        } catch (Exception e) {
            throw new IllegalStateException("Failed retrieve mongo database", e);
        }
    }

    @Bean(BEAN_NAME_MONGO_TEMPLATE)
    ReactiveMongoTemplate mongoTemplate() {
        return new ReactiveMongoTemplate(this.reactiveMongoClient(), this.getDatabaseName());
    }

    @Bean(BEAN_NAME_MONGO_TRANSACTION_MANAGER)
    ReactiveMongoTransactionManager transactionManager(ReactiveMongoDatabaseFactory transactionManagerFactory) {
        return new ReactiveMongoTransactionManager(transactionManagerFactory);
    }
}
