package org.example.rateproject.entity;

public class UserRequestInfo {

    private int requestCount;
    private long lastRequestTime;

    public UserRequestInfo(int requestCount, long lastRequestTime) {
        this.requestCount = requestCount;
        this.lastRequestTime = lastRequestTime;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public long getLastRequestTime() {
        return lastRequestTime;
    }

    public void incrementRequestCount() {
        this.requestCount++;
    }

    public void setLastRequestTime(long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

}
