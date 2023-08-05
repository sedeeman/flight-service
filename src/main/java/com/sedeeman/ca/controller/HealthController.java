package com.sedeeman.ca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthIndicator {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @GetMapping("/health")
    public String checkHealth() {
        Health health = health();
        if (health.getStatus().equals(Status.UP)) {
            logger.info("Application is up and running!");
            return "Application is up and running!";
        } else {
            logger.error("Application is down!");
            return "Application is down!";
        }
    }

    @Override
    public Health health() {
        try {
            return Health.up().build();
        } catch (Exception e) {
            logger.error(String.format("Error checking health: %s",e.getMessage()));
            return Health.down().build();
        }
    }
}
