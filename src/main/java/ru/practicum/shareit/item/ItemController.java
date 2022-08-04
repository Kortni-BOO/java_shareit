package ru.practicum.shareit.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.UserController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long id,
                          @RequestBody ItemDto item) {
        log.debug("Получен запрос на создание вещи POST /items.");
        return itemService.create(item, id);

    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable int itemId,
                          @RequestBody ItemDto item) {
        log.debug("Получен запрос на обновление вещи PATCH /items/{}.", itemId);
        return itemService.update(item, userId, itemId);

    }

    @GetMapping("/{itemId}")
    public ItemDto getById(@PathVariable("itemId") long id) {
        log.debug("Получен запрос GET /items/{itemsId}.");
        return itemService.getById(id);
    }

    @GetMapping
    public List<ItemOwnerDto> getAllByUserId(@RequestHeader("X-Sharer-User-Id") long id) {
        return itemService.getAllByUserId(id);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByQuery(@RequestParam String text) {
        return itemService.searchItemByQuery(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestHeader("X-Sharer-User-Id") long id,
                                    @PathVariable long itemId,
                                    @RequestBody CommentDto commentDto) {
        return itemService.createComment(id, itemId, commentDto);
    }
}
