package com.starter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Health check controller with liveness and readiness probes.
 */
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    /**
     * Liveness probe endpoint.
     * Indicates whether the application is running.
     * 
     * Note: This is a basic implementation. In production, enhance this to include
     * actual health checks for critical dependencies (e.g., database connections,
     * message queues, external services).
     * 
     * @return liveness status
     */
    @GetMapping("/health/liveness")
    public Map<String, Object> liveness() {
        return createHealthResponse("UP", "Application is alive");
    }

    /**
     * Readiness probe endpoint.
     * Indicates whether the application is ready to accept traffic.
     * 
     * Note: This is a basic implementation. In production, enhance this to verify
     * that the application can handle traffic by checking database connections,
     * external services, cache availability, etc.
     * 
     * @return readiness status
     */
    @GetMapping("/health/readiness")
    public Map<String, Object> readiness() {
        return createHealthResponse("UP", "Application is ready");
    }

    /**
     * Helper method to create health response.
     * 
     * @param status health status
     * @param message health message
     * @return health response map
     */
    private Map<String, Object> createHealthResponse(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return response;
    }
}
