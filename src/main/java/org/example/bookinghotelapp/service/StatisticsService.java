package org.example.bookinghotelapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookinghotelapp.dto.BookingDate;
import org.example.bookinghotelapp.dto.BookingEvent;
import org.example.bookinghotelapp.dto.UserEvent;
import org.example.bookinghotelapp.entity.Statistics;
import org.example.bookinghotelapp.exception.InternalServerErrorException;
import org.example.bookinghotelapp.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public void saveUserEvent(UserEvent userEvent) {
        Statistics stats = new Statistics();
        stats.setEventType("USER_REGISTRATION");
        stats.setUserId(userEvent.getUserId());
        stats.setEventTimestamp(userEvent.getTimestamp());
        stats.setCreatedDate(LocalDate.now());

        statisticsRepository.save(stats);
    }

    public void saveBookingEvent(BookingEvent bookingEvent) {
        Statistics stats = new Statistics();
        stats.setEventType("BOOKING");
        stats.setUserId(bookingEvent.getUserId());
        stats.setBookingDates(bookingEvent.getBookingDates());
        stats.setEventTimestamp(LocalDateTime.now());
        stats.setCreatedDate(LocalDate.now());

        statisticsRepository.save(stats);
    }

    public List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }

    public byte[] exportToCsv() {
        List<Statistics> stats = getAllStatistics();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {

            writer.println("Event Type,User ID,Event Timestamp,Checkin Date,Checkout Date");

            for (Statistics stat : stats) {
                if ("USER_REGISTRATION".equals(stat.getEventType())) {
                    writer.printf("%s,%d,%s,%s,%s%n",
                            stat.getEventType(),
                            stat.getUserId(),
                            stat.getEventTimestamp(),
                            "", "");
                } else if ("BOOKING".equals(stat.getEventType()) && stat.getBookingDates() != null) {
                    for (BookingDate bookingDate : stat.getBookingDates()) {
                        writer.printf("%s,%d,%s,%s,%s%n",
                                stat.getEventType(),
                                stat.getUserId(),
                                stat.getEventTimestamp(),
                                bookingDate.getCheckin(),
                                bookingDate.getCheckout());
                    }
                }
            }

            writer.flush();
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new InternalServerErrorException("message.exception.internal.generate.file.statistics");
        }
    }

}
