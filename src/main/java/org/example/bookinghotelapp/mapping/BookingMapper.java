package org.example.bookinghotelapp.mapping;

import org.example.bookinghotelapp.controller.response.BookingResponse;
import org.example.bookinghotelapp.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "roomId", source = "room.id")
    BookingResponse toBookingResponse(Booking booking);
}
