package org.example.car.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.car.controller.api.MarkModelApi;
import org.example.car.models.ListDataAttributes;
import org.example.car.persistence.entity.MarkModel;
import org.example.car.service.MarkModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class MarkModelController implements MarkModelApi {

    private final MarkModelService service;
    Logger logger = LoggerFactory.getLogger(MarkModelController.class);
    private final MeterRegistry meterRegistry;

    public MarkModelController(MarkModelService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<ListDataAttributes<List<MarkModel>>> readMarkModel() {
        UUID runId = UUID.randomUUID();
        ResponseEntity<ListDataAttributes<List<MarkModel>>> result;
        try {
            logger.info(String.format("readMarkModels: runId: '%s'", runId));
            ListDataAttributes<List<MarkModel>> res = meterRegistry.timer("mark_models.time").record(() -> service.readMarkModels(runId));
            result = ResponseEntity.ok(res);
        } catch (Exception e) {
            String err = String.format("readMarkModels: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
        return result;
    }
}
