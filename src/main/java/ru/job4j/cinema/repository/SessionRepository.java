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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Repository
public class SessionRepository {
    private final BasicDataSource pool;
    private static final Logger LOG = LogManager.getLogger(SessionRepository.class);
    private static final String FIND_ALL = "SELECT * FROM sessions";
    private static final String FIND_BY_ID = "SELECT * FROM sessions where id = ?";

    public SessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

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
}
