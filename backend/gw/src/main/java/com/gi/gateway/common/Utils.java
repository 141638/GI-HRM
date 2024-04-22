package com.gi.gateway.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    public static final String RESOURCE_ENDPOINTS_JSON = "json/router-mapping.json";

    public static Map<String, Object> getJsonEndpoints() {
        Resource jsonResource = new ClassPathResource(RESOURCE_ENDPOINTS_JSON);
        return parseJsonResourceToMap(jsonResource);
    }

    public static Map<String, Object> parseJsonResourceToMap(Resource jsonResource) {
        Map<String, Object> jsonMapping = Collections.emptyMap();
        try {
            String source = new String(Files.readAllBytes(jsonResource.getFile().toPath()));
            JsonParser jParser = JsonParserFactory.getJsonParser();
            jsonMapping = jParser.parseMap(source);
        } catch (IOException | JsonParseException e) {
            log.error(e.getMessage());
        }
        return jsonMapping;
    }

    public static Map<String, Object> parseJsonResourceToMap(InputStream jsonResource) {
        Map<String, Object> jsonMapping = Collections.emptyMap();
        try {
            String source = new String(jsonResource.readAllBytes());
            JsonParser jParser = JsonParserFactory.getJsonParser();
            jsonMapping = jParser.parseMap(source);
        } catch (IOException | JsonParseException e) {
            log.error(e.getMessage());
        }
        return jsonMapping;
    }

    public static <T> T jsonObjectMapper(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("[utils-exception] jsonObjectMapper error: {}", e.getMessage());
        }
        return null;
    }

    public static <T, Y> String mapToString(Map<T, Y> map) {
        return map.entrySet().stream().map(tyEntry -> tyEntry.getKey() + "=" + tyEntry.getValue())
                .collect(Collectors.joining(",", "[", "]"));
    }
}
