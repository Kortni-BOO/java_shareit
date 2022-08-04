package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.PostBookingDto;
import ru.practicum.shareit.booking.enums.State;

import java.util.List;

public interface BookingService {
    /* Добавление (создание) нового запроса на бронирование */
    BookingDto create(PostBookingDto bookingDto, long userId);

    /* Подтверждение или отклонение запроса на бронирование */
    BookingDto setApproved(long bookingId, boolean approved);

    /* Получение данных о конкретном бронировании */
    BookingDto getById(long bookingId);

    /* Получение списка всех бронирований текущего пользователя */
    List<BookingDto> getAllBookingByUserId(long userId, State state);

    /* Получение списка бронирований для всех вещей текущего пользователя */
    List<BookingDto> getAllItemByUserId(long userId, State state);
}
