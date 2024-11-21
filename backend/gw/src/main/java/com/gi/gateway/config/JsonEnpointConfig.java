package com.gi.gateway.config;

import com.gi.gateway.common.Utils;
import com.gi.gateway.dto.RouterProperties;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class JsonEnpointConfig {
    /**
     * read all properties in router properties json file into a map
     *
     * @return the router properties map
     */
    @Bean
    public Map<String, RouterProperties> routerMappingProperties() {
        // open input stream for the resource
        try (InputStream inputStream = getClass().getResourceAsStream("/json/router-mapping.json")) {
            // init map result
            Map<String, RouterProperties> propertiesMap = new ConcurrentHashMap<>();

            // parse the json to map
            var jsonMap = Utils.parseJsonResourceToMap(Objects.requireNonNull(inputStream));

            // put the json properties into the map result
            jsonMap.forEach((key, value) -> {
                Map<String, Object> mapValue = (LinkedHashMap<String, Object>) value;
                propertiesMap.put(
                    key,
                    new RouterProperties(
                        mapValue.get("pattern"),
                        mapValue.get("host"),
                        mapValue.get("uri"),
                        mapValue.get("isLoadBalanced")));
            });

            return propertiesMap;
        } catch (IOException | NullPointerException e) {
            throw new BeanCreationException("Failed to load router-mapping properties", e);
        }
    }

    /**
     * read all properties in public endpoints json file into a list
     *
     * @return list contains all API endpoints opened to public
     */
    @Bean("publicApiEndpoints")
    public List<String> publicApiEndpoints() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/json/public-endpoints.json")) {
            Map<String, Object> endpointParserMapping = Utils.parseJsonResourceToMap(
                Objects.requireNonNull(inputStream));
            Object endpoints = endpointParserMapping.get("endpoints");
            return new ArrayList<>((Collection<String>) endpoints);
        } catch (IOException | NullPointerException e) {
            throw new BeanCreationException("Failed to load router-mapping properties", e);
        }
    }
}

