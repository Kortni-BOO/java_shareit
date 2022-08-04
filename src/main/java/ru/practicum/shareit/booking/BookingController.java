package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.PostBookingDto;
import ru.practicum.shareit.booking.enums.State;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

/**
 * // Контроллер для бронирования вещей.
 */
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") long id,
                                 @RequestBody PostBookingDto booking) {
        return bookingService.create(booking, id);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto setApproved(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @PathVariable long bookingId,
                                  @RequestParam boolean approved) {
        return bookingService.setApproved(bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getItemByUserId(@RequestHeader("X-Sharer-User-Id") long userId,
                                      @PathVariable long bookingId) {
        return bookingService.getById(bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllBookingByUserId(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(defaultValue = "ALL") State state) {
        return bookingService.getAllBookingByUserId(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllItemByUserId(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(defaultValue = "ALL") State state) {
        return bookingService.getAllItemByUserId(userId, state);
    }
}
