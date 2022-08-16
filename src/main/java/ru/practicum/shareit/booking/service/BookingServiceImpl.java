package ru.practicum.shareit.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.PostBookingDto;
import ru.practicum.shareit.booking.enums.State;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final UserService userService;
    private final ItemService itemService;
    private final BookingRepository repository;

    private final UserMapper userMapper = new UserMapper();
    private final ItemMapper itemMapper = new ItemMapper();
    private final BookingMapper bookingMapper = new BookingMapper();

    @Autowired
    public BookingServiceImpl(UserService userService,
                              ItemService itemService,
                              BookingRepository repository) {
        this.userService = userService;
        this.itemService = itemService;
        this.repository = repository;
    }

    @Override
    public BookingDto getById(long bookingId) {
        Booking booking = repository.findById(bookingId).orElseThrow(() -> {
            throw  new ObjectNotFoundException(String.format("Сущность с id %d не найдена", bookingId));
        });
        return bookingMapper.toBookingDto(booking);
    }

    @Override
    public PostBookingDto create(PostBookingDto bookingDto, long userId) {
        ItemDto item = itemService.getById(bookingDto.getItemId());
        UserDto user = userService.getById(userId);
        Booking booking = bookingMapper.toBooking(bookingDto);
        booking.setBooker(userMapper.toUser(user));
        booking.setStatus(Status.WAITING);
        booking.setItem(itemMapper.toItem(item));
        return bookingMapper.toBookingPostDto(repository.save(booking));
    }

    @Override
    public BookingDto setApproved(long bookingId, boolean approved) {
        Booking booking = bookingMapper.toBooking(getById(bookingId));
        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        return bookingMapper.toBookingDto(repository.save(booking));
    }


    @Override
    public List<BookingDto> getAllBookingByUserId(long userId, State state, int from, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "start");
        Pageable page = PageRequest.of(from, size, sort);
        switch (state) {
            case ALL:
                 return bookingMapper.toBookingDtoList(repository.findAllByBookerIdOrderByStartDesc(userId, page));
            case CURRENT:
                return bookingMapper.toBookingDtoList(repository.findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
                        userId,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        page));
            case PAST:
                return bookingMapper.toBookingDtoList(repository.findAllByBookerIdAndEndIsBeforeOrderByStartDesc(userId,
                        LocalDateTime.now(),
                        page));
            case FUTURE:
                return bookingMapper.toBookingDtoList(repository.findAllByBookerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now(), page));
            case WAITING:
                return bookingMapper.toBookingDtoList(repository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING, page));
            case REJECTED:
                return bookingMapper.toBookingDtoList(repository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED, page));
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public List<BookingDto> getAllItemByUserId(long userId, State state, int from, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "start");
        Pageable page = PageRequest.of(from, size, sort);
        switch (state) {
            case ALL:
                return bookingMapper.toBookingDtoList(repository.findAllByItemOwnerIdOrderByStartDesc(userId, page));
            case CURRENT:
                return bookingMapper.toBookingDtoList(repository.findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
                        userId,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        page));
            case PAST:
                return bookingMapper.toBookingDtoList(repository.findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(userId, LocalDateTime.now(), page));
            case FUTURE:
                return bookingMapper.toBookingDtoList(repository.findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now(), page));
            case WAITING:
                return bookingMapper.toBookingDtoList(repository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.WAITING, page));
            case REJECTED:
                return bookingMapper.toBookingDtoList(repository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.REJECTED, page));
            default:
                return Collections.emptyList();
        }
    }
}
