package org.example.bookinghotelapp.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными об отеле")
public record HotelResponse(Long id, String name, String advertisementTitle, String city, String address,
                            Double distanceFromCityCenter,
                            Double rating, Integer ratingCount) {
}
