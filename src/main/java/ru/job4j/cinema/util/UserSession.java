package ru.job4j.cinema.util;

import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

/**
 * Служебный класс.
 * Добавляет в модель атрибут "user".
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class UserSession {
    public static void getUser(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость!");
        }
        model.addAttribute("user", user);
    }
}
