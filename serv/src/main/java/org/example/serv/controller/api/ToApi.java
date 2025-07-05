package org.example.serv.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.serv.models.ListDataAttributes;
import org.example.serv.models.ResultWithId;
import org.example.serv.models.ToReq;
import org.example.serv.persistence.entity.To;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/tos")
public interface ToApi {

    @PostMapping
    @Operation(description = "Добавление ТО",
            responses = {
                    @ApiResponse(description = "Created",responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultWithId.class))),
                    @ApiResponse(description = "Bad Request",responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
                    @ApiResponse(description = "Unprocessable Entity",responseCode = "422", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    ResponseEntity<ResultWithId> create(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody() ToReq req);

    @GetMapping
    @Operation(description = "Получение списка ТО",
    responses = {
            @ApiResponse(description = "Ok", responseCode = "200"),
    })
    ResponseEntity<ListDataAttributes<List<To>>> readTos();

    @GetMapping(value = "/{id}")
    @Operation(description = "Получение ТО",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    ResponseEntity<To> read(@PathVariable UUID id);
}
