package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class User {
    /**
     * Идентификатор пользователя
     */
    private int id;
    /**
     * Имя пользователя
     */
    private String userName;
    /**
     * почта пользователя
     */
    private String email;
    /**
     * телефон пользователя
     */
    private String phone;

    public User() {
    }

    public User(int id, String username, String email, String phone) {
        this.id = id;
        this.userName = username;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phone);
    }
}
