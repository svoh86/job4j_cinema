package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Seat;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Интерфейс для хранилища мест в зале
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface SeatRepository {
    List<Integer> getAllRows();

    List<Integer> getAllCells();

    Map<Integer, Map<Integer, Set<Seat>>> add(int userId, int sessionId, Seat seat);

    List<Seat> showChosenSeats(int userId, int sessionId);

    void deleteFromChosen(int userId, int sessionId, Seat seat);
}
