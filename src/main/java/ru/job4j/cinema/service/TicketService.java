package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */

public interface TicketService {
    Optional<Ticket> add(int sessionId, int posRow, int cell, int userId);

    List<Ticket> findTicketByUserId(int id);
}
