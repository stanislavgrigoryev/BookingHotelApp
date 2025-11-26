package org.example.bookinghotelapp.entity;

import lombok.Data;
import org.example.bookinghotelapp.dto.BookingDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "statistics")
@Data
public class Statistics {

    @Id
    private String id;

    private String eventType;

    private Long userId;

    private LocalDateTime eventTimestamp;

    private List<BookingDate> bookingDates;

    private LocalDate createdDate;
}
