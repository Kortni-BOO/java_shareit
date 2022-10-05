package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    long id;

    @Column(name = "text")
    String text;

    @ManyToOne()
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    Item item;

    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    User author;

    @Column(name = "created")
    LocalDateTime created;
}
