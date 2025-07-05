package org.example.oil.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.oil.models.ListDataAttributes;
import org.example.oil.persistence.entity.OilType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/oiltypes")
public interface OilTypeApi {

    @GetMapping
    @Operation(description = "Получение списка типов масел",
    responses = {
            @ApiResponse(description = "Ok", responseCode = "200"),
    })
    ResponseEntity<ListDataAttributes<List<OilType>>> readOilTypes();
}
