package org.example.client.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.client.models.ResultWithId;
import org.example.client.models.UserReq;
import org.example.client.models.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
public interface UserApi {

    @PostMapping
    @Operation(description = "Добавление пользователя",
            responses = {
                    @ApiResponse(description = "Created",responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultWithId.class))),
                    @ApiResponse(description = "Bad Request",responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
                    @ApiResponse(description = "Unprocessable Entity",responseCode = "422", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            }
    )
    ResponseEntity<ResultWithId> create(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody() UserReq req);

    @GetMapping("/emailbylogin/{login}")
    @Operation(description = "Получение email по логину",
    responses = {
            @ApiResponse(description = "Ok", responseCode = "200", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(description = "Not found", responseCode = "404")
    })
    ResponseEntity<String> readEmailByLogin(@PathVariable("login") String login);
}
