package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Класс репозиторий пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Repository
public class UserRepository {
    private final BasicDataSource pool;
    private static final Logger LOG = Logger.getLogger(UserRepository.class);
    private static final String ADD = "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)";
    private static final String FIND_USER_BY_EMAIL_OR_PHONE = "SELECT * FROM users WHERE username = ? AND (email = ? OR phone = ?)";

    public UserRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> userDB = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     ADD, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.execute();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                    userDB = Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method add()", e);
        }
        return userDB;
    }

    public Optional<User> findUserByEmailOrPhone(User user) {
        Optional<User> userDB = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_OR_PHONE)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    userDB = Optional.of(new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in method findUserByEmailOrPhone()", e);
        }
        return userDB;
    }
}
