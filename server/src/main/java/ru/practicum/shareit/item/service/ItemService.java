package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    //добавление вещи
    public ItemDto create(ItemDto item, long id);

    //обновление вещи
    public ItemDto update(ItemDto item, long id, long itemId);

    //удаление вещи по id
    public void delete(Item item);

    //Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой
    public List<ItemOwnerDto> getAllByUserId(long id, int from, int size);

    //Просмотр информации о конкретной вещи по её идентификатору
    public ItemDto getById(long id);

    //Поиск вещи потенциальным арендатором по тексту в название или описание
    List<ItemDto> searchItemByQuery(String text, int from, int size);

    CommentDto createComment(long id, long itemId, CommentDto commentDto);
}
