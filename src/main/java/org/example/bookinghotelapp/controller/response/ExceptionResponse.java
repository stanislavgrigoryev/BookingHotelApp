package org.example.bookinghotelapp.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с сообщением об исключении")
public record ExceptionResponse(String message) {
}
