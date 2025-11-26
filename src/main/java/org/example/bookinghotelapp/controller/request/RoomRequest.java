package org.example.bookinghotelapp.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Запрос на создание/обновление комнаты")
public record RoomRequest(
        @NotBlank(message = "Имя должно быть обязательным")
        @Size(min = 2, max = 20, message = "Количество символов должно составлять от 2 до 20")
        String name,
        @NotBlank(message = "Описание должно быть обязательным")
        @Size(min = 5, max = 200, message = "Количество символов должно составлять от 5 до 200")
        String description,
        @NotBlank(message = "Номер комнаты должно быть заполнено")
        @Size(min = 1, max = 20, message = "Количество символов должно составлять от 1 до 20")
        String numberRoom,
        @Positive(message = "Цена не может быть отрицательной")
        BigDecimal price,
        @Positive(message = "Вместимость не может быть отрицательной")
        Long capacity,
        @NotNull(message = "ID отеля обязательно")
        Long hotelId) {
}
