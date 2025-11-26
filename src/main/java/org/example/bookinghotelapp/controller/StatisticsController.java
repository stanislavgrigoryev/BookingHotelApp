package org.example.bookinghotelapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.bookinghotelapp.service.StatisticsService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/statistics")
@Tag(name = "Statistics API", description = "API для получения статистики")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Operation(summary = "Получение статистики", description = "Метод в случае успешного выполнения возвращает файл в формате CSV. " +
            "по событиям регистрации пользователя и бронированию. Доступен только для администратора.")
    @GetMapping("/v1/export")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> exportStatistics() {
        byte[] csvData = statisticsService.exportToCsv();

        ByteArrayResource resource = new ByteArrayResource(csvData);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=statistics.csv")
                .body(resource);
    }
}
