package org.example.rateproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.example.rateproject.service.RateLimitingService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    private RateLimitingService rateLimitingService;

    @GetMapping("/api")
    public ResponseEntity<Map<String, Object>> limitedEndpoint(@RequestParam String userId) {
        Map<String, Object> response = new HashMap<>();

        if (rateLimitingService.isRequestAllowed(userId)) {
            response.put("message", "Request allowed.");
            response.put("remainingRequests", rateLimitingService.getRemainingRequests(userId));
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Too many requests. Try again later.");
            response.put("remainingRequests", 0);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(response);
        }
    }
}
