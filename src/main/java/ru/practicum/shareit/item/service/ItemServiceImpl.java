package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemStorage;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;
    private final BookingRepository bookingRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemStorage, UserService userService,
                           UserMapper userMapper, ItemMapper itemMapper,
                           BookingRepository bookingRepository, CommentMapper commentMapper,
                           CommentRepository commentRepository) {
        this.itemStorage = itemStorage;
        this.userService = userService;
        this.userMapper = userMapper;
        this.itemMapper = itemMapper;
        this.bookingRepository = bookingRepository;
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public ItemDto getById(long id) {
        Item item = itemStorage.findById(id).orElseThrow(() -> {
                    throw  new ObjectNotFoundException(String.format("Сущность с id %d не найдена", id));
        });
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto create(ItemDto itemDto, long id) {
        User user = userMapper.toUser(userService.getById(id));
        itemDto.setOwner(user);
        Item item = itemStorage.save(itemMapper.toItem(itemDto));
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(ItemDto itemDto, long userId, long itemId) {
        Item item = itemMapper.toItem(itemDto);
        checkOwner(item, userId);
        Item itemUpdate = itemStorage.save(item);
        return itemMapper.toItemDto(itemUpdate);
    }

    @Override
    public void delete(Item item) {
        itemStorage.deleteById(item.getId());
    }

    @Override
    public List<ItemOwnerDto> getAllByUserId(long id) {
        //bookingService.getBookingId(id);
        return itemStorage.findAllByOwnerId(id)
                .stream()
                .map(this::setBooking)
                .collect(Collectors.toList());

    }


    public ItemOwnerDto setBooking(Item item) {
        Booking booking = bookingRepository.findFirstByBooker_Id(item.getOwner().getId());
        return itemMapper.toItemOwnerDto(item, booking);
    }

    @Override
    public List<ItemDto> searchItemByQuery(String text) {
        return itemStorage.search(text).stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }

    public void checkOwner(Item item, long userId) {
        if (item.getOwner().getId() != userId) {
            throw new ObjectNotFoundException("Вещь не принадлежит пользователю");
        }
    }

    @Override
    public CommentDto createComment(long id, long itemId, CommentDto commentDto) {
        User user = userMapper.toUser(userService.getById(id));
        Item item = itemMapper.toItem(getById(itemId));
        Comment comment = commentMapper.toComment(commentDto);
        comment.setItem(item);
        comment.setAuthor(user);
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }


}