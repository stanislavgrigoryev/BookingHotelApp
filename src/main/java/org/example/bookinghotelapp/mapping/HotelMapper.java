package org.example.bookinghotelapp.mapping;

import org.example.bookinghotelapp.controller.request.HotelRequest;
import org.example.bookinghotelapp.controller.response.HotelResponse;
import org.example.bookinghotelapp.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    Hotel toEntity(HotelRequest dto);

    HotelResponse toResponseDto(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    void updateEntityFromDTO(HotelRequest hotelRequest, @MappingTarget Hotel hotel);
}
