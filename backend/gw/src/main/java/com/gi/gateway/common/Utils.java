package com.gi.gateway.common;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@Component
public class Utils {

    public static Map<String, Object> getJsonEndpoints(){
        Resource jsonResource = new ClassPathResource(JsonEnpointKeyMapping.RESOURCE_ENDPOINTS_JSON);
        return parseJsonResourceToMap(jsonResource);
    }
    public static Map<String, Object> parseJsonResourceToMap(Resource jsonResource) {
        Map<String, Object> jsonMapping = Collections.emptyMap();
        try {
            String source = new String(Files.readAllBytes(jsonResource.getFile().toPath()));
            JsonParser jParser = JsonParserFactory.getJsonParser();
            jsonMapping = jParser.parseMap(source);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (JsonParseException e) {
            log.error(e.getMessage());
        }
        return jsonMapping;
    }

    ;
}
