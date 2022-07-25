package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    long id; // — уникальный идентификатор пользователя;
    String name; // — имя или логин пользователя;
    String email; // — адрес электронной почты;
}
