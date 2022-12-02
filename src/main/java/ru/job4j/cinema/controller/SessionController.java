package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.util.UserSession;

import javax.servlet.http.HttpSession;

/**
 * Контроллер сеансов
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Controller
public class SessionController {
    private final SessionService sessionService;
    private final SeatService seatService;

    public SessionController(SessionService sessionService, SeatService seatService) {
        this.sessionService = sessionService;
        this.seatService = seatService;
    }

    /**
     * Показывает основную страницу с сеансами фильмов.
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @return index
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("sessions", sessionService.findAll());
        return "index";
    }

    /**
     * Показывает страницу с конкретным сеансом фильма.
     * Пользователь выбирает ряд и место. Бронирует.
     * Отображается список выбранных билетов.
     * При нажатии кнопки "Купить" переходим в корзину.
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @param sessionId   id сеанса фильма
     * @return cinemaHall
     */
    @GetMapping("/index/{sessionsID}")
    public String cinemaHallRows(Model model, HttpSession httpSession, @PathVariable("sessionsID") int sessionId) {
        UserSession.getUser(model, httpSession);
        User user = (User) httpSession.getAttribute("user");
        int userId = user.getId();
        httpSession.setAttribute("sessionId", sessionId);
        model.addAttribute("rows", seatService.getAllRows());
        model.addAttribute("cells", seatService.getAllCells());
        model.addAttribute("seats", seatService.showChosenSeats(userId, sessionId));
        return "cinemaHall";
    }

    /**
     * Добавляет данные из формы в карту, где ключ - id пользователя,
     * значение - карта с id сеанса фильма и списком выбранных мест.
     *
     * @param seat        Место
     * @param httpSession HttpSession
     * @return страницу выбора места
     */
    @PostMapping("/choiceSeat")
    public String choiceSeat(@ModelAttribute Seat seat, HttpSession httpSession) {
        int sessionId = (int) httpSession.getAttribute("sessionId");
        User user = (User) httpSession.getAttribute("user");
        int userId = user.getId();
        seatService.add(userId, sessionId, seat);
        return "redirect:/index/" + sessionId;
    }

    /**
     * Обрабатывает запрос на удаление места из списка выбранных
     *
     * @param row         ряд
     * @param cell        место
     * @param httpSession httpSession
     * @return страницу выбора места
     */
    @GetMapping("/deleteSeat/{seatRow}/{seatCell}")
    public String deleteSeat(@PathVariable("seatRow") int row,
                             @PathVariable("seatCell") int cell,
                             HttpSession httpSession) {
        int sessionId = (int) httpSession.getAttribute("sessionId");
        User user = (User) httpSession.getAttribute("user");
        int userId = user.getId();
        Seat seat = new Seat(row, cell);
        seatService.deleteFromChosen(userId, sessionId, seat);
        return "redirect:/index/" + sessionId;
    }
}
