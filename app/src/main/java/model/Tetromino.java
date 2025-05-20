package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    public Tetromino previewAction(Action action) {
        Tetromino preview = this.copy();

        switch (action) {
            case LEFT -> preview.position.x += -1;
            case RIGHT -> preview.position.x += 1;
            case SOFT_DROP -> preview.position.y += 1;
            case UP -> preview.position.y += -1;
            case CLOCKWISE -> preview.shape.rotateClockwise();
            case COUNTER_CLOCKWISE -> preview.shape.rotateCounterClockwise();
            default -> System.err.println("No action associated with" + action);
        }
        return preview;
    }

    public void applyAction(Action action) {
        switch (action) {
            case LEFT -> this.position.x += -1;
            case RIGHT -> this.position.x += 1;
            case SOFT_DROP -> this.position.y += 1;
            case UP -> this.position.y += -1;
            case CLOCKWISE -> this.shape.rotateClockwise();
            case COUNTER_CLOCKWISE -> this.shape.rotateCounterClockwise();
            default -> System.err.println("No action associated with" + action);
        }
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
