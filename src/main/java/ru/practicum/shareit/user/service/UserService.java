package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    //создание пользователя
    public UserDto create(User user);

    //обновление пользователя
    public UserDto update(User user, long id);

    //получение пользователя по id
    public UserDto getById(long id);

    //удаление пользователя по id
    public void delete(long id);

    public List<User> getAll();
}
