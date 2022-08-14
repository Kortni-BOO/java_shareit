package ru.practicum.shareit.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * // TODO .
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    long id;
    @NotNull
    private String description;
    private User requester;
    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime created;
}
