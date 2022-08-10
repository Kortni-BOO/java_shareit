package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBookingDto {
    long id;

    @NotNull
    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    LocalDateTime start;

    @NotNull
    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    LocalDateTime end;

    @NotNull
    long itemId;
}
