package ru.practicum.shareit.user;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import org.slf4j.Logger;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto user) {
        log.debug("Получен запрос на создание пользователя POST /users.");
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable long id, @RequestBody UserDto user) {
        log.debug("Получен запрос на изменение пользователя PATCH /users.");
        return userService.update(user, id);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable long id) {
        log.debug("Получен запрос GET /users/{id}.");
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

    @GetMapping
    public List<UserDto> findAll() {
        log.debug("Получен запрос GET /users.");
        return userService.getAll();
    }
}
