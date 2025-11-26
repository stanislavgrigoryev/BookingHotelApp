package org.example.bookinghotelapp.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookinghotelapp.dto.BookingEvent;
import org.example.bookinghotelapp.dto.UserEvent;
import org.example.bookinghotelapp.service.StatisticsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventListener {
    private final StatisticsService statisticsService;

    @KafkaListener(topics = "${app.kafka.userTopic}", groupId = "${app.kafka.groupId}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenUserEvent(UserEvent userEvent) {
        log.info("Received user event: {}", userEvent);
        statisticsService.saveUserEvent(userEvent);
    }

    @KafkaListener(topics = "${app.kafka.bookingTopic}", groupId = "${app.kafka.groupId}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenBookingEvent(BookingEvent bookingEvent) {
        log.info("Received booking event: {}", bookingEvent);
        statisticsService.saveBookingEvent(bookingEvent);
    }
}
