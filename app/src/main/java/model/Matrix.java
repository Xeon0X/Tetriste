package model;

import java.awt.*;
import java.util.Observable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Matrix extends Observable implements Runnable {

    public final int SIZE_X = 20;
    public final int SIZE_Y = 20;
    private final Tetromino activeTetromino = new Tetromino.TetrominoBuilder()
        .matrix(this)
        .coordinate(new Point(5, 5))
        .shape(Shape.I)
        .build();

    public Matrix() {
        new Scheduler(this).start();
    }

    @Override
    public void run() {
        activeTetromino.run();
        setChanged();
        notifyObservers();
    }

    public void action(Direction direction) {
        activeTetromino.action(direction);
        setChanged();
        notifyObservers();
    }

    public boolean isCoordinateValid(Point coordinate) {
        return (
            (coordinate.getY() >= 0 && coordinate.getY() < SIZE_Y) &&
            ((coordinate.getX() >= 0 && coordinate.getX() < SIZE_X))
        );
    }
}
