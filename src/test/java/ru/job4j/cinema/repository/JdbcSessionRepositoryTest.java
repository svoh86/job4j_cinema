package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class JdbcSessionRepositoryTest {
    @BeforeEach
    private void before() {
        try (Connection connection = new Main().loadPool().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM sessions")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    private void after() {
        try (Connection connection = new Main().loadPool().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM sessions")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddAndFindAll() {
        SessionRepository sessionRepository = new JdbcSessionRepository(new Main().loadPool());
        Session session = new Session(1, "name", "time");
        Session session2 = new Session(2, "name2", "time2");
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);
        sessionList.add(session2);
        sessionRepository.add(session);
        sessionRepository.add(session2);
        assertThat(sessionList).isEqualTo(sessionRepository.findAll());
    }

    @Test
    public void whenAddAndFindById() {
        SessionRepository sessionRepository = new JdbcSessionRepository(new Main().loadPool());
        Session session = new Session(1, "name", "time");
        Session session2 = new Session(2, "name2", "time2");
        sessionRepository.add(session);
        sessionRepository.add(session2);
        Session byId = sessionRepository.findById(session2.getId());
        assertThat(session2).isEqualTo(byId);
    }
}