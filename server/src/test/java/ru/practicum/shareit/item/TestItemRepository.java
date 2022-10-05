package ru.practicum.shareit.item;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TestItemRepository {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    User user = new User(1l, "Kis", "kis@mail.ru");

    Item itemDto1 = new Item(
            1L,
            "Отвертка хорошая",
            "Крестовая",
            true,
            user,
            null);

    Item itemDto2 = new Item(
            2L,
            "Отвертка",
            "Крестовая, для закручивания устройств соединений с крестовой шляпкой",
            true,
            user,
            null);



    @Test
    void search() {

        userRepository.save(user);
        itemRepository.save(itemDto1);

        itemRepository.save(itemDto2);

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(0, 5, sort);

        List<Item> items = itemRepository.search("отв", page);

        assertThat(items).isNotEmpty();
        assertThat(items).hasSize(2);

    }
}
