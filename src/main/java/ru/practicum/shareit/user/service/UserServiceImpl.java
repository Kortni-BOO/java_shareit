package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserStorage userStorage;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserStorage userStorage, UserMapper userMapper) {
        this.userStorage = userStorage;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userStorage.create(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto update(UserDto userDto, long id) {
        User user = userStorage.update(userMapper.toUser(userDto), id);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getById(long id) {
        User user = userStorage.getById(id).orElseThrow(() -> {
            throw new UserNotFoundException(String.format("Пользователь с id %d не найден", id));
        });
        return userMapper.toUserDto(user);
    }

    @Override
    public void delete(long id) {
        userStorage.delete(id);
    }

    @Override
    public List<UserDto> getAll() {
        return userStorage.getAll().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }
}
