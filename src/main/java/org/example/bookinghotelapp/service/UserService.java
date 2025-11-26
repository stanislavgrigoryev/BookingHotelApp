package org.example.bookinghotelapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookinghotelapp.controller.request.UserRequest;
import org.example.bookinghotelapp.controller.response.UserResponse;
import org.example.bookinghotelapp.dto.UserEvent;
import org.example.bookinghotelapp.entity.Role;
import org.example.bookinghotelapp.entity.RoleType;
import org.example.bookinghotelapp.entity.User;
import org.example.bookinghotelapp.exception.ConflictException;
import org.example.bookinghotelapp.exception.NotFoundException;
import org.example.bookinghotelapp.mapping.UserMapper;
import org.example.bookinghotelapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public UserResponse create(UserRequest userRequest, RoleType roleType) {
        log.info("Creating user");
        if (userRepository.existsUserByName(userRequest.name()) || userRepository.existsUserByEmail(userRequest.email())) {
            throw new ConflictException("message.exception.conflict.user");
        }

        Role role = Role.from(roleType);
        User user = User.builder().password(passwordEncoder.encode(userRequest.password()))
                .name(userRequest.name())
                .email(userRequest.email())
                .roles(List.of(role))
                .build();

        role.setUser(user);

        userRepository.saveAndFlush(user);

        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(user.getId());
        userEvent.setTimestamp(LocalDateTime.now());
        kafkaProducerService.sendUserEvent(userEvent);

        return userMapper.toResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.info("Get user by id {}: ", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("message.exception.not-found.user.id", id));
        return userMapper.toResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        log.info("Get all users");
        return userRepository.findAll().stream().map(userMapper::toResponseDto).toList();
    }

    @Transactional
    public UserResponse update(Long id, UserRequest userRequest) {
        log.info("Update user by id {}: ", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("message.exception.not-found.user.id", id));
        userMapper.updateEntityFromDto(userRequest, user);
        User upadateUser = userRepository.saveAndFlush(user);
        return userMapper.toResponseDto(upadateUser);
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("Delete user by id {}: ", id);
        userRepository.deleteById(id);
    }
}
