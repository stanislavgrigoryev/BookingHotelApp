package org.example.bookinghotelapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookinghotelapp.controller.request.HotelFilter;
import org.example.bookinghotelapp.controller.request.HotelRequest;
import org.example.bookinghotelapp.controller.response.HotelResponse;
import org.example.bookinghotelapp.entity.Hotel;
import org.example.bookinghotelapp.exception.BadRequestException;
import org.example.bookinghotelapp.exception.NotFoundException;
import org.example.bookinghotelapp.mapping.HotelMapper;
import org.example.bookinghotelapp.repository.HotelSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.bookinghotelapp.repository.HotelRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Transactional(readOnly = true)
    public HotelResponse getById(Long hotelId) {
        log.info("Find hotel by id with {}: ", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("message.exception.not-found.hotel"));
        return hotelMapper.toResponseDto(hotel);
    }

    @Transactional(readOnly = true)
    public List<HotelResponse> getAll() {
        log.info("Find all hotels");
        return hotelRepository.findAll().stream().map(hotelMapper::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public HotelResponse save(HotelRequest hotelRequest) {
        Hotel hotel = hotelMapper.toEntity(hotelRequest);
        return hotelMapper.toResponseDto(hotelRepository.save(hotel));
    }

    @Transactional
    public void delete(Long hotelId) {
        log.info("Delete hotel by id with {}: ", hotelId);
        hotelRepository.deleteById(hotelId);
    }

    @Transactional
    public HotelResponse update(Long hotelId, HotelRequest hotelRequest) {
        log.info("Update hotel with id {}: ", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("message.exception.not-found.hotel"));
        hotelMapper.updateEntityFromDTO(hotelRequest, hotel);
        Hotel updatedHotel = hotelRepository.save(hotel);
        return hotelMapper.toResponseDto(updatedHotel);
    }

    @Transactional
    public void changeRating(Long hotelId, Double newMark) {
        log.info("Change hotel rating with id {}: ", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("message.exception.not-found.hotel"));
        if (newMark < 1 || newMark > 5) {
            throw new BadRequestException("message.exception.bad-request.hotel.rating");
        }
        Double currentRating = hotel.getRating();
        Integer currentRatingCount = hotel.getRatingCount();
        double totalRating = currentRating * currentRatingCount;
        totalRating = totalRating - currentRating + newMark;
        int newRatingCount = currentRatingCount + 1;
        double newRating = totalRating / newRatingCount;
        newRating = Math.round(newRating * 10.0) / 10.0;

        hotel.setRating(newRating);
        hotel.setRatingCount(newRatingCount);
        hotelRepository.save(hotel);
    }

    @Transactional(readOnly = true)
    public Page<HotelResponse> getAll(Pageable pageable, HotelFilter filter) {
        Page<Hotel> hotels = hotelRepository.findAll(HotelSpecification.withFilter(filter), pageable);
        return hotels.map(hotelMapper::toResponseDto);
    }
}