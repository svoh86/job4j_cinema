package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.util.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Контроллер юзеров
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает страницу добавления (регистрации) нового пользователя.
     * Добавляет в модель атрибуты для валидации данных (fail, success).
     *
     * @param model       Model
     * @param fail        флаг, что пользователь уже существует.
     * @param success     флаг, что регистрация успешна.
     * @param httpSession HttpSession
     * @return addUser
     */
    @GetMapping("/addUser")
    public String addUser(Model model,
                          @RequestParam(name = "fail", required = false) Boolean fail,
                          @RequestParam(name = "success", required = false) Boolean success,
                          HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("fail", fail != null);
        model.addAttribute("success", success != null);
        return "addUser";
    }

    /**
     * Добавляет данные из формы в Optional<User>.
     *
     * @param user пользователь
     * @return сообщение об успешной/неуспешной регистрации.
     */
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/addUser?fail=true";
        }
        return "redirect:/addUser?success=true";
    }

    /**
     * Отображает страницу авторизации пользователя.
     * Добавляет в модель атрибуты для валидации данных (fail).
     *
     * @param model Model
     * @param fail  флаг, что входные данные невалидные.
     * @return login
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * Ищет пользователя в БД. Если его там нет, то возвращает страницу с параметром fail=true.
     * Иначе переходит на начальную страницу.
     * Получает объект httpSession из запроса и устанавливает ей параметр "user".
     *
     * @param user пользователь
     * @param req  запрос
     * @return index или loginPage?fail=true.
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDB = userService.findUserByEmailOrPhone(
                user.getUserName(), user.getEmail(), user.getPhone());
        if (userDB.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userDB.get());
        return "redirect:/index";
    }

    /**
     * Выходит из сессии.
     *
     * @param session HttpSession
     * @return станицу авторизации пользователя.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
