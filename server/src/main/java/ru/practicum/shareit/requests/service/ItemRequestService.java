package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    // добавить новый запрос вещи
    ItemRequestDto create(ItemRequestDto request);

    //получить список своих запросов вместе с данными об ответах на них.
    List<ItemRequestDto> getRequestsByUserId(long userId);

    //получить список запросов, созданных другими пользователями
    List<ItemRequestDto> getAllRequests(long userId, int from, int size);

    //получить данные об одном конкретном запросе
    ItemRequestDto getRequestByUserId(long userId, long requestId);


}
