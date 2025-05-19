package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ShapeUtils {

    public static List<Point> createMinos(Shape shape) {
        List<Point> minos = new ArrayList<>();
        boolean[][] pattern = shape.getPattern();

        for (int y = 0; y < shape.getSize(); y++) {
            for (int x = 0; x < shape.getSize(); x++) {
                if (pattern[y][x]) {
                    minos.add(new Point(x, y));
                }
            }
        }

        return minos;
    }
}
