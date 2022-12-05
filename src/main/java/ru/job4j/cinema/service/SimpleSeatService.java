package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.repository.SeatRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Service
public class SimpleSeatService implements SeatService {
    final SeatRepository seatRepository;

    public SimpleSeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Integer> getAllRows() {
        return seatRepository.getAllRows();
    }

    @Override
    public List<Integer> getAllCells() {
        return seatRepository.getAllCells();
    }

    @Override
    public Map<Integer, Map<Integer, Set<Seat>>> add(int userId, int sessionId, Seat seat) {
        return seatRepository.add(userId, sessionId, seat);
    }

    @Override
    public List<Seat> showChosenSeats(int userId, int sessionId) {
        return seatRepository.showChosenSeats(userId, sessionId);
    }

    @Override
    public void deleteFromChosen(int userId, int sessionId, Seat seat) {
        seatRepository.deleteFromChosen(userId, sessionId, seat);
    }
}
