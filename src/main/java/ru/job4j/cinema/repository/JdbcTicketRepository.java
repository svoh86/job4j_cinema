package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Хранилище билетов
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Repository
public class JdbcTicketRepository implements TicketRepository {
    private final BasicDataSource pool;
    private static final Logger LOG = Logger.getLogger(JdbcTicketRepository.class);
    private static final String ADD = "INSERT INTO ticket(session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_TICKET_BY_USER_ID = "SELECT * FROM ticket WHERE user_id = ?";

    public JdbcTicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Отправляет SQL запрос в БД.
     * Добавляет билет в БД.
     *
     * @param sessionId id сеанса фильма
     * @param posRow    ряд
     * @param cell      сиденье
     * @param userId    id пользователя
     * @return Optional билета
     */
    @Override
    public Optional<Ticket> add(int sessionId, int posRow, int cell, int userId) {
        Optional<Ticket> ticketDB = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     ADD, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, sessionId);
            statement.setInt(2, posRow);
            statement.setInt(3, cell);
            statement.setInt(4, userId);
            statement.execute();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    Ticket ticket = new Ticket(
                            rs.getInt("id"),
                            sessionId,
                            posRow,
                            cell,
                            userId);
                    ticketDB = Optional.of(ticket);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method add()", e);
        }
        return ticketDB;
    }

    /**
     * Отправляет SQL запрос в БД.
     * Ищет список билетов по id пользователя.
     *
     * @param id id пользователя
     * @return список билетов
     */
    @Override
    public List<Ticket> findTicketByUserId(int id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TICKET_BY_USER_ID)
        ) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    tickets.add(getTicket(rs));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findTicketByUserId()", e);
        }
        return tickets;
    }

    private Ticket getTicket(ResultSet rs) throws SQLException {
        return new Ticket(
                rs.getInt("id"),
                rs.getInt("session_id"),
                rs.getInt("pos_row"),
                rs.getInt("cell"),
                rs.getInt("user_id")
        );
    }
}
