package org.example.oil.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.oil.models.ListDataAttributes;
import org.example.oil.persistence.entity.OilViscosity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/oilviscosities")
public interface OilViscosityApi {

    @GetMapping
    @Operation(description = "Получение списка вязкости масла",
    responses = {
            @ApiResponse(description = "Ok", responseCode = "200"),
    })
    ResponseEntity<ListDataAttributes<List<OilViscosity>>> readOilViscosities();
}
