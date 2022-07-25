package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
@Data
@Builder
public class Item {
    long id; // — уникальный идентификатор вещи;
    @NotNull
    String name; // — краткое название;
    @NotNull
    String description; // — развёрнутое описание;
    @NotNull
    Boolean available; // — статус о том, доступна или нет вещь для аренды;
    User owner; // — владелец вещи;
    String request; /*если вещь была создана по запросу другого пользователя,
              то в этом поле будет храниться ссылка на соответствующий запрос.*/
}
