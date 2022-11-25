package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionControllerTest {
    private final Model model = mock(Model.class);
    private final SessionService sessionService = mock(SessionService.class);
    private final SeatService seatService = mock(SeatService.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final SessionController sessionController = new SessionController(sessionService, seatService);

    @Test
    public void whenIndex() {
        List<Session> sessions = Arrays.asList(
                new Session(1, "first", "19-00"),
                new Session(2, "second", "20-00"));
        when(sessionService.findAll()).thenReturn(sessions);
        String page = sessionController.index(model, httpSession);
        verify(model).addAttribute("sessions", sessions);
        assertThat(page).isEqualTo("index");
    }

    @Test
    public void whenCinemaHallRows() {
        List<Seat> seats = new ArrayList<>();
        List<Integer> rows = List.of(1, 2, 3, 4);
        List<Integer> cells = List.of(1, 2, 3, 4, 5, 6);
        when(httpSession.getAttribute("user")).thenReturn(
                new User(1, "name", "email", "phone"));
        when(seatService.getAllRows()).thenReturn(rows);
        when(seatService.getAllCells()).thenReturn(cells);
        when(seatService.showChosenSeats(1, 2)).thenReturn(seats);
        String page = sessionController.cinemaHallRows(model, httpSession, 1);
        verify(model).addAttribute("rows", rows);
        verify(model).addAttribute("cells", cells);
        verify(model).addAttribute("seats", seats);
        assertThat(page).isEqualTo("cinemaHall");
    }

    @Test
    public void whenChoiceSeat() {
        Seat seat = new Seat(2, 3);
        when(httpSession.getAttribute("sessionId")).thenReturn(3);
        when(httpSession.getAttribute("user")).thenReturn(
                new User(1, "name", "email", "phone"));
        String page = sessionController.choiceSeat(seat, httpSession);
        verify(seatService).add(1, 3, seat);
        assertThat(page).isEqualTo("redirect:/index/3");
    }
}