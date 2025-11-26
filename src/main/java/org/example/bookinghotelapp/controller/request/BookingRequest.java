package org.example.bookinghotelapp.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Запрос на создание бронирования")
public record BookingRequest(
        @NotNull(message = "Дата заезда должна быть заполнена") LocalDate checkin,
        @NotNull(message = "Дата выезда должна быть заполнена") LocalDate checkout,
        @NotNull(message = "ID пользователя должен быть указан") Long userId,
        @NotNull(message = "ID комнаты должен быть указан") Long roomId) {
}
