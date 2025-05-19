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

    // The relative coordinates of each minos, starting at (0,0) = top left corner of the tetromino.
    private List<Point> minos;

    // Initial shape of the tetromino (for color purposes).
    private Shape shape;

    public Tetromino copy() {
        return new Tetromino.TetrominoBuilder().position(new Point(position)).minos(minos).build();
    }

    public Tetromino previewAction(Action action) {
        Tetromino preview = this.copy();

        switch (action) {
            case LEFT -> preview.position.x += -1;
            case RIGHT -> preview.position.x += 1;
            case SOFT_DROP -> preview.position.y += 1;
            // case HARD_DROP -> preview.position.y += matrix.SIZE_Y - position.y - 1;
            case UP -> preview.position.y += -1;
            case CLOCKWISE -> preview.rotateClockwise();
            case COUNTER_CLOCKWISE -> preview.rotateCounterClockwise();
        }
        return preview;
    }

    public void applyAction(Action action) {
        switch (action) {
            case LEFT -> this.position.x += -1;
            case RIGHT -> this.position.x += 1;
            case SOFT_DROP -> this.position.y += 1;
            // case HARD_DROP -> this.position.y += matrix.SIZE_Y - position.y - 1;
            case UP -> this.position.y += -1;
            case CLOCKWISE -> this.rotateClockwise();
            case COUNTER_CLOCKWISE -> this.rotateCounterClockwise();
        }
    }

    public void rotateClockwise() {
        for (Point mino : this.minos) {
            int tempX = mino.x;
            mino.x = -mino.y;
            mino.y = tempX;
        }
    }

    public void rotateCounterClockwise() {
        for (Point mino : this.minos) {
            int tempX = mino.x;
            mino.x = mino.y;
            mino.y = -tempX;
        }
    }

    public List<Point> getMinos() {
        List<Point> transformedMinos = new ArrayList<>();
        for (Point mino : this.minos) {
            transformedMinos.add(new Point(mino.x + this.position.x, mino.y + this.position.y));
        }
        return transformedMinos;
    }
}
