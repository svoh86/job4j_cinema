package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для Хранилища билетов
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */

public interface TicketRepository {
    Optional<Ticket> add(int sessionId, int posRow, int cell, int userId);

    List<Ticket> findTicketByUserId(int id);

}
