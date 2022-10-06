package ru.practicum.shareit.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    long id;
    @NotNull
    private String description;
    private Long requester;
    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime created;
}
