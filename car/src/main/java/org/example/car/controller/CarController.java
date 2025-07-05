package org.example.car.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.car.controller.api.CarApi;
import org.example.car.models.CarReq;
import org.example.car.models.ListDataAttributes;
import org.example.car.models.ResultWithId;
import org.example.car.persistence.entity.Car;
import org.example.car.service.CarService;
import org.example.car.serviceExceptions.ServiceEntityExistRuntimeException;
import org.example.car.serviceExceptions.ServiceNotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class CarController implements CarApi {

    private final CarService service;
    Logger logger = LoggerFactory.getLogger(CarController.class);
    private final MeterRegistry meterRegistry;

    public CarController(CarService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<ResultWithId> create(CarReq req) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("CAR_CREATE: runId: '%s', req: '%s'", runId, req));
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
    public ResponseEntity<ListDataAttributes<List<Car>>> readCars() {
        UUID runId = UUID.randomUUID();
        ResponseEntity<ListDataAttributes<List<Car>>> result;
        try {
            logger.info(String.format("readCars: runId: '%s'", runId));
            ListDataAttributes<List<Car>> res = meterRegistry.timer("cars.time").record(() -> service.readCars(runId));
            result = ResponseEntity.ok(res);
        } catch (Exception e) {
            String err = String.format("readCars: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
        return result;
    }

    @Override
    public ResponseEntity<Car> read(UUID id) {
        UUID runId = UUID.randomUUID();
        ResponseEntity<Car> result;
        try {
            logger.info(String.format("CAR_READ: runId: '%s', id: '%s'", runId,  id));
            Car data = meterRegistry.timer("car.time").record(() ->service.read(id, runId));
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

    @Override
    public ResponseEntity<Car> readByRegNumber(String regNumber) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("CAR_REGNUMBER: runId: '%s', regnumber: '%s'", runId, regNumber));
            Car res = meterRegistry.timer("car_byregnumber.time").record(() -> service.readByRegNumber(regNumber, runId));
            return new ResponseEntity<>(res, HttpStatus.resolve(200));
        } catch (ServiceNotFoundRuntimeException e) {
            String err = String.format("readByRegNumber: ServiceNotFoundRuntimeException runId: '%s', regNumber: '%s', error: '%s'", runId, regNumber, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(404), String.format("runId: '%s'", runId));
        } catch (Exception e) {
            String err = String.format("readByRegNumber: Error runId: '%s', regNumber: '%s', error: '%s'", runId, regNumber, e);
            logger.error(err);
            throw e;
        }
    }
}
