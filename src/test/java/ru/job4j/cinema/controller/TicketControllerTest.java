package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketControllerTest {
    private final TicketService ticketService = mock(TicketService.class);
    private final SessionService sessionService = mock(SessionService.class);
    private final SeatService seatService = mock(SeatService.class);
    private final TicketController ticketController = new TicketController(
            ticketService, sessionService, seatService);
    private final Model model = mock(Model.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @Test
    public void whenBasketSuccess() {
        when(httpSession.getAttribute("sessionId")).thenReturn(1);
        User user = new User(1, "name", "email", "phone");
        when(httpSession.getAttribute("user")).thenReturn(user);
        Session session = new Session(1, "film", "time");
        when(sessionService.findById(anyInt())).thenReturn(session);
        List<Seat> seatList = List.of(
                new Seat(1, 1),
                new Seat(1, 2)
        );
        when(seatService.showChosenSeats(anyInt(), anyInt())).thenReturn(seatList);
        String page = ticketController.basket(model, httpSession, null, null, true);
        verify(model).addAttribute("needTickets", false);
        verify(model).addAttribute("fail", false);
        verify(model).addAttribute("success", true);
        verify(model).addAttribute("sessions", session);
        verify(model).addAttribute("seats", seatList);
        verify(model).addAttribute("failTickets", null);
        assertThat(page).isEqualTo("basket");
    }

    @Test
    public void whenBasketNeedTickets() {
        when(httpSession.getAttribute("sessionId")).thenReturn(1);
        when(httpSession.getAttribute("user")).thenReturn(new User());
        String page = ticketController.basket(model, httpSession, true, null, null);
        verify(model).addAttribute("needTickets", true);
        assertThat(page).isEqualTo("basket");
    }

    @Test
    public void whenBasketFail() {
        when(httpSession.getAttribute("sessionId")).thenReturn(1);
        when(httpSession.getAttribute("user")).thenReturn(new User());
        String page = ticketController.basket(model, httpSession, null, true, null);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("basket");
    }

    @Test
    public void whenCreateTicketWithNeedsTickets() {
        when(httpSession.getAttribute("sessionId")).thenReturn(1);
        when(httpSession.getAttribute("user")).thenReturn(new User());
        when(seatService.showChosenSeats(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        String page = ticketController.createTicket(model, httpSession);
        assertThat(page).isEqualTo("redirect:/basket?needTickets=true");
    }

    @Test
    public void whenCreateTicketWithFailTickets() {
        when(httpSession.getAttribute("sessionId")).thenReturn(1);
        when(httpSession.getAttribute("user")).thenReturn(new User());
        List<Seat> seatList = List.of(
                new Seat(1, 1),
                new Seat(1, 2)
        );
        when(seatService.showChosenSeats(anyInt(), anyInt())).thenReturn(seatList);
        when(ticketService.add(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(Optional.empty());
        String page = ticketController.createTicket(model, httpSession);
        assertThat(page).isEqualTo("redirect:/basket?fail=true");
    }

    @Test
    public void whenCreateTicketWithSuccess() {
        when(httpSession.getAttribute("sessionId")).thenReturn(1);
        when(httpSession.getAttribute("user")).thenReturn(new User());
        List<Seat> seatList = List.of(
                new Seat(1, 1),
                new Seat(1, 2)
        );
        when(seatService.showChosenSeats(anyInt(), anyInt())).thenReturn(seatList);
        when(ticketService.add(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(Optional.of(new Ticket()));
        String page = ticketController.createTicket(model, httpSession);
        assertThat(page).isEqualTo("redirect:/basket?success=true");
    }


}