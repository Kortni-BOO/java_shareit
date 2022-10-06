package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private static final String USER_HEADER = "X-Sharer-User-Id";
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(USER_HEADER) long userId,
                                                @RequestBody @Valid ItemRequestDto requestDto) {
        log.info("Post requests , userId={}", userId);
        return itemRequestClient.createRequest(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequestByUserId(@RequestHeader(USER_HEADER) long userId) {
        log.info("Get requests , userId={}", userId);
        return itemRequestClient.getRequestByUserId(userId);
    }

    @GetMapping("/{all}")
    public ResponseEntity<Object> getAllRequest(@RequestHeader(USER_HEADER) long userId,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "20") int size) {
        log.info("Get all requests , userId={}", userId);
        return itemRequestClient.getAllRequest(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader(USER_HEADER) long userId,
                                                 @PathVariable long requestId) {
        log.info("Get requests/{} , userId={}", requestId, userId);
        return itemRequestClient.getRequestById(userId, requestId);
    }
}
