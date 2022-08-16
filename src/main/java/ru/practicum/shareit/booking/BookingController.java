package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.PostBookingDto;
import ru.practicum.shareit.booking.enums.State;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.user.UserController;

import java.util.List;

/**
 * // Контроллер для бронирования вещей.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingServiceImpl bookingService;
    private static final String USER_HEADER = "X-Sharer-User-Id";
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public PostBookingDto create(@RequestHeader(USER_HEADER) long id,
                                 @RequestBody PostBookingDto booking) {
        log.debug("Получен запрос на создание бронирования POST /bookings.");
        return bookingService.create(booking, id);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto setApproved(@RequestHeader(USER_HEADER) long userId,
                                  @PathVariable long bookingId,
                                  @RequestParam boolean approved) {
        log.debug("Получен запрос на одобрение бронирования PATCH /bookings.");
        return bookingService.setApproved(bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getItemByUserId(@RequestHeader(USER_HEADER) long userId,
                                      @PathVariable long bookingId) {
        log.debug("Получен запрос на получение бронирования GET /bookings.");
        return bookingService.getById(bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllBookingByUserId(@RequestHeader(USER_HEADER) long userId,
                                                  @RequestParam(defaultValue = "ALL") State state,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "20") int size) {
        log.debug("Получен запрос на получение бронирования GET /bookings.");
        return bookingService.getAllBookingByUserId(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllItemByUserId(@RequestHeader(USER_HEADER) long userId,
                                               @RequestParam(defaultValue = "ALL") State state,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "20") int size) {
        log.debug("Получен запрос на получение бронирования GET /bookings.");
        return bookingService.getAllItemByUserId(userId, state, from, size);
    }
}
