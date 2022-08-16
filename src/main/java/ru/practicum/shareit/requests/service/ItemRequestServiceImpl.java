package ru.practicum.shareit.requests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.mapper.ItemRequestMapper;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository repository;
    private final ItemRequestMapper mapper = new ItemRequestMapper();

    @Autowired
    public ItemRequestServiceImpl(ItemRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemRequestDto create(ItemRequestDto request) {
        return mapper.toItemRequestDto(repository.save(mapper.toItemRequest(request)));
    }

    //получить список своих запросов вместе с данными об ответах на них
    @Override
    public List<ItemRequestDto> getRequestsByUserId(long userId) {
        return repository.findAllByRequesterIdOrderByCreatedDesc(userId)
                .stream()
                .map(mapper::toItemRequestDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ItemRequestDto> getAllRequests(long userId, int from, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "created");
        Pageable page = PageRequest.of(from, size, sort);
        return repository.findAllByRequesterIdNot(userId, page)
                .stream()
                .map(mapper::toItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getRequestByUserId(long userId, long requestId) {
        ItemRequest itemRequest = repository.findById(requestId).orElseThrow(() -> {
            throw  new ObjectNotFoundException(String.format("Запрос с id %d не найдена", requestId));
        });
        return mapper.toItemRequestDto(itemRequest);
    }
}
