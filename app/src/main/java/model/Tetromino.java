package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Tetromino implements Runnable {

    @NonNull
    private Matrix matrix;

    private Point coordinate; // top, left

    @Builder.Default
    private Point nextMove = new Point(0, 0);

    private Shape shape;

    @Override
    public void run() {
        Point nextCoordinate = new Point(
            coordinate.x + nextMove.x,
            coordinate.y + nextMove.y
        );
        if (matrix.isCoordinateValid(nextCoordinate)) {
            coordinate = nextCoordinate;
        }
        nextMove = new Point(0, 0);
    }

    public void action(Direction direction) {
        switch (direction) {
            case LEFT -> {
                nextMove.x = -1;
                System.out.println("left");
            }
            case RIGHT -> {
                nextMove.x = 1;
                System.out.println("right");
            }
            case SOFT_DROP -> {
                nextMove.y = 1;
                System.out.println("soft_drop");
            }
            case HARD_DROP -> {
                nextMove.y = matrix.SIZE_Y - coordinate.y - 1;
                System.out.println("test" + (matrix.SIZE_Y - coordinate.y));
            }
            case UP -> {
                nextMove.y = -1;
                System.out.println("up");
            }
        }
        run();
    }

    public List<Point> getCoordinates() {
        int size = shape.getSize();
        List<Point> coordinates = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (shape.getPattern()[x][y]) {
                    coordinates.add(
                        new Point(coordinate.x + x, coordinate.y + y)
                    );
                }
            }
        }
        return coordinates;
    }
}
