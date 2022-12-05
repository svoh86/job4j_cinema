package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Service
public class SimpleSessionService implements SessionService {
    final SessionRepository sessionRepository;

    public SimpleSessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Session findById(int id) {
        return sessionRepository.findById(id);
    }
}
