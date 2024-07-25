package org.example.rateproject.service;

import org.springframework.stereotype.Service;


public interface RateLimitingService {

    boolean isRequestAllowed(String userId);

    int getRemainingRequests(String userId);
}
