package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */
@Data
@Builder
public class ItemDto {
    long id; // — уникальный идентификатор вещи;
    @NotNull
    String name; // — краткое название;
    @NotNull
    String description; // — развёрнутое описание;
    @NotNull
    Boolean available; // — статус о том, доступна или нет вещь для аренды;
    User owner;
    String request; /*если вещь была создана по запросу другого пользователя,
              то в этом поле будет храниться ссылка на соответствующий запрос.*/
}
