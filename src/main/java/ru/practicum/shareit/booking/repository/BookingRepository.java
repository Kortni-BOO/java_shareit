package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    /*Параметр state необязательный и по умолчанию равен ALL (англ. «все»).
    Также он может принимать значения
    CURRENT (англ. «текущие»),
    **PAST** (англ. «завершённые»),
    FUTURE (англ. «будущие»),
    WAITING (англ. «ожидающие подтверждения»),
    REJECTED (англ. «отклонённые»).*/

    //получение всех бронирований пользователя отсортированных по дате от новых к старым ALL
    List<Booking> findAllByBookerIdOrderByStartDesc(long bookerId);

    //получение всех текущих бронирований пользователя отсортированных по дате от новых к старым CURRENT
    List<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long bookerId, LocalDateTime start, LocalDateTime end);

    //получение всех завершённых бронирований пользователя отсортированных по дате от новых к старым PAST
    List<Booking> findAllByBookerIdAndEndIsBeforeOrderByStartDesc(long bookerId, LocalDateTime end);

    //получение всех будущих бронирований пользователя по дате от новых к старым FUTURE
    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStartDesc(long bookerId, LocalDateTime start);

    //получение всех бронирований пользователя по статусу отсортированных по дате от новых к старым WAITING
    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(long bookerId, Status status);


    //получение бронирований для всех вещей пользователя отсортированных по дате от новых к старым ALL
    List<Booking> findAllByItemOwnerIdOrderByStartDesc(long bookerId);


    //получение всех текущих бронирований для всех вещей пользователя отсортированных по дате от новых к старым CURRENT
    List<Booking> findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long bookerId, LocalDateTime start, LocalDateTime end);

    //получение всех завершённых бронирований для всех вещей пользователя отсортированных по дате от новых к старым PAST
    List<Booking> findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(long bookerId, LocalDateTime end);

    //получение всех будущих бронирований для всех вещей пользователя по дате от новых к старым FUTURE
    List<Booking> findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(long bookerId, LocalDateTime start);

    //получение всех бронирований для всех вещей пользователя по статусу отсортированных по дате от новых к старым WAITING
    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(long bookerId, Status status);

   //("SELECT b FROM Booking AS b WHERE b.item.id = ?1 AND b.end < ?2 ORDER BY b.end DESC")

    Booking findFirstByBooker_Id(long bookerId);

}
