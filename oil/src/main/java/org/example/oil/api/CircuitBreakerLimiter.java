package org.example.oil.api;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.oil.models.OilReq;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CircuitBreakerLimiter {

    private final RpmLimiter rpmLimiter;

    public CircuitBreakerLimiter(RpmLimiter rpmLimiter) {
        this.rpmLimiter = rpmLimiter;
    }

    @CircuitBreaker(name = "default")
    public UUID add(OilReq req, UUID runId){
        return rpmLimiter.add(req, runId);
    }
}

