package org.example.oil.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.oil.api.CircuitBreakerLimiter;
import org.example.oil.controller.api.OilApi;
import org.example.oil.models.ListDataAttributes;
import org.example.oil.models.OilReq;
import org.example.oil.models.ResultWithId;
import org.example.oil.persistence.entity.Oil;
import org.example.oil.service.OilService;
import org.example.oil.serviceExceptions.ServiceEntityExistRuntimeException;
import org.example.oil.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class OilController implements OilApi {

    private final OilService service;
    Logger logger = LoggerFactory.getLogger(OilController.class);
    private final MeterRegistry meterRegistry;
    private final CircuitBreakerLimiter circuitBreakerLimiter;

    public OilController(OilService service, MeterRegistry meterRegistry, CircuitBreakerLimiter circuitBreakerLimiter) {
        this.service = service;
        this.meterRegistry = meterRegistry;
        this.circuitBreakerLimiter = circuitBreakerLimiter;
    }

    @Override
    public ResponseEntity<ResultWithId> create(OilReq req) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("OIL_CREATE: runId: '%s', req: '%s'", runId, req));
            UUID res = meterRegistry.timer("item_create.time").record(() -> circuitBreakerLimiter.add(req, runId));
            return new ResponseEntity<>(new ResultWithId(res), HttpStatus.resolve(201));
        } catch (ServiceNotFoundRuntimeException e) {
            String err = String.format("create: ServiceNotFoundRuntimeException runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(422), String.format("runId: '%s'", runId));
        } catch (ServiceEntityExistRuntimeException e) {
            String err = String.format("create: ServiceEntityExistRuntimeException runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(422), String.format("Details: '%s' ", e.getMessage()));
        } catch (CallNotPermittedException e) {
            String err = String.format("create: CallNotPermittedException runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(500), String.format("Details: '%s' ", e.getMessage()));
        } catch (RequestNotPermitted e) {
            String err = String.format("create: RequestNotPermitted runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(500), String.format("Details: '%s' ", e.getMessage()));
        } catch (Exception e) {
            String err = String.format("create: Error runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.error(err);
            throw e;
        }
    }

    @Override
    public ResponseEntity<ListDataAttributes<List<Oil>>> readOils() {
        UUID runId = UUID.randomUUID();
        ResponseEntity<ListDataAttributes<List<Oil>>> result;
        try {
            logger.info(String.format("readOils: runId: '%s'", runId));
            ListDataAttributes<List<Oil>> res = meterRegistry.timer("oils.time").record(() -> service.readOils(runId));
            result = ResponseEntity.ok(res);
        } catch (Exception e) {
            String err = String.format("readOils: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
        return result;
    }

    @Override
    public ResponseEntity<Oil> read(UUID id) {
        UUID runId = UUID.randomUUID();
        ResponseEntity<Oil> result;
        try {
            logger.info(String.format("OIL_READ: runId: '%s', id: '%s'", runId,  id));
            Oil data = meterRegistry.timer("oil.time").record(() ->service.read(id, runId));
            result = ResponseEntity.ok(data);
        } catch (ServiceNotFoundRuntimeException e) {
            String err = String.format("read: ServiceNotFoundRuntimeException runId: '%s', id: '%s', error: '%s'", runId, id, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(404), String.format("runId: '%s'", runId));
        } catch (Exception e) {
            String err = String.format("read: Error runId: '%s', id: '%s', error: '%s'", runId, id, e);
            logger.error(err);
            throw e;
        }
        return result;
    }
}
