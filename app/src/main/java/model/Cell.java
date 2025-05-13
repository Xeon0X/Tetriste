package model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@Builder
public class Cell implements Runnable {
    @NonNull
    private Grid grid;
    private Point coordinate;
    @Builder.Default
    private Point nextMove = new Point(0, 0);
    private Color color;

    @Override
    public void run() {
        Point nextCoordinate = new Point(coordinate.x + nextMove.x, coordinate.y + nextMove.y);
        if (grid.isCoordinateValid(nextCoordinate)) {
            coordinate = nextCoordinate;
        }
    }

    public void action() {
        nextMove.y = -1;
    }
}
