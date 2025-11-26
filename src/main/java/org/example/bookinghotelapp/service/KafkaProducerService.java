package org.example.bookinghotelapp.service;

import lombok.RequiredArgsConstructor;
import org.example.bookinghotelapp.dto.BookingEvent;
import org.example.bookinghotelapp.dto.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.userTopic}")
    private String userTopic;

    @Value("${app.kafka.bookingTopic}")
    private String bookingTopic;

    public void sendUserEvent(UserEvent userEvent) {
        kafkaTemplate.send(userTopic, userEvent.getUserId().toString(), userEvent);
    }

    public void sendBookingEvent(BookingEvent bookingEvent) {
        kafkaTemplate.send(bookingTopic, bookingEvent.getUserId().toString(), bookingEvent);
    }
}
