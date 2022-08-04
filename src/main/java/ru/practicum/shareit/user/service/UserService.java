package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    //создание пользователя
    public UserDto create(UserDto user);

    //обновление пользователя
    public UserDto update(UserDto user, long id);

    //получение пользователя по id
    public UserDto getById(long id);

    //удаление пользователя по id
    public void delete(long id);

    public List<UserDto> getAll();
}
