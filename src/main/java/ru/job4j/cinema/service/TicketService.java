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
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<Ticket> add(int sessionId, int posRow, int cell, int userId) {
        return ticketRepository.add(sessionId, posRow, cell, userId);
    }

    public List<Ticket> findTicketByUserId() {
        return ticketRepository.findTicketByUserId();
    }
}
