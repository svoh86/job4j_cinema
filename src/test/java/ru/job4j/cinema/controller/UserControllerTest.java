package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private final Model model = mock(Model.class);
    private final UserService userService = mock(UserService.class);
    private final UserController userController = new UserController(userService);
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    public void whenAddUserSuccess() {
        String page = userController.addUser(model, null, true);
        verify(model).addAttribute("fail", false);
        verify(model).addAttribute("success", true);
        assertThat(page).isEqualTo("addUser");
    }

    @Test
    public void whenAddUserFail() {
        String page = userController.addUser(model, true, null);
        verify(model).addAttribute("fail", true);
        verify(model).addAttribute("success", false);
        assertThat(page).isEqualTo("addUser");
    }

    @Test
    public void whenSuccessCreateUser() {
        User user = new User(1, "name", "email", "phone");
        when(userService.add(user)).thenReturn(Optional.of(user));
        String page = userController.createUser(user);
        assertThat(page).isEqualTo("redirect:/addUser?success=true");
    }

    @Test
    public void whenFailCreateUser() {
        User user = new User(1, "name", "email", "phone");
        when(userService.add(user)).thenReturn(Optional.empty());
        String page = userController.createUser(user);
        assertThat(page).isEqualTo("redirect:/addUser?fail=true");
    }

    @Test
    public void whenLoginPageSuccess() {
        String page = userController.loginPage(model, null);
        verify(model).addAttribute("fail", false);
        assertThat(page).isEqualTo("login");
    }

    @Test
    public void whenLoginPageFail() {
        String page = userController.loginPage(model, true);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("login");
    }

    @Test
    public void whenLoginSuccess() {
        User user = new User(1, "name", "email", "phone");
        when(userService.findUserByEmailOrPhone(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(user));
        when(req.getSession()).thenReturn(httpSession);
        String page = userController.login(user, req);
        verify(httpSession).setAttribute("user", Optional.of(user).get());
        assertThat(page).isEqualTo("redirect:/index");
    }

    @Test
    public void whenLoginFail() {
        User user = new User(1, "name", "email", "phone");
        when(userService.findUserByEmailOrPhone(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        String page = userController.login(user, req);
        assertThat(page).isEqualTo("redirect:/loginPage?fail=true");
    }

    @Test
    public void whenLogout() {
        String page = userController.logout(httpSession);
        assertThat(page).isEqualTo("redirect:/loginPage");
    }
}