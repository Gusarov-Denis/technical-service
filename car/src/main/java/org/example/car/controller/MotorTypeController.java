package org.example.car.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.car.controller.api.MotorTypeApi;
import org.example.car.models.ListDataAttributes;
import org.example.car.persistence.entity.MotorType;
import org.example.car.service.MotorTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class MotorTypeController implements MotorTypeApi {

    private final MotorTypeService service;
    Logger logger = LoggerFactory.getLogger(MotorTypeController.class);
    private final MeterRegistry meterRegistry;

    public MotorTypeController(MotorTypeService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<ListDataAttributes<List<MotorType>>> readMotorTypes() {
        UUID runId = UUID.randomUUID();
        ResponseEntity<ListDataAttributes<List<MotorType>>> result;
        try {
            logger.info(String.format("readMotorTypes: runId: '%s'", runId));
            ListDataAttributes<List<MotorType>> res = meterRegistry.timer("motor_types.time").record(() -> service.readMotorTypes(runId));
            result = ResponseEntity.ok(res);
        } catch (Exception e) {
            String err = String.format("readMotorTypes: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
        return result;
    }
}
