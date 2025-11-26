package org.example.bookinghotelapp.repository;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.example.bookinghotelapp.controller.request.RoomFilter;
import org.example.bookinghotelapp.entity.Booking;
import org.example.bookinghotelapp.entity.Room;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter filter) {
        return Specification.allOf(
                byRoomId(filter.getRoomId()),
                byRoomName(filter.getName()),
                byRangePrice(filter.getMinPrice(), filter.getMaxPrice()),
                byHotelId(filter.getHotelId()),
                byRoomCapacity(filter.getCapacity()),
                isAvailableBetweenDates(filter.getCheckin(), filter.getCheckout()));
    }

    static Specification<Room> byRoomId(Long id) {
        return ((root, query, criteriaBuilder) ->
                id == null ? null : criteriaBuilder.equal(root.get(Room.Fields.id), id));
    }

    static Specification<Room> byRoomName(String roomName) {
        return ((root, query, criteriaBuilder) ->
                roomName == null ? null : criteriaBuilder.equal(root.get(Room.Fields.name), roomName));
    }

    static Specification<Room> byRangePrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return ((root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Room.Fields.price), maxPrice);
            }

            if (maxPrice == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Room.Fields.price), minPrice);
            }
            return criteriaBuilder.between(root.get(Room.Fields.price), minPrice, maxPrice);

        });
    }

    static Specification<Room> byHotelId(Long id) {
        return ((root, query, criteriaBuilder) ->
                id == null ? null : criteriaBuilder.equal(root.get(Room.Fields.hotel), id));
    }

    static Specification<Room> byRoomCapacity(Long capacity) {
        return ((root, query, criteriaBuilder) ->
                capacity == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get(Room.Fields.capacity), capacity));
    }

    static Specification<Room> isAvailableBetweenDates(LocalDate checkin, LocalDate checkout) {
        return ((root, query, criteriaBuilder) -> {
            if (checkin == null && checkout == null) {
                return null;
            }
            Subquery<Long> occupiedRoomsSubquery = query.subquery(Long.class);
            Root<Booking> bookingRoot = occupiedRoomsSubquery.from(Booking.class);

            occupiedRoomsSubquery.select(bookingRoot.get(Room.Fields.id))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.lessThan(bookingRoot.get(Booking.Fields.checkin), checkout),
                            criteriaBuilder.greaterThan(bookingRoot.get(Booking.Fields.checkout), checkin)
                    ));
            return criteriaBuilder.not(root.get(Room.Fields.id).in(occupiedRoomsSubquery));
        });
    }
}
