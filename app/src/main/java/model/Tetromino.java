package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Tetromino {

    // The position of the top left corner of the tetromino on a 3x3 or 4x4 grid.
    private Point position;

    private Shape shape;

    public Tetromino copy() {
        return new Tetromino.TetrominoBuilder().position(new Point(position)).shape(new Shape(shape)).build();
    }

    public Tetromino precompute(Action action) {
        Tetromino precompute = this.copy();

        switch (action) {
            case LEFT -> precompute.position.x += -1;
            case RIGHT -> precompute.position.x += 1;
            case SOFT_DROP -> precompute.position.y += 1;
            case UP -> precompute.position.y += -1;
            case CLOCKWISE -> precompute.shape.rotateClockwise();
            case COUNTER_CLOCKWISE -> precompute.shape.rotateCounterClockwise();
            default -> System.err.println("No action associated with" + action);
        }
        return precompute;
    }

    public Tetromino applyAction(Action action) {
        switch (action) {
            case LEFT -> this.position.x += -1;
            case RIGHT -> this.position.x += 1;
            case SOFT_DROP -> this.position.y += 1;
            case UP -> this.position.y += -1;
            case CLOCKWISE -> this.shape.rotateClockwise();
            case COUNTER_CLOCKWISE -> this.shape.rotateCounterClockwise();
            default -> System.err.println("No action associated with" + action);
        }
        return this;
    }

    public List<Point> getMinos() {
        int size = shape.getSize();
        List<Point> minos = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (shape.getPattern()[x][y]) {
                    minos.add(new Point(position.x + x, position.y + y));
                }
            }
        }
        return minos;
    }
}
