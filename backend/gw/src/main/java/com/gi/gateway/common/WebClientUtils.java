package com.gi.gateway.common;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class WebClientUtils {
    private final WebClient webClient;

    private static String CLASS_NAME;

    static {
        CLASS_NAME = WebClientUtils.class.getSimpleName();
    }

    /**
     * Auto-config post method for webclient
     * @param uri endpoint URI
     * @param headers request headers
     * @param request request body
     * @param requestClazz request body's class
     * @param <T> payload's generic type
     * @return ResponseSpec
     */
    public <T> WebClient.ResponseSpec post(String uri, Map<String, ?> headers, Object request, Class<T> requestClazz) {
        // URL setting
        var postSpec = webClient.post().uri(uri);

        // Headers setting
        headers.forEach((key, value) -> {
            postSpec.header(key, value.toString());
        });

        // Payload(RequestBody) setting
        postSpec.body(Mono.just(request), requestClazz);

        // Logging
        apiLogging(uri, headers, request, requestClazz, HttpMethod.POST);

        return postSpec.retrieve();
    }

    private <T> void apiLogging(String uri, Map<String, ?> headers, Object request, Class<T> requestClazz, HttpMethod httpMethod) {
        String preLogSignature = String.format("[%s - %s]", CLASS_NAME, httpMethod.name());

        // log uri
        log.debug("{} URI: {}", preLogSignature, uri);

        // log headers
        if (!headers.entrySet().isEmpty()) {
            log.debug("{} Headers: {}", preLogSignature, Utils.mapToString(headers));
        }

        // log requestBody
        if (Boolean.FALSE.equals(ObjectUtils.isEmpty(request))) {
            log.debug("{} Body: {}", preLogSignature, request);
        }
    }
}
