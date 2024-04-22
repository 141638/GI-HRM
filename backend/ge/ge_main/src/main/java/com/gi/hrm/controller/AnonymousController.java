package com.gi.hrm.controller;

import com.gi.hrm.service.AnonymousService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
public class AnonymousController {
    /** service for handling anonymous api request */
    private final AnonymousService anonymousService;

    @PostMapping(value = "/kafka/test-topic")
    public ResponseEntity<?> kafkaTestTopicSender(
        @RequestPart(required = false, name = "key") String key,
        @RequestPart(required = false, name = "message") String message
    ) {
        try {
            anonymousService.kafkaTestTopicSender(key, message);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
