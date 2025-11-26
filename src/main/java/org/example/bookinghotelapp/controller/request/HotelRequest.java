package org.example.bookinghotelapp.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание/обновление отеля")
public record HotelRequest(
        @NotBlank(message = "Имя является обязательным")
        @Size(min = 2, max = 50, message = "Длинна имени должна составлять от 2 до 50 символом")
        String name,
        @NotBlank(message = "Заголовок является обязательным")
        @Size(min = 5, max = 200, message = "Длинна заголовка должна составлять от 5 до 200 символом")
        String advertisementTitle,
        @NotBlank(message = "Город является обязательным")
        @Size(min = 2, max = 50, message = "Название города должно составлять от 2 до 50 символом")
        String city,
        @NotBlank(message = "Адрес является обязательным")
        @Size(min = 5, max = 200, message = "Длинна адреса должна составлять от 2 до 200 символом")
        String address,
        @NotNull(message = "Дистанция является обязательным")
        @Positive(message = "Дистанция должна быть положительная")
        Double distanceFromCityCenter) {
}
