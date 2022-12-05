package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Хранилище сеансов фильмов.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Repository
public class JdbcSessionRepository implements SessionRepository {
    private final BasicDataSource pool;
    private static final Logger LOG = LogManager.getLogger(JdbcSessionRepository.class);
    private static final String FIND_ALL = "SELECT * FROM sessions";
    private static final String FIND_BY_ID = "SELECT * FROM sessions where id = ?";
    private static final String ADD = "INSERT INTO sessions(name, time) VALUES(?, ?)";

    public JdbcSessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Отправляет SQL запрос в БД.
     * Ищет все сеансы фильмов.
     *
     * @return список сеансов фильмов
     */
    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    sessions.add(
                            new Session(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("time")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findAll()", e);
        }
        return sessions;
    }

    /**
     * Отправляет SQL запрос в БД.
     * Ищет сеанс фильма по id.
     *
     * @param id id сеанса фильма
     * @return сеанс фильма
     */
    @Override
    public Session findById(int id) {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Session(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("time")
                );
            }
        } catch (Exception e) {
            LOG.error("Exception in method findById()", e);
        }
        return null;
    }

    /**
     * Отправляет SQL запрос в БД.
     * Добавляет сеанс фильма в БД.
     *
     * @param session сеанс фильма
     * @return сеанс фильма
     */
    @Override
    public Session add(Session session) {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, session.getName());
            statement.setString(2, session.getTime());
            statement.execute();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    session.setId(rs.getInt("id"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method add()", e);
        }
        return session;
    }
}
