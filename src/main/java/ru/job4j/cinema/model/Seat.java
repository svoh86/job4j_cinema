package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных места в зале
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public class Seat implements Comparable<Seat> {
    private int row;
    private int cell;

    public Seat() {
    }

    public Seat(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        return row == seat.row && cell == seat.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, cell);
    }

    @Override
    public int compareTo(Seat anotherSeat) {
        if (this.row > anotherSeat.row) {
            return 1;
        }
        if (this.row == anotherSeat.row && this.cell > anotherSeat.cell) {
            return 1;
        }
        return -1;
    }
}
