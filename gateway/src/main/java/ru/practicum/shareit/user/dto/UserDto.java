package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
     long id; // — уникальный идентификатор пользователя;
     @NotNull
     String name; // — имя или логин пользователя;
     @Email
     @NotNull
     String email; // — адрес электронной почты;
 }
