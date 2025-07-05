package org.example.oil.api;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.example.oil.models.OilReq;
import org.example.oil.service.OilService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RpsLimiter {

    private final OilService service;

    public RpsLimiter(OilService service) {
        this.service = service;
    }

    @RateLimiter(name = "rps")
    public UUID add(OilReq req, UUID runId){
        return service.add(req, runId);
    }
}

