package org.example.bookinghotelapp.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Ответ с данными о комнате")
public record RoomResponse(Long id, String name, String description, String numberRoom, BigDecimal price,
                           Long capacity, List<LocalDate> unavailableDates, Long hotelId) {
}
