package model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Observable;

@Getter
@Setter
public class Grid extends Observable implements Runnable {
    public final int SIZE_X = 20;
    public final int SIZE_Y = 20;
    private final Cell activeCell = new Cell.CellBuilder().grid(this).coordinate(new Point(5, 5)).build();

    public Grid() {
        new Scheduler(this).start();
    }

    @Override
    public void run() {
        activeCell.run();
        setChanged();
        notifyObservers();
    }

    public void action() {
        activeCell.action();
    }

    public boolean isCoordinateValid(Point coordinate) {
        return (coordinate.getY() >= 0 && coordinate.getY() < SIZE_Y);
    }
}
