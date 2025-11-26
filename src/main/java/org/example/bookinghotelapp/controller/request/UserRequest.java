package org.example.bookinghotelapp.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание/обновление пользователя (ADMIN/USER)")
public record UserRequest(@NotBlank(message = "Имя должно быть обязательным")
                          @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
                          String name,
                          @NotBlank(message = "Email должно быть обязательным") @Email(message = "Некорректный формат Email")
                          String email,
                          @NotBlank(message = "Пароль обязателен") @Size(min = 5, max = 12, message = "Количество символов должно составлять от 5 до 12")
                          String password) {
}
