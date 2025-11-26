package org.example.bookinghotelapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookinghotelapp.controller.request.BookingRequest;
import org.example.bookinghotelapp.controller.response.BookingResponse;
import org.example.bookinghotelapp.service.BookingService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
@Tag(name = "Bookings API", description = "API для управления бронированиями номеров")
public class BookingController {
    private final BookingService bookingService;

    @Operation(summary = "Получение всех бронирований",
            description = "Метод возвращает все бронирования в формате пагинации. После успешного выполнения метод возвращает 'BookingResponse' " +
                    "содержащий информацию о всех бронированиях")
    @GetMapping(path = "/v1/bookings")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookingResponse> getAll(@ParameterObject Pageable pageable) {
        return bookingService.getAll(pageable);
    }

    @Operation(summary = "Создание брони", description = "Создает новое бронирование комнаты для пользователя. " +
            "Проверяет доступность дат, отправляет событие в Kafka и возвращает 'BookingResponse' содержащий информацию о бронировании.")
    @PostMapping(path = "/v1/bookings")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody BookingRequest bookingRequest) {
        return bookingService.create(bookingRequest);
    }
}
