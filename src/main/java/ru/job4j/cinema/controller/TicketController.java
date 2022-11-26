package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.UserSession;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер билетов
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@ThreadSafe
@Controller
public class TicketController {
    private final TicketService ticketService;
    private final SessionService sessionService;
    private final SeatService seatService;

    public TicketController(TicketService ticketService, SessionService sessionService, SeatService seatService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
        this.seatService = seatService;
    }

    /**
     * Показывает корзину с выбранными местами для конкретного сеанса фильма.
     * Добавляет в модель атрибуты для валидации данных (needTickets, fail, success) и
     * отображения данных о сеансе фильма, выбранных местах и занятых местах.
     *
     * @param model       Model
     * @param httpSession httpSession
     * @param needTickets флаг, что надо выбрать билеты
     * @param fail        флаг, что места заняты
     * @param success     флаг, что билеты куплены
     * @return basket
     */
    @GetMapping("/basket")
    public String basket(Model model, HttpSession httpSession,
                         @RequestParam(name = "needTickets", required = false) Boolean needTickets,
                         @RequestParam(name = "fail", required = false) Boolean fail,
                         @RequestParam(name = "success", required = false) Boolean success) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("needTickets", needTickets != null);
        model.addAttribute("fail", fail != null);
        model.addAttribute("success", success != null);
        int sessionId = (int) httpSession.getAttribute("sessionId");
        User user = (User) httpSession.getAttribute("user");
        int userId = user.getId();
        model.addAttribute("sessions", sessionService.findById(sessionId));
        model.addAttribute("seats", seatService.showChosenSeats(userId, sessionId));
        List<Ticket> failTickets = (List<Ticket>) httpSession.getAttribute("failTickets");
        model.addAttribute("failTickets", failTickets);
        return "basket";
    }

    /**
     * Пытается создать билет.
     * Если список выбранных билетов пуст, то вернет страницу с корзиной с параметром needTickets=true.
     * Если билет не добавился в БД, то он попадает в список failTickets.
     * В таком случае возвращается страница с параметром fail=true.
     * В остальных случаях возвращается страница с параметром success=true.
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @return варианты страницы basket
     */
    @PostMapping("/createTicket")
    public String createTicket(Model model, HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        int sessionId = (int) httpSession.getAttribute("sessionId");
        User user = (User) httpSession.getAttribute("user");
        int userId = user.getId();
        List<Seat> setSeats = seatService.showChosenSeats(userId, sessionId);
        if (setSeats.isEmpty()) {
            return "redirect:/basket?needTickets=true";
        }
        List<Ticket> failTickets = new ArrayList<>();
        for (Seat seat : setSeats) {
            Optional<Ticket> ticket = ticketService.add(sessionId, seat.getRow(), seat.getCell(), userId);
            if (ticket.isEmpty()) {
                failTickets.add(new Ticket(sessionId, seat.getRow(), seat.getCell(), userId));
            }
        }
        if (!failTickets.isEmpty()) {
            httpSession.setAttribute("failTickets", failTickets);
            return "redirect:/basket?fail=true";
        }
        return "redirect:/basket?success=true";
    }
}
