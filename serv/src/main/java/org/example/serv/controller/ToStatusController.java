package org.example.serv.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.serv.controller.api.ToStatusApi;
import org.example.serv.models.ListDataAttributes;
import org.example.serv.persistence.entity.ToStatus;
import org.example.serv.service.ToStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ToStatusController implements ToStatusApi {

    private final ToStatusService service;
    Logger logger = LoggerFactory.getLogger(ToStatusController.class);
    private final MeterRegistry meterRegistry;

    public ToStatusController(ToStatusService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<ListDataAttributes<List<ToStatus>>> readToStatuses() {
        UUID runId = UUID.randomUUID();
        ResponseEntity<ListDataAttributes<List<ToStatus>>> result;
        try {
            logger.info(String.format("readToStatuses: runId: '%s'", runId));
            ListDataAttributes<List<ToStatus>> res = meterRegistry.timer("to_statuses.time").record(() -> service.readToStatuses(runId));
            result = ResponseEntity.ok(res);
        } catch (Exception e) {
            String err = String.format("readToStatuses: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
        return result;
    }
}
