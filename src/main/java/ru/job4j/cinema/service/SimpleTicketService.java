package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Controller
public class SimpleTicketService implements TicketService {
    final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> add(int sessionId, int posRow, int cell, int userId) {
        return ticketRepository.add(sessionId, posRow, cell, userId);
    }

    @Override
    public List<Ticket> findTicketByUserId(int id) {
        return ticketRepository.findTicketByUserId(id);
    }
}
