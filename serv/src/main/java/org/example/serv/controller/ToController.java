package org.example.serv.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.serv.controller.api.ToApi;
import org.example.serv.models.ListDataAttributes;
import org.example.serv.models.ResultWithId;
import org.example.serv.models.ToReq;
import org.example.serv.persistence.entity.To;
import org.example.serv.service.ToService;
import org.example.serv.serviceExceptions.ServiceEntityExistRuntimeException;
import org.example.serv.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class ToController implements ToApi {

    private final ToService service;
    Logger logger = LoggerFactory.getLogger(ToController.class);
    private final MeterRegistry meterRegistry;

    public ToController(ToService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<ResultWithId> create(ToReq req) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("TO_CREATE: runId: '%s', req: '%s'", runId, req));
            UUID res = meterRegistry.timer("item_create.time").record(() -> service.add(req, runId));
            return new ResponseEntity<>(new ResultWithId(res), HttpStatus.resolve(201));
        } catch (ServiceNotFoundRuntimeException e) {
            String err = String.format("create: ServiceNotFoundRuntimeException runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(422), String.format("runId: '%s'", runId));
        } catch (ServiceEntityExistRuntimeException e) {
            String err = String.format("create: ServiceEntityExistRuntimeException runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(422), String.format("Details: '%s' ", e.getMessage()));
        } catch (Exception e) {
            String err = String.format("create: Error runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.error(err);
            throw e;
        }
    }

    @Override
    public ResponseEntity<ListDataAttributes<List<To>>> readTos() {
        UUID runId = UUID.randomUUID();
        ResponseEntity<ListDataAttributes<List<To>>> result;
        try {
            logger.info(String.format("readTos: runId: '%s'", runId));
            ListDataAttributes<List<To>> res = meterRegistry.timer("tos.time").record(() -> service.readTos(runId));
            result = ResponseEntity.ok(res);
        } catch (Exception e) {
            String err = String.format("readTos: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
        return result;
    }

    @Override
    public ResponseEntity<To> read(UUID id) {
        UUID runId = UUID.randomUUID();
        ResponseEntity<To> result;
        try {
            logger.info(String.format("TO_READ: runId: '%s', id: '%s'", runId,  id));
            To data = meterRegistry.timer("oil.time").record(() ->service.readToById(id, runId));
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
