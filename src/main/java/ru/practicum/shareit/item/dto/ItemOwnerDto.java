package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOwnerDto {
    long id; // — уникальный идентификатор вещи;
    @NotNull
    String name; // — краткое название;
    @NotNull
    String description; // — развёрнутое описание;
    @NotNull
    Boolean available; // — статус о том, доступна или нет вещь для аренды;
    User owner;
    LocalDateTime end;
    LocalDateTime start;
}
