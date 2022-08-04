package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id; // — уникальный идентификатор вещи;

    @Column(name = "name")
    String name; // — краткое название;

    @Column(name = "description")
    String description; // — развёрнутое описание;

    @Column(name = "available")
    Boolean available; // — статус о том, доступна или нет вещь для аренды;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    User owner; // — владелец вещи;

    @OneToOne()
    @JoinColumn(name = "request_id")
    ItemRequest request; /*если вещь была создана по запросу другого пользователя,
              то в этом поле будет храниться ссылка на соответствующий запрос.*/
}
