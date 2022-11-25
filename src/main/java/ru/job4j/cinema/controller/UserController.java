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

    @GetMapping("/addUser")
    public String addUser(Model model,
                          @RequestParam(name = "fail", required = false) Boolean fail,
                          @RequestParam(name = "success", required = false) Boolean success) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("success", success != null);
        return "addUser";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/addUser?fail=true";
        }
        return "redirect:/addUser?success=true";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
