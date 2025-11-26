package org.example.bookinghotelapp.mapping;

import org.example.bookinghotelapp.controller.request.UserRequest;
import org.example.bookinghotelapp.controller.response.UserResponse;
import org.example.bookinghotelapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toResponseDto(User user);

    void updateEntityFromDto(UserRequest userRequest, @MappingTarget User user);
}
