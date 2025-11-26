package org.example.bookinghotelapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookinghotelapp.controller.request.RoomFilter;
import org.example.bookinghotelapp.controller.request.RoomRequest;
import org.example.bookinghotelapp.controller.response.RoomResponse;
import org.example.bookinghotelapp.entity.Hotel;
import org.example.bookinghotelapp.entity.Room;
import org.example.bookinghotelapp.exception.NotFoundException;
import org.example.bookinghotelapp.mapping.RoomMapper;
import org.example.bookinghotelapp.repository.HotelRepository;
import org.example.bookinghotelapp.repository.RoomSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.bookinghotelapp.repository.RoomRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    @Transactional(readOnly = true)
    public RoomResponse getById(Long roomId) {
        log.info("Find Room by id: {} ", roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("message.exception.not-found.room"));
        return roomMapper.toResponseDto(room);
    }

    @Transactional
    public RoomResponse create(RoomRequest roomRequest) {
        log.info("Create Room");
        Hotel hotel = hotelRepository.findById(roomRequest.hotelId()).orElseThrow(() -> new NotFoundException("message.exception.not-found.hotel"));
        Room room = roomMapper.toEntity(roomRequest);
        room.setHotel(hotel);
        Room save = roomRepository.save(room);
        return roomMapper.toResponseDto(save);
    }

    @Transactional
    public RoomResponse update(Long roomId, RoomRequest roomRequest) {
        log.info("Update Room with id: {} ", roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("message.exception.not-found.room"));
        roomMapper.updateEntityFromDto(roomRequest, room);
        Room save = roomRepository.save(room);
        return roomMapper.toResponseDto(save);
    }

    @Transactional
    public void delete(Long roomId) {
        log.info("Delete Room with id: {}", roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("message.exception.not-found.room"));
        roomRepository.delete(room);
    }

    @Transactional(readOnly = true)
    public Page<RoomResponse> getAll(Pageable pageable, RoomFilter filter) {
        Page<Room> rooms = roomRepository.findAll(RoomSpecification.withFilter(filter), pageable);
        return rooms.map(roomMapper::toResponseDto);
    }
}