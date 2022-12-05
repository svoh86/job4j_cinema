package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Seat;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface SeatService {
    List<Integer> getAllRows();

    List<Integer> getAllCells();

    Map<Integer, Map<Integer, Set<Seat>>> add(int userId, int sessionId, Seat seat);

    List<Seat> showChosenSeats(int userId, int sessionId);

    void deleteFromChosen(int userId, int sessionId, Seat seat);
}
