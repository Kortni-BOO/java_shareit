package ru.practicum.shareit.item.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class ItemStorage {
    private HashMap<Long, Item> items = new HashMap<>();
    private long id = 0;

    private long generateId() {
        return ++id;
    }

    public Item create(Item item, User user) {
        checkData(item);
        item.setId(generateId());
        item.setOwner(user);
        items.put(item.getId(), item);
        return item;
    }

    public Item update(Item item, long userId, long itemId) {
        Item itemUp = items.get(itemId);
        if (item.getName() != null) {
            itemUp.setName(item.getName());
        }
        if (item.getAvailable() != null) {
            itemUp.setAvailable(item.getAvailable());
        }
        if (item.getDescription() != null) {
            itemUp.setDescription(item.getDescription());
        }

        items.put(itemId, itemUp);
        return items.get(itemId);
    }

    public void delete(Item item) {
        items.remove(item.getId());
    }

    public Item getById(long id) {
        if (id < 0) {
            throw new UserNotFoundException("Вещь № %d не найдена");
        }
        return items.get(id);
    }

    public List<Item> getAllByUserId(long id) {
        return items.values().stream()
                .filter(i -> i.getOwner().getId() == id)
                .collect(Collectors.toList());
    }

    public Collection<Item> getItemsByQuery(String query) {
        return items
                .values()
                .stream()
                .filter(i -> (i.getName().toLowerCase().contains(query.toLowerCase())
                        || i.getDescription().toLowerCase().contains(query.toLowerCase()))
                        && i.getAvailable())
                .collect(Collectors.toList());
    }

    public void checkData(Item item) {
        if (item.getAvailable() == null) {
            throw new ValidationException("Поле available не может быть пустым");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("Поле Description не может быть пустым");
        }
        if (item.getName().isBlank() || item.getName().isEmpty()) {
            throw new ValidationException("Поле Name не может быть пустым");
        }
    }

    public boolean checkOwner(long userId, long itemId) {
        return items.get(itemId).getOwner().getId() != userId;
    }

    public void checkItemId(long itemId) {
        if (!items.containsKey(itemId)) {
            throw new ObjectNotFoundException("Вещь № %d не найдена");
        }
    }
}
