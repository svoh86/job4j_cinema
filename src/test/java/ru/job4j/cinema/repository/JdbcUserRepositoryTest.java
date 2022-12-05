package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class JdbcUserRepositoryTest {
    @BeforeEach
    private void before() {
        try (Connection connection = new Main().loadPool().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    private void after() {
        try (Connection connection = new Main().loadPool().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenAddUserAndFindByEmail() {
        UserRepository userRepository = new JdbcUserRepository(new Main().loadPool());
        User user = new User(1, "username", "email", "phone");
        userRepository.add(user);
        User userDB = userRepository.findUserByEmailOrPhone("username", "email", null).get();
        assertThat(userDB).isEqualTo(user);
    }

    @Test
    void whenAddUserAndFindByPhone() {
        UserRepository userRepository = new JdbcUserRepository(new Main().loadPool());
        User user = new User(2, "newUsername", "newEmail", "newPhone");
        userRepository.add(user);
        User userDB = userRepository.findUserByEmailOrPhone("newUsername", null, "newPhone").get();
        assertThat(userDB).isEqualTo(user);
    }

    @Test
    void whenNotAddUser() {
        UserRepository userRepository = new JdbcUserRepository(new Main().loadPool());
        User user = new User(1, "username", "mail", "phone");
        User user2 = new User(2, "username2", "mail", "phone2");
        userRepository.add(user);
        assertThatThrownBy(() -> userRepository.add(user2).get()).isInstanceOf(NoSuchElementException.class);
    }
}