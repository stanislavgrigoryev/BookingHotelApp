package org.example.bookinghotelapp.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными о пользователе")
public record UserResponse(Long id, String name, String email) {
}
