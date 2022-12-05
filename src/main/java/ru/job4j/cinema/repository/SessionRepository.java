package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.util.List;

/**
 * Интерфейс для хранилища сеансов фильмов.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */

public interface SessionRepository {

    List<Session> findAll();

    Session findById(int id);

    Session add(Session session);
}
