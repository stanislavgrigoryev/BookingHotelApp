package org.example.bookinghotelapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDate {
    private LocalDate checkin;
    private LocalDate checkout;
}
