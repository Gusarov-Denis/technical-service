package org.example.car.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.car.models.CarReq;
import org.example.car.models.ListDataAttributes;
import org.example.car.models.ResultWithId;
import org.example.car.persistence.entity.Car;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/cars")
public interface CarApi {

    @PostMapping
    @Operation(description = "Добавление машины",
            responses = {
                    @ApiResponse(description = "Created",responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultWithId.class))),
                    @ApiResponse(description = "Bad Request",responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
                    @ApiResponse(description = "Unprocessable Entity",responseCode = "422", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    ResponseEntity<ResultWithId> create(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody() CarReq req);

    @GetMapping
    @Operation(description = "Получение списка машин",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
            })
    ResponseEntity<ListDataAttributes<List<Car>>> readCars();

    @GetMapping(value = "/{id}")
    @Operation(description = "Получение данных о машине",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    ResponseEntity<Car> read(@PathVariable UUID id);

    @GetMapping("/byregnumber/{regnumber}")
    @Operation(description = "Получение данных о машине по регистрационному номеру",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(description = "Not found", responseCode = "404")
            })
    ResponseEntity<Car> readByRegNumber(@PathVariable("regnumber") String regNumber);
}

