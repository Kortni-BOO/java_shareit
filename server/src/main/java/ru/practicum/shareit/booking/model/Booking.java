package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id; // — уникальный идентификатор бронирования;

    @Column(name = "start_date_time")
    LocalDateTime start; // — дата начала бронирования;

    @Column(name = "end_date_time")
    LocalDateTime end; // — дата конца бронирования;

    @ManyToOne()
    @JoinColumn(name = "item_id")
    Item item; // — вещь, которую пользователь бронирует;

    @ManyToOne()
    @JoinColumn(name = "booker_id")
    User booker; // — пользователь, который осуществляет бронирование;

    @Enumerated(EnumType.STRING)
    Status status;
}
