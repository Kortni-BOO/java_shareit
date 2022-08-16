package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    @Mock
    private ItemServiceImpl itemService;

    @InjectMocks
    private ItemController controller;

    private final ObjectMapper mapper = new ObjectMapper();

    private final ItemMapper itemMapper = new ItemMapper();

    private MockMvc mvc;

    User user = new User(1l, "Kis", "kis@mail.ru");

    private ItemDto itemDto = new ItemDto(
            1L,
            "Отвертка",
            "Крестовая",
            true,
            user,
            null);

    private ItemDto itemUpdateDto = new ItemDto(
            1L,
            "Отвертка",
            "Крестовая, для закручивания устройств соединений с крестовой шляпкой",
            true,
            user,
            null);

    private ItemDto itemDto2 = new ItemDto(
            2L,
            "Утюг",
            "Гладить",
            true,
            user,
            null);

    private ItemOwnerDto itemOwner = new ItemOwnerDto(
            3L,
            "Утюг",
            "Гладить",
            true,
            user,
            null,
            null
    );

    private ItemOwnerDto itemOwner1 = new ItemOwnerDto(
            4L,
            "Утюг",
            "Гладить",
            true,
            user,
            null,
            null
    );


    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void saveNewItem() throws Exception {
        when(itemService.create(any(), anyLong()))
                .thenReturn(itemDto);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1l)
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.requesterId", is(itemDto.getRequesterId()), Long.class));
    }

    @Test
    void updateNewItem() throws Exception {
        when(itemService.update(any(), anyLong(), anyLong()))
                .thenReturn(itemUpdateDto);

        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1l)
                        .content(mapper.writeValueAsString(itemUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemUpdateDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemUpdateDto.getName())))
                .andExpect(jsonPath("$.description", is(itemUpdateDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemUpdateDto.getAvailable())))
                .andExpect(jsonPath("$.requesterId", is(itemUpdateDto.getRequesterId()), Long.class));
    }

    @Test
    void testGetById() throws Exception {
        when(itemService.getById(anyLong()))
                .thenReturn(itemDto);

        mvc.perform(get("/items/1")
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.requesterId", is(itemDto.getRequesterId()), Long.class));
    }

    @Test
    void testGetAllByUserId() throws Exception {
        when(itemService.getAllByUserId(anyLong(), anyInt(), anyInt()))
                .thenReturn(List.of(itemOwner, itemOwner1));

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1l)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("size", "0")
                        .param("from", "0"))
                .andExpect(status().isOk());
    }


}
