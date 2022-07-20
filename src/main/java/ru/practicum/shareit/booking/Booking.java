package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * // TODO .
 */
@Data
public class Booking {
    long id; // — уникальный идентификатор бронирования;
    LocalDateTime start; // — дата начала бронирования;
    LocalDateTime end; // — дата конца бронирования;
    Item item; // — вещь, которую пользователь бронирует;
    User booker; // — пользователь, который осуществляет бронирование;
}
