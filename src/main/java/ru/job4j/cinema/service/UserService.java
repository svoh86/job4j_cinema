package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

/**
 * Класс сервиса пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    public Optional<User> findUserByEmailOrPhone(String username, String email, String phone) {
        return userRepository.findUserByEmailOrPhone(username, email, phone);
    }
}
