package ru.practicum.shareit.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBookingDto {
    long id;


    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    LocalDateTime start;


    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    LocalDateTime end;


    long itemId;
}
