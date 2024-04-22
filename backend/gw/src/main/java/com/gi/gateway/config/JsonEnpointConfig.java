package com.gi.gateway.config;

import com.gi.gateway.common.Utils;
import com.gi.gateway.dto.RouterProperties;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class JsonEnpointConfig {
    /**
     * resource contains router mapping properties
     */
    @Value("classpath:json/router-mapping.json")
    private Resource routerMappingResource;


    /**
     * resource contains public api endpoints
     */
    @Value("classpath:json/public-endpoints.json")
    private Resource publicAPIsResource;

    /**
     * read all properties in router properties json file into a map
     *
     * @return the router properties map
     */
    @Bean
    public Map<String, RouterProperties> routerMappingProperties() {
        // open input stream for the resource
        try (var resourceInputStream = routerMappingResource.getInputStream()) {
            // init map result
            Map<String, RouterProperties> propertiesMap = new ConcurrentHashMap<>();

            // parse the json to map
            var jsonMap = Utils.parseJsonResourceToMap(resourceInputStream);

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
        } catch (IOException ioe) {
            throw new BeanCreationException("Failed to load router-mapping properties", ioe);
        }
    }

    /**
     * read all properties in public endpoints json file into a list
     *
     * @return list contains all API endpoints opened to public
     */
    @Bean("publicApiEndpoints")
    public List<String> publicApiEndpoints() {
        Map<String, Object> endpointParserMapping = Utils.parseJsonResourceToMap(publicAPIsResource);
        Object endpoints = endpointParserMapping.get("endpoints");
        return new ArrayList<>((Collection<String>) endpoints);
    }
}
