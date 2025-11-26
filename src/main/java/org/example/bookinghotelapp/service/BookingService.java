package org.example.bookinghotelapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookinghotelapp.controller.request.BookingRequest;
import org.example.bookinghotelapp.controller.response.BookingResponse;
import org.example.bookinghotelapp.dto.BookingDate;
import org.example.bookinghotelapp.dto.BookingEvent;
import org.example.bookinghotelapp.entity.Booking;
import org.example.bookinghotelapp.entity.Room;
import org.example.bookinghotelapp.entity.User;
import org.example.bookinghotelapp.exception.BadRequestException;
import org.example.bookinghotelapp.exception.ConflictException;
import org.example.bookinghotelapp.exception.NotFoundException;
import org.example.bookinghotelapp.mapping.BookingMapper;
import org.example.bookinghotelapp.repository.BookingRepository;
import org.example.bookinghotelapp.repository.RoomRepository;
import org.example.bookinghotelapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final KafkaProducerService kafkaProducerService;
    private final BookingMapper bookingMapper;

    @Transactional(readOnly = true)
    public Page<BookingResponse> getAll(Pageable pageable) {
        log.info("Getting all bookings");
        Page<Booking> bookings = bookingRepository.findAll(pageable);
        return bookings.map(bookingMapper::toBookingResponse);
    }

    @Transactional
    public BookingResponse create(BookingRequest bookingRequest) {
        log.info("Creating a booking");

        Room room = roomRepository.findById(bookingRequest.roomId())
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.room"));

        User user = userRepository.findById(bookingRequest.userId())
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.user.id"));

        LocalDate checkIn = bookingRequest.checkin();
        LocalDate checkOut = bookingRequest.checkout();

        validateBookingDates(checkIn, checkOut);

        List<LocalDate> unavailableDates = room.getUnavailableDates();

        List<LocalDate> availableList = checkDateAvailability(checkIn, checkOut, unavailableDates);
        room.setUnavailableDates(availableList);

        Booking booking = Booking.builder()
                .room(room)
                .user(user)
                .checkin(checkIn)
                .checkout(checkOut)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        kafkaProducerService.sendBookingEvent(producerEvent(user, checkIn, checkOut));

        return bookingMapper.toBookingResponse(savedBooking);
    }

    private List<LocalDate> checkDateAvailability(LocalDate checkIn, LocalDate checkOut, List<LocalDate> unavailableDates) {
        List<LocalDate> unavailableList = new ArrayList<>(unavailableDates);

        boolean hasConflict = checkIn.datesUntil(checkOut.plusDays(1))
                .anyMatch(unavailableList::contains);

        if (hasConflict) {
            throw new ConflictException("message.exception.conflict.booking.dates-unavailable");
        }

        return List.of(checkIn, checkOut);
    }

    private void validateBookingDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isBefore(LocalDate.now())) {
            throw new BadRequestException("Check-in date cannot be in the past");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        if (ChronoUnit.DAYS.between(checkIn, checkOut) > 30) {
            throw new BadRequestException("Maximum booking duration is 30 days");
        }
    }

    private BookingEvent producerEvent(User user, LocalDate checkIn, LocalDate checkOut) {

        BookingDate bookingDate = new BookingDate();
        bookingDate.setCheckin(checkIn);
        bookingDate.setCheckout(checkOut);

        return BookingEvent.builder().userId(user.getId())
                .bookingDates(List.of(bookingDate))
                .build();
    }
}
