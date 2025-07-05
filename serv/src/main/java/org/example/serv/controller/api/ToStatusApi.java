package org.example.serv.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.serv.models.ListDataAttributes;
import org.example.serv.persistence.entity.ToStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/tostatuses")
public interface ToStatusApi {

    @GetMapping
    @Operation(description = "Получение списка статусов ТО",
    responses = {
            @ApiResponse(description = "Ok", responseCode = "200"),
    })
    ResponseEntity<ListDataAttributes<List<ToStatus>>> readToStatuses();
}
