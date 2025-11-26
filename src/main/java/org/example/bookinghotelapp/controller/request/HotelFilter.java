package org.example.bookinghotelapp.controller.request;

import lombok.Data;

@Data
public class HotelFilter {
    private Long id;
    private String name;
    private String advertisementTitle;
    private String city;
    private String address;
    private Double distanceFromCityCenter;
    private Double rating;
    private Integer ratingCount;
}
