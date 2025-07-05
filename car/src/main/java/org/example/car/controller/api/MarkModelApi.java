package org.example.car.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.car.models.ListDataAttributes;
import org.example.car.persistence.entity.MarkModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/markmodels")
public interface MarkModelApi {

    @GetMapping
    @Operation(description = "Получение списка марок моделей авто",
    responses = {
            @ApiResponse(description = "Ok", responseCode = "200"),
    })
    ResponseEntity<ListDataAttributes<List<MarkModel>>> readMarkModel();
}
