package ru.practicum.shareit.item.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.user.model.User;


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
    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    LocalDateTime end;
    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    LocalDateTime start;
}
