package org.example.bookinghotelapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookinghotelapp.controller.request.UserRequest;
import org.example.bookinghotelapp.controller.response.UserResponse;
import org.example.bookinghotelapp.entity.RoleType;
import org.example.bookinghotelapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Tag(name = "Users API", description = "API для управления пользователями")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Регистрация пользователя", description = "Метод создает пользователя с ролью USER/ADMIN и после " +
            "успешного выполнения сохраняет его, отправляет событие в Kafka и " +
            "возвращает 'UserResponse' содержащий информацию о пользователе.")
    @PostMapping(path = "/v1/users/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest userRequest, @RequestParam RoleType roleType) {
        return userService.create(userRequest, roleType);
    }

    @Operation(summary = "Получение пользователя", description = "После успешного выполнения метод возвращает по идентификатору 'UserResponse' " +
            "который содержит всю информацию о пользователе. Доступен для авторизованных пользователей и администратора.")
    @GetMapping(path = "/v1/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @Operation(summary = "Получение всех пользователей", description = "Метод в случае успешного выполнения возвращает список 'UserResponse'. " +
            "Доступен для авторизованных пользователей и администратора.")
    @GetMapping(path = "/v1/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAll() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Обновление пользователя", description = "Метод в случае успешного выполнения обновляет данные о пользователе по ее идентификатору " +
            "и информацией из запроса, сохраняет его и возвращает 'UserResponse' c обновленной информацией. Доступен для авторизованных пользователей и администратора.")
    @PutMapping(path = "/v1/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

    @Operation(summary = "Удаление пользователя", description = "Метод в случае успешного выполнения удаляет пользователя по его идентификатору. " +
            "Доступен для авторизованных пользователей и администратора")
    @DeleteMapping(path = "/v1/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
