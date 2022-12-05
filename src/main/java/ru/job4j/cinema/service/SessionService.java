package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.List;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface SessionService {
    List<Session> findAll();

    Session findById(int id);
}
