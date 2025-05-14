package model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        if (isValidPosition(nextCoordinate)) {
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
                System.out.println("hard_drop");
            }
            case UP -> {
                nextMove.y = -1;
                System.out.println("up");
            }
        }
        run();
    }

    public boolean canMoveInDirection(Direction direction) {
        Point nextPos = new Point(coordinate);
        switch (direction) {
            case LEFT -> nextPos.x -= 1;
            case RIGHT -> nextPos.x += 1;
            case SOFT_DROP -> nextPos.y += 1;
            case UP -> nextPos.y -= 1;
        }
        return isValidPosition(nextPos);
    }

    private boolean isValidPosition(Point position) {
        int size = shape.getSize();
        boolean[][] pattern = shape.getPattern();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (pattern[y][x]) {
                    int worldX = position.x + x;
                    int worldY = position.y + y;

                    if (matrix.isCellOccupied(worldX, worldY)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<Point> getCoordinates() {
        int size = shape.getSize();
        List<Point> coordinates = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (shape.getPattern()[y][x]) {
                    coordinates.add(
                            new Point(coordinate.x + x, coordinate.y + y)
                    );
                }
            }
        }
        return coordinates;
    }
}