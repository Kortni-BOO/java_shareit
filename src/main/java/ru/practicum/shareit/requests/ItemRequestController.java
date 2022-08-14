package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.service.ItemRequestServiceImpl;

import java.util.List;

/**
 * // TODO .
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestServiceImpl service;


    @PostMapping
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") long id,
                                 @RequestBody ItemRequestDto requestDto) {
        return service.create(requestDto);
    }

    @GetMapping("/all")
    public List<ItemRequestDto>  getAllRequests(@RequestHeader("X-Sharer-User-Id") long id,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "20") int size) {
        return service.getAllRequests(id, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@RequestHeader("X-Sharer-User-Id") long id,
                                         @PathVariable long requestId) {
        return service.getRequestByUserId(id, requestId);
    }

    @GetMapping
    public List<ItemRequestDto> getRequestsByUserId(@RequestHeader("X-Sharer-User-Id") long id) {
        return service.getRequestsByUserId(id);
    }
}
