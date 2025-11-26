package org.example.bookinghotelapp.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Ответ с данными бронирования")
public record BookingResponse(Long id, LocalDate checkin,
                              LocalDate checkout, Long userId, Long roomId) {
}
