package org.example.client.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.client.controller.api.UserApi;
import org.example.client.models.ResultWithId;
import org.example.client.models.UserReq;
import org.example.client.service.UserService;
import org.example.client.serviceExceptions.ServiceEntityExistRuntimeException;
import org.example.client.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class UserController implements UserApi {

    private final UserService service;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final MeterRegistry meterRegistry;

    public UserController(UserService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<ResultWithId> create(UserReq req) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("USER_CREATE: runId: '%s', req: '%s'", runId, req));
            UUID res = meterRegistry.timer("item_create.time").record(() -> service.add(req, runId));
            return new ResponseEntity<>(new ResultWithId(res), HttpStatus.resolve(201));
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
    public ResponseEntity<String> readEmailByLogin(String login) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("USER_EMAIL: runId: '%s', login: '%s'", runId, login));
            String res = meterRegistry.timer("user_bylogin.time").record(() -> service.readEmailByLogin(login, runId));
            return new ResponseEntity<>(res, HttpStatus.resolve(200));
        } catch (ServiceNotFoundRuntimeException e) {
            String err = String.format("readEmailByLogin: ServiceNotFoundRuntimeException runId: '%s', login: '%s', error: '%s'", runId, login, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(404), String.format("runId: '%s'", runId));
        } catch (Exception e) {
            String err = String.format("readEmailByLogin: Error runId: '%s', login: '%s', error: '%s'", runId, login, e);
            logger.error(err);
            throw e;
        }
    }
}
