package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shape {

    private boolean[][] pattern;
    private int size;
    private ShapeLetter letter;

    public Shape(boolean[][] _pattern) {
        for (int x = 0; x < size; x++) {
            System.arraycopy(_pattern[x], 0, pattern[x], 0, size);
        }
    }

    public Shape(Shape shape) {
        this.pattern = shape.getPattern();
        this.size = shape.getSize();
        this.letter = shape.getLetter();
    }

    public Shape(ShapeLetter letter) {
        switch (letter) {
            case I -> {
                pattern = new boolean[][] {
                    { false, false, false, false },
                    { true, true, true, true },
                    { false, false, false, false },
                    { false, false, false, false },
                };
                size = 4;
                this.letter = ShapeLetter.I;
            }
            case O -> {
                pattern = new boolean[][] {
                    { false, false, false, false },
                    { false, true, true, false },
                    { false, true, true, false },
                    { false, false, false, false },
                };
                size = 4;
                this.letter = ShapeLetter.O;
            }
            case T -> {
                pattern = new boolean[][] { { false, true, false }, { true, true, true }, { false, false, false } };
                size = 3;
                this.letter = ShapeLetter.T;
            }
            case L -> {
                pattern = new boolean[][] { { false, false, true }, { true, true, true }, { false, false, false } };
                size = 3;
                this.letter = ShapeLetter.L;
            }
            case J -> {
                pattern = new boolean[][] { { true, false, false }, { true, true, true }, { false, false, false } };
                size = 3;
                this.letter = ShapeLetter.J;
            }
            case S -> {
                pattern = new boolean[][] { { false, true, true }, { true, true, false }, { false, false, false } };
                size = 3;
                this.letter = ShapeLetter.J;
            }
            case Z -> {
                pattern = new boolean[][] { { true, true, false }, { false, true, true }, { false, false, false } };
                size = 3;
                this.letter = ShapeLetter.J;
            }
        }
    }

    public void rotateClockwise() {
        int size = this.pattern.length;
        boolean[][] rotatedPattern = new boolean[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                rotatedPattern[x][y] = this.pattern[y][size - x - 1];
            }
        }
        this.pattern = rotatedPattern;
    }

    public void rotateCounterClockwise() {
        int size = this.pattern.length;
        boolean[][] rotatedPattern = new boolean[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                rotatedPattern[x][y] = this.pattern[size - y - 1][x];
            }
        }
        this.pattern = rotatedPattern;
    }
}
