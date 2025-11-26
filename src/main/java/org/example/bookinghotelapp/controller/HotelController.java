package org.example.bookinghotelapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookinghotelapp.controller.request.HotelFilter;
import org.example.bookinghotelapp.controller.request.HotelRequest;
import org.example.bookinghotelapp.controller.response.HotelResponse;
import org.example.bookinghotelapp.service.HotelService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Tag(name = "Hotels API", description = "API для управления комнатами")
public class HotelController {
    private final HotelService hotelService;

    @Operation(summary = "Получение отеля", description = "После успешного выполнения метод возвращает по идентификатору 'HotelResponse' " +
            "который содержит всю информацию об отеле. Доступен для авторизованных пользователей и администратора.")
    @GetMapping(path = "/v1/hotels/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse getById(@PathVariable Long id) {
        return hotelService.getById(id);
    }

    @Operation(summary = "Получение всех отелей", description = "После успешного выполнения метод возвращает список 'HotelResponse' " +
            "который содержит всю информацию об отелях. Доступен для авторизованных пользователей и администратора.")
    @GetMapping(path = "/v1/hotels")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelResponse> getAll() {
        return hotelService.getAll();
    }

    @Operation(summary = "Создание отеля", description = "Метод создает отель и после успешного выполнения сохраняет его и " +
            "возвращает 'HotelResponse' " +
            "содержащий информацию о созданном отеле. Доступен только для администратора.")
    @PostMapping(path = "/v1/hotels")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse create(@Valid @RequestBody HotelRequest hotelRequest) {
        return hotelService.save(hotelRequest);
    }

    @Operation(summary = "Удаление отеля", description = "Метод в случае успешного выполнения удаляет отель по его идентификатору. " +
            "Доступен только для администратора.")
    @DeleteMapping(path = "/v1/hotels/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        hotelService.delete(id);
    }

    @Operation(summary = "Обновление отеля", description = "Метод в случае успешного выполнения обновляет данные об отеле по его идентификатору " +
            "и информацией из запроса, сохраняет его и возвращает 'HotelResponse' c обновленной информацией. Доступен только для администратора.")
    @PutMapping(path = "/v1/hotels/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse update(@PathVariable Long id, @Valid @RequestBody HotelRequest hotelRequest) {
        return hotelService.update(id, hotelRequest);
    }

    @Operation(summary = "Изменение рейтинга", description = "Метод в случае успешного выполнения изменяет рейтинг существующего отеля по шкале от 1 до 5. " +
            "Доступен для авторизованных пользователей и администратора.")
    @PatchMapping(path = "/v1/hotels/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public void patch(@PathVariable Long id, @RequestBody Double value) {
        hotelService.changeRating(id, value);
    }

    @Operation(summary = "Получение всех отелей", description = "Метод в случае успешного выполнения возвращает список 'HotelResponse'. " +
            "Предоставляет возможность фильтрации. Доступен для авторизованных пользователей и администратора.")
    @GetMapping(path = "/v1/filter")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public Page<HotelResponse> getHotels(@ParameterObject Pageable pageable, @ParameterObject HotelFilter filter) {
        return hotelService.getAll(pageable, filter);
    }
}
