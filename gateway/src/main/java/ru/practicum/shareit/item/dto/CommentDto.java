package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    long id;

    @NotNull
    @Size(max = 5000)
    String text;

    String author;

    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    LocalDateTime created;
}
