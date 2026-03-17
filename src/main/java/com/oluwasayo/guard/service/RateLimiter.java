package com.oluwasayo.guard.service;


import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class RateLimiter {

    private final Map<String, Queue<Long>> requestMap = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 5;
    private static final long TIME_WINDOW_MS = 60000L;

    public boolean isAllowed(String ip) {
        long now = System.currentTimeMillis();
        Queue<Long> requests = requestMap.computeIfAbsent(ip, k -> new ConcurrentLinkedQueue<>());

        while(!requests.isEmpty() && (now - requests.peek()) > TIME_WINDOW_MS) {
            requests.poll();
        }

        if(requests.size() == MAX_REQUESTS) return false;
        requests.offer(now);
        return true;
    }
}
