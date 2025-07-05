package org.example.oil.api;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.example.oil.models.OilReq;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RpmLimiter {

    private final RpsLimiter rpsLimiter;

    public RpmLimiter(RpsLimiter rpsLimiter) {
        this.rpsLimiter = rpsLimiter;
    }

    @RateLimiter(name = "rpm")
    public UUID add(OilReq req, UUID runId){
        return rpsLimiter.add(req, runId);
    }
}

