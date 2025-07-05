package org.example.car.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.car.models.ListDataAttributes;
import org.example.car.persistence.entity.MotorType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/motortypes")
public interface MotorTypeApi {

    @GetMapping
    @Operation(description = "Получение списка типов моторов",
    responses = {
            @ApiResponse(description = "Ok", responseCode = "200"),
    })
    ResponseEntity<ListDataAttributes<List<MotorType>>> readMotorTypes();
}
