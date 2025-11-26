package org.example.bookinghotelapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookinghotelapp.controller.request.RoomFilter;
import org.example.bookinghotelapp.controller.request.RoomRequest;
import org.example.bookinghotelapp.controller.response.RoomResponse;
import org.example.bookinghotelapp.service.RoomService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Tag(name = "Rooms API", description = "API для управления комнатами")
public class RoomController {
    private final RoomService roomService;

    @Operation(summary = "Получение комнаты", description = "После успешного выполнения метод возвращает по идентификатору 'RoomResponse' " +
            "который содержит всю информацию о комнате. Доступен для авторизованных пользователей и администратора.")
    @GetMapping(path = "/v1/rooms/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponse getById(@PathVariable Long id) {
        return roomService.getById(id);
    }

    @Operation(summary = "Создание комнаты", description = "Метод создает комнату и после успешного выполнения сохраняет ее и " +
            "возвращает 'RoomResponse' " +
            "содержащий информацию о созданной комнате. Доступен только для администратора.")
    @PostMapping(path = "/v1/rooms")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse create(@Valid @RequestBody RoomRequest roomRequest) {
        return roomService.create(roomRequest);
    }

    @Operation(summary = "Обновление отеля", description = "Метод в случае успешного выполнения обновляет данные о комнате по ее идентификатору " +
            "и информацией из запроса, сохраняет его и возвращает 'RoomResponse' c обновленной информацией. Доступен только для администратора.")
    @PutMapping("/v1/rooms/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public RoomResponse update(@PathVariable Long id, @Valid @RequestBody RoomRequest roomRequest) {
        return roomService.update(id, roomRequest);
    }
    @Operation(summary = "Удаление комнаты", description = "Метод в случае успешного выполнения удаляет комнату по его идентификатору. " +
            "Доступен только для администратора.")
    @DeleteMapping(path = "/v1/rooms/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        roomService.delete(id);
    }

    @Operation(summary = "Получение всех комнат", description = "Метод в случае успешного выполнения возвращает список 'RoomResponse'. " +
            "Предоставляет возможность фильтрации. Доступен для авторизованных пользователей и администратора.")
    @GetMapping(path = "/v1/rooms")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public Page<RoomResponse> getAll(@ParameterObject Pageable pageable, @ParameterObject RoomFilter filter) {
        return roomService.getAll( pageable, filter);
    }
}
