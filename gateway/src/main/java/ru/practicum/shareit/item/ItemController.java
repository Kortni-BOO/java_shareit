package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private static final String USER_HEADER = "X-Sharer-User-Id";
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(USER_HEADER) long id,
                                             @RequestBody @Valid ItemDto itemDto) {
        log.info("Post items , userId={}", id);
        return itemClient.createItem(itemDto, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestHeader(USER_HEADER) long userId,
                                             @RequestBody ItemDto itemDto,
                                             @PathVariable long id) {
        log.info("Patch items {}, userId={}", id, userId);
        return itemClient.updateItem(itemDto, userId, id);
    }

    //getAllByUserId(long id, int from, int size)
    @GetMapping()
    public ResponseEntity<Object> getAllByUserId(@RequestHeader(USER_HEADER) long userId,
                                                 @RequestParam(name = "from",defaultValue = "0") int from,
                                                 @RequestParam(name = "size",defaultValue = "20") @Positive int size) {
        log.info("Get items userId={}", userId);
        return itemClient.getAllByUserId(userId, from, size);
    }

    //getById(long id)
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@RequestHeader(USER_HEADER) long userId,
                                          @PathVariable long id) {
        log.info("Get items , itemId={}", id);
        return itemClient.getById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam("text") String text,
                                         @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                         @RequestParam(name = "size", defaultValue = "20") @Positive int size) {
        log.info("Get items search");
        return itemClient.search(text, from, size);
    }

    //createComment(long id, long itemId, CommentDto commentDto)
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(USER_HEADER) long userId,
                                                @RequestBody @Valid CommentDto commentDto,
                                                @PathVariable long itemId) {
        log.info("Post items/{}/comment, userId={}", itemId, userId);
        return itemClient.createComment(userId, itemId, commentDto);
    }
}
