package org.example.bookinghotelapp.controller.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RoomFilter {
    private Long roomId;
    private String name;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private Long capacity;
    private Long hotelId;
    private LocalDate checkin;
    private LocalDate checkout;
}
