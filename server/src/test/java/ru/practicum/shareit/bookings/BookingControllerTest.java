package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.PostBookingDto;
import ru.practicum.shareit.booking.enums.State;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {
    @Mock
    private BookingServiceImpl service;

    @InjectMocks
    private BookingController controller;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    User user = new User(1l, "Kis", "kis@mail.ru");
    private Item item = new Item(
            1L,
            "Отвертка",
            "Крестовая",
            true,
            user,
            null);

    private PostBookingDto postBookingDto = new PostBookingDto(
            1L,
            LocalDateTime.parse(LocalDateTime.of(2022, 3, 1, 10, 11)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))),
            LocalDateTime.parse(LocalDateTime.of(2022, 3, 1, 19, 11)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))),
            item.getId()
    );

    private BookingDto bookingDto = new BookingDto(
            1L,
            LocalDateTime.parse(LocalDateTime.of(2022, 3, 1, 10, 11)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))),
            LocalDateTime.parse(LocalDateTime.of(2022, 3, 1, 19, 11)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))),
            item,
            user,
            Status.REJECTED
    );

    @Test
    void saveNewItem() throws Exception {
        String bookingJson = "{\"itemId\": 2, \"start\": \"2022-03-01T10:11:00\",\"end\": \"2022-03-01T19:11:00\"}";
        when(service.create(any(), anyLong()))
                .thenReturn(postBookingDto);

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1L)
                        .content(bookingJson)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSetApproved() throws Exception {
        mvc.perform(patch("/bookings/{bookingId}", "1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("approved", "true"))
                .andExpect(status().isOk());
        verify(service, Mockito.times(1)).setApproved(1L, true);
    }

    @Test
    void testGetItemByUserId() throws Exception {
        when(service.getById(anyLong()))
                .thenReturn(bookingDto);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class));
    }

    @Test
    void testGetAllBookingByUserId() throws Exception {
        when(service.getAllBookingByUserId(anyLong(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("size", "1")
                        .param("from", "1")
                        .param("state", "ALL"))
                .andExpect(status().isOk());


        verify(service, times(1)).getAllBookingByUserId(1L, State.ALL, 1, 1);
    }

    @Test
    void testGetAllItemByUserId() throws Exception {
        when(service.getAllItemByUserId(anyLong(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("size", "1")
                        .param("from", "1")
                        .param("state", "ALL"))
                .andExpect(status().isOk());


        verify(service, times(1)).getAllItemByUserId(1L, State.ALL, 1, 1);
    }
}
