package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных сеансов
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Session {
    /**
     * Идентификатор сеанса
     */
    private int id;
    /**
     * Название сеанса
     */
    private String name;
    /**
     * Время сеанса
     */
    private String time;

    public Session() {
    }

    public Session(int id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
