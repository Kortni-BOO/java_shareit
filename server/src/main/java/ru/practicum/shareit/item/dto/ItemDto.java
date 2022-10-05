package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    long id; // — уникальный идентификатор вещи;

    String name; // — краткое название;

    String description; // — развёрнутое описание;

    Boolean available; // — статус о том, доступна или нет вещь для аренды;
    User owner;
    Long requesterId;
}
