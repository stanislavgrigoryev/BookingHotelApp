package org.example.bookinghotelapp.mapping;

import org.example.bookinghotelapp.controller.request.RoomRequest;
import org.example.bookinghotelapp.controller.response.RoomResponse;
import org.example.bookinghotelapp.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    Room toEntity(RoomRequest roomRequest);

    @Mapping(target = "hotelId", source = "hotel.id")
    RoomResponse toResponseDto(Room room);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    void updateEntityFromDto(RoomRequest roomRequest, @MappingTarget Room room);
}
