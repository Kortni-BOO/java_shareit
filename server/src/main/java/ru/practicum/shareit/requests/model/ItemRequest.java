package ru.practicum.shareit.requests.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id; // — уникальный идентификатор запроса;

    @Column(name = "description")
    String description; // — текст запроса, содержащий описание требуемой вещи;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    User requester; // — пользователь, создавший запрос;

    @Column(name = "created")
    LocalDateTime created; // — дата и время создания запроса.
}
