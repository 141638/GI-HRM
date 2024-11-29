package com.gi.hrm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;


@Configuration
public class MapperConfig {
    /** config bean name for object mapper */
    public static final String OBJECT_MAPPER = "objectMapper";
    /** config bean name for object to json mapper */
    public static final String OBJECT_TO_JSON_MAPPER = "objectToJsonMapper";

    /**
     * Default object mapper for system
     *
     * @return configured default object mapper
     */
    @Bean(OBJECT_MAPPER)
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateSerializer(false, null));
        module.addDeserializer(Date.class, new DateDeserializers.DateDeserializer());

        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(module)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    /**
     * Object mapper for JSON mapping
     *
     * @return configured JSON-mapping object mapper
     */
    @Bean(OBJECT_TO_JSON_MAPPER)
    public ObjectMapper objectToJsonMapper() {
        ObjectMapper jsonMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        jsonMapper.registerModule(module);
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        jsonMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE);
        return jsonMapper;
    }
}
