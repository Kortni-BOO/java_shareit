package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemStorage itemStorage, UserService userService, UserMapper userMapper, ItemMapper itemMapper) {
        this.itemStorage = itemStorage;
        this.userService = userService;
        this.userMapper = userMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto create(ItemDto itemDto, long id) {
        Item item = itemStorage.create(itemMapper.toItem(itemDto), userMapper.toUser(userService.getById(id)));
        return itemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(ItemDto itemDto, long userId, long itemId) {
        itemStorage.checkItemId(itemId);
        if (itemStorage.checkOwner(userId, itemId)) {
            throw new UserNotFoundException("Пользователь не имеет доступа к этой вещи");
        }
        Item item = itemStorage.update(itemMapper.toItem(itemDto), userId, itemId);
        return itemMapper.toItemDto(item);
    }

    @Override
    public void delete(Item item) {
        itemStorage.delete(item);
    }

    @Override
    public List<ItemDto> getAllByUserId(long id) {
        return itemStorage.getAllByUserId(id).stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(long id) {
        userService.getById(id);
        return itemMapper.toItemDto(itemStorage.getById(id));
    }

    @Override
    public List<ItemDto> searchItemByQuery(String text) {
        return itemStorage.getItemsByQuery(text).stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }
}