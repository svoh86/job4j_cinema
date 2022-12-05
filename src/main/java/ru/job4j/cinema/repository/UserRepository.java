package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * Интерфейс для хранилища пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface UserRepository {

    Optional<User> add(User user);

    Optional<User> findUserByEmailOrPhone(String username, String email, String phone);
}
