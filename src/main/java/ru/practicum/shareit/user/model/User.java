package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id; // — уникальный идентификатор пользователя;
    @Column(name = "name")
    String name; // — имя или логин пользователя;
    @Column(name = "email")
    String email; // — адрес электронной почты;
}
