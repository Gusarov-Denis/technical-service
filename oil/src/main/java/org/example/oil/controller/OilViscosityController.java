package org.example.oil.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.oil.controller.api.OilViscosityApi;
import org.example.oil.models.ListDataAttributes;
import org.example.oil.persistence.entity.OilViscosity;
import org.example.oil.service.OilViscosityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class OilViscosityController implements OilViscosityApi {

    private final OilViscosityService service;
    Logger logger = LoggerFactory.getLogger(OilViscosityController.class);
    private final MeterRegistry meterRegistry;

    public OilViscosityController(OilViscosityService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<ListDataAttributes<List<OilViscosity>>> readOilViscosities() {
        UUID runId = UUID.randomUUID();
        ResponseEntity<ListDataAttributes<List<OilViscosity>>> result;
        try {
            logger.info(String.format("readOilViscosities: runId: '%s'", runId));
            ListDataAttributes<List<OilViscosity>> res = meterRegistry.timer("oil_viscosities.time").record(() -> service.readOilViscosities(runId));
            result = ResponseEntity.ok(res);
        } catch (Exception e) {
            String err = String.format("readOilViscosities: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
        return result;
    }
}
