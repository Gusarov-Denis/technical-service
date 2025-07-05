package org.example.oil.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.oil.controller.api.OilTypeApi;
import org.example.oil.models.ListDataAttributes;
import org.example.oil.persistence.entity.OilType;
import org.example.oil.service.OilTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class OilTypeController implements OilTypeApi {

    private final OilTypeService service;
    Logger logger = LoggerFactory.getLogger(OilTypeController.class);
    private final MeterRegistry meterRegistry;

    public OilTypeController(OilTypeService service, MeterRegistry meterRegistry) {
        this.service = service;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ResponseEntity<ListDataAttributes<List<OilType>>> readOilTypes() {
        UUID runId = UUID.randomUUID();
        ResponseEntity<ListDataAttributes<List<OilType>>> result;
        try {
            logger.info(String.format("readOilTypes: runId: '%s'", runId));
            ListDataAttributes<List<OilType>> res = meterRegistry.timer("oil_types.time").record(() -> service.readOilTypes(runId));
            result = ResponseEntity.ok(res);
        } catch (Exception e) {
            String err = String.format("readOilTypes: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
        return result;
    }
}
