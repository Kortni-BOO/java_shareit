package ru.practicum.shareit.item.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    long id;
    String text;

    String author;

    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    LocalDateTime created;
}
