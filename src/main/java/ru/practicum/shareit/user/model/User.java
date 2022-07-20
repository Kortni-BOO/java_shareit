package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Data;

/**
 * // TODO .
 */
@Data
@Builder
public class User {
    long id; // — уникальный идентификатор пользователя;
    String name; // — имя или логин пользователя;
    String email; // — адрес электронной почты;
}
