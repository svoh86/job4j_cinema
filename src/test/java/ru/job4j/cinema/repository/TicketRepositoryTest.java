package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class TicketRepositoryTest {
    private final TicketRepository ticketRepository = new TicketRepository(new Main().loadPool());
    private final static UserRepository USERREPOSITORY = new UserRepository(new Main().loadPool());
    private final static SessionRepository SESSIONREPOSITORY = new SessionRepository(new Main().loadPool());
    private final static User USER = new User(10, "1", "1", "1");
    private final static Session SESSION = new Session(4, "2", "2");

    @BeforeAll
    private static void beforeAll() {
        SESSIONREPOSITORY.add(SESSION);
        USERREPOSITORY.add(USER);
    }

    @AfterEach
    private void after() {
        try (Connection connection = new Main().loadPool().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM ticket");
             PreparedStatement statement2 = connection.prepareStatement("DELETE FROM sessions");
             PreparedStatement statement3 = connection.prepareStatement("DELETE FROM users")) {
            statement.execute();
            statement2.execute();
            statement3.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddAndFindByUserId() {
        Ticket ticket = new Ticket(1, SESSION.getId(), 2, 3, USER.getId());
        Optional<Ticket> optionalTicket = ticketRepository.add(
                SESSION.getId(), ticket.getPosRow(), ticket.getCell(), USER.getId());
        Ticket ticketDB = optionalTicket.get();
        ticket.setId(ticketDB.getId());
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        assertThat(ticket).isEqualTo(ticketDB);
        assertThat(ticketList).isEqualTo(ticketRepository.findTicketByUserId(USER.getId()));
    }
}