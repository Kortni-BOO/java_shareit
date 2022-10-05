package ru.practicum.shareit.booking.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private long id;
    @NotNull
    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime start;
    @NotNull
    @DateTimeFormat(fallbackPatterns = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime end;
    private Item item;
    private User booker;
    private Status status;


}
