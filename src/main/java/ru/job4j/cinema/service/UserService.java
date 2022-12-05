package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * Интерфейс для сервиса пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */

public interface UserService {
    Optional<User> add(User user);

    Optional<User> findUserByEmailOrPhone(String username, String email, String phone);
}
