package org.example.rateproject.service;

import org.example.rateproject.entity.UserRequestInfo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingServiceImpl  implements RateLimitingService{
    private final int MAX_REQUESTS = 10;
    private final long TIME_WINDOW = 2 * 60 * 60 * 1000; // 2 hours in milliseconds

    private final Map<String, UserRequestInfo> requestCounts = new ConcurrentHashMap<>();

    public boolean isRequestAllowed(String userId) {
        long currentTime = Instant.now().toEpochMilli();

        UserRequestInfo userInfo = requestCounts.getOrDefault(userId, new UserRequestInfo(0, currentTime));

        if (currentTime - userInfo.getLastRequestTime() > TIME_WINDOW) {
            userInfo = new UserRequestInfo(0, currentTime);
        }

        if (userInfo.getRequestCount() >= MAX_REQUESTS) {
            return false;
        }

        userInfo.incrementRequestCount();
        userInfo.setLastRequestTime(currentTime);
        requestCounts.put(userId, userInfo);
        return true;
    }

    public int getRemainingRequests(String userId) {
        long currentTime = Instant.now().toEpochMilli();

        UserRequestInfo userInfo = requestCounts.getOrDefault(userId, new UserRequestInfo(0, currentTime));

        if (currentTime - userInfo.getLastRequestTime() > TIME_WINDOW) {
            userInfo = new UserRequestInfo(0, currentTime);
            requestCounts.put(userId, userInfo);
        }

        return MAX_REQUESTS - userInfo.getRequestCount();
    }

}
