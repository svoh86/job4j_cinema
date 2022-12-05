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
public class SimpleUserService implements UserService {
    final UserRepository userRepository;

    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    @Override
    public Optional<User> findUserByEmailOrPhone(String username, String email, String phone) {
        return userRepository.findUserByEmailOrPhone(username, email, phone);
    }
}
