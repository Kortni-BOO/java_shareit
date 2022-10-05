package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class UserStorage {
    private HashMap<Long, User> users = new HashMap<>();
    private long id = 0;

    private long generateId() {
        return ++id;
    }

    //создание пользователя
    public User create(User user) {
        checkData(user);
        checkEmail(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    //обновление пользователя;
    public User update(User user, long id) {
        User userUp = users.get(id);
        checkEmail(user);
        if (users.get(user.getId()) != null) {
            users.put(userUp.getId(), user);
        }
        if (user.getEmail() != null) {
            userUp.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            userUp.setName(user.getName());
        }
        return users.get(userUp.getId());
    }


    //получить пользователя по id
    public Optional<User> getById(long id) {
        if (id < 0) {
            throw new UserNotFoundException(String.format("Пользователь № %d не найден", id));
        }
        return Optional.ofNullable(users.get(id));
    }

    //удалить пользователя по id
    public void delete(long id) {
        users.remove(id);
    }

    public List<User> getAll() {
        List<User> usersList = new ArrayList<>(users.values());
        return usersList;
    }

    public void checkData(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || (!user.getEmail().contains("@"))) {
            throw new ValidationException("Адрес электронной почты не может быть " +
                    "пустым и должен содержать символ @.");
        }
    }

    public void checkEmail(User user) {
        ArrayList<String> emails = new ArrayList<>();
        for (User userEmail: users.values()) {
            //userEmail.getEmail();
            emails.add(userEmail.getEmail());
        }
        if (emails.contains(user.getEmail())) {
            throw new DuplicateEmailException("Пользователь с электронной почтой уже зарегистрирован.");
        }
    }
}
