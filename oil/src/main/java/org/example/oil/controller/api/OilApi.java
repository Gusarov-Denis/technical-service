package org.example.oil.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.oil.models.ListDataAttributes;
import org.example.oil.models.OilReq;
import org.example.oil.models.ResultWithId;
import org.example.oil.persistence.entity.Oil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/oils")
public interface OilApi {

    @PostMapping
    @Operation(description = "Добавление масла",
            responses = {
                    @ApiResponse(description = "Created",responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultWithId.class))),
                    @ApiResponse(description = "Bad Request",responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
                    @ApiResponse(description = "Unprocessable Entity",responseCode = "422", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    ResponseEntity<ResultWithId> create(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody() OilReq req);

    @GetMapping
    @Operation(description = "Получение списка масел",
    responses = {
            @ApiResponse(description = "Ok", responseCode = "200"),
    })
    ResponseEntity<ListDataAttributes<List<Oil>>> readOils();

    @GetMapping(value = "/{id}")
    @Operation(description = "Получение масла",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    ResponseEntity<Oil> read(@PathVariable UUID id);

}
