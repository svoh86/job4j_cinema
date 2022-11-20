package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Seat;

import java.util.*;


/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Repository
public class SeatRepository {
    private final Set<Seat> seats = new HashSet<>();
    private final Map<Integer, Map<Integer, Set<Seat>>> chosen;

    public SeatRepository(Map<Integer, Map<Integer, Set<Seat>>> chosen) {
        this.chosen = chosen;
        seats.add(new Seat(1, 1));
        seats.add(new Seat(1, 2));
        seats.add(new Seat(1, 3));
        seats.add(new Seat(1, 4));
        seats.add(new Seat(1, 5));
        seats.add(new Seat(1, 6));
        seats.add(new Seat(2, 1));
        seats.add(new Seat(2, 2));
        seats.add(new Seat(2, 3));
        seats.add(new Seat(2, 4));
        seats.add(new Seat(2, 5));
        seats.add(new Seat(2, 6));
        seats.add(new Seat(3, 1));
        seats.add(new Seat(3, 2));
        seats.add(new Seat(3, 3));
        seats.add(new Seat(3, 4));
        seats.add(new Seat(3, 5));
        seats.add(new Seat(3, 6));
        seats.add(new Seat(4, 1));
        seats.add(new Seat(4, 2));
        seats.add(new Seat(4, 3));
        seats.add(new Seat(4, 4));
        seats.add(new Seat(4, 5));
        seats.add(new Seat(4, 6));
    }

    public List<Integer> getAllRows() {
        return seats.stream()
                .map(Seat::getRow)
                .sorted()
                .distinct()
                .toList();
    }

    public List<Integer> getAllCells() {
        return seats.stream()
                .map(Seat::getCell)
                .sorted()
                .distinct()
                .toList();
    }

    public Map<Integer, Map<Integer, Set<Seat>>> add(int userId, int sessionId, Seat seat) {
        if (!chosen.containsKey(userId)) {
            Map<Integer, Set<Seat>> mapSeats = new HashMap<>();
            HashSet<Seat> setSeats = new HashSet<>();
            setSeats.add(seat);
            mapSeats.put(sessionId, setSeats);
            chosen.put(userId, mapSeats);
        } else if (!chosen.get(userId).containsKey(sessionId)) {
            Map<Integer, Set<Seat>> mapSeats = chosen.get(userId);
            HashSet<Seat> setSeats = new HashSet<>();
            setSeats.add(seat);
            mapSeats.put(sessionId, setSeats);
        } else {
            Set<Seat> seats = chosen.get(userId).get(sessionId);
            seats.add(seat);
        }
        return chosen;
    }

    public List<Seat> showChosenSeats(int userId, int sessionId) {
        if (!chosen.containsKey(userId) || !chosen.get(userId).containsKey(sessionId)) {
            return new ArrayList<>();
        }
        return chosen.get(userId).get(sessionId)
                .stream()
                .sorted()
                .toList();
    }
}
