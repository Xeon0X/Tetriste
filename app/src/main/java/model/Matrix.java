package model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Observable;

@Getter
@Setter
public class Matrix extends Observable implements Runnable {
    public final int SIZE_X = 20;
    public final int SIZE_Y = 20;
    private final Block activeBlock = new Block.BlockBuilder().matrix(this).coordinate(new Point(5, 5)).build();

    public Matrix() {
        new Scheduler(this).start();
    }

    @Override
    public void run() {
        activeBlock.run();
        setChanged();
        notifyObservers();
    }

    public void action(Direction direction) {
        activeBlock.action(direction);
        setChanged();
        notifyObservers();
    }

    public boolean isCoordinateValid(Point coordinate) {
        return ((coordinate.getY() >= 0 && coordinate.getY() < SIZE_Y)
                && ((coordinate.getX() >= 0 && coordinate.getX() < SIZE_X)));
    }
}
