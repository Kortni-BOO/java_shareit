package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.service.ItemRequestServiceImpl;
import ru.practicum.shareit.user.UserController;

import java.util.List;

/**
 * // TODO .
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestServiceImpl service;
    private static final String USER_HEADER = "X-Sharer-User-Id";
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ItemRequestDto create(@RequestHeader(USER_HEADER) long id,
                                 @RequestBody ItemRequestDto requestDto) {
        log.debug("Получен запрос на создание запроса POST /requests.");
        return service.create(requestDto);
    }

    @GetMapping("/all")
    public List<ItemRequestDto>  getAllRequests(@RequestHeader(USER_HEADER) long id,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "20") int size) {
        log.debug("Получен запрос GET /requests/all.");
        return service.getAllRequests(id, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@RequestHeader(USER_HEADER) long id,
                                         @PathVariable long requestId) {
        log.debug("Получен запрос GET /requests.");
        return service.getRequestByUserId(id, requestId);
    }

    @GetMapping
    public List<ItemRequestDto> getRequestsByUserId(@RequestHeader(USER_HEADER) long id) {
        log.debug("Получен запрос GET /requests.");
        return service.getRequestsByUserId(id);
    }
}
