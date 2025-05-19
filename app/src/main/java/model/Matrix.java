package model;

import java.awt.*;
import java.util.Observable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Matrix extends Observable implements Runnable {

    public final int SIZE_X;
    public final int SIZE_Y;
    private boolean[][] matrix;
    private Scheduler scheduler;
    private Tetromino activeTetromino;
    private int score;

    @Builder
    public Matrix(int sizeX, int sizeY) {
        this.SIZE_X = sizeX > 0 ? sizeX : 10;
        this.SIZE_Y = sizeY > 0 ? sizeY : 20;
        this.matrix = new boolean[SIZE_X][SIZE_Y];
        this.score = 0;

        this.scheduler = new Scheduler(this);
        this.scheduler.start();

        spawnNewTetromino();

        setChanged();
        notifyObservers();
    }

    @Override
    public void run() {
        System.out.println("gravity");
        try {
            // Gravity
            Tetromino preview = this.getActiveTetromino().previewAction(Action.SOFT_DROP);
            if (this.isPositionValid(preview)) {
                this.getActiveTetromino().applyAction(Action.SOFT_DROP);
            } else {
                validateTetromino();
                spawnNewTetromino();
            }
            setChanged();
            notifyObservers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveTetromino(Action action) {
        Tetromino preview = this.getActiveTetromino().previewAction(action);
        if (this.isPositionValid(preview)) {
            this.getActiveTetromino().applyAction(action);
        }
        setChanged();
        notifyObservers();
    }

    private void spawnNewTetromino() {
        this.activeTetromino = new Tetromino.TetrominoBuilder()
            .position(new Point(SIZE_X / 2 - 2, 0))
            .shape(new Shape(ShapeLetter.values()[(int) (Math.random() * ShapeLetter.values().length)]))
            .build();

        if (isGameOver()) {
            setChanged();
            notifyObservers("GAME_OVER");
        }
    }

    private void validateTetromino() {
        for (Point p : activeTetromino.getMinos()) {
            if (p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y) {
                matrix[p.x][p.y] = true;
            }
        }
        checkAndClearLines();
    }

    private void checkAndClearLines() {
        int linesCleared = 0;
        for (int y = SIZE_Y - 1; y >= 0; y--) {
            boolean isLineComplete = true;
            for (int x = 0; x < SIZE_X; x++) {
                if (!matrix[x][y]) {
                    isLineComplete = false;
                    break;
                }
            }

            if (isLineComplete) {
                clearLine(y);
                linesCleared++;
                y++;
            }
        }

        if (linesCleared > 0) {
            updateScoreLine(linesCleared);
        }
    }

    private void clearLine(int lineY) {
        for (int y = lineY; y > 0; y--) {
            for (int x = 0; x < SIZE_X; x++) {
                matrix[x][y] = matrix[x][y - 1];
            }
        }

        for (int x = 0; x < SIZE_X; x++) {
            matrix[x][0] = false;
        }
        setChanged();
        notifyObservers();
    }

    private void updateScoreLine(int linesCleared) {
        switch (linesCleared) {
            case 1 -> updateScore(100);
            case 2 -> updateScore(300);
            case 3 -> updateScore(500);
            case 4 -> updateScore(800);
        }
    }

    private void updateScore(int score) {
        this.score += score;
    }

    public boolean isCellOccupied(Point point) {
        System.out.println(point);
        return matrix[point.x][point.y];
    }

    public boolean isInMatrix(Point point) {
        return (point.x >= 0 && point.x < SIZE_X && point.y >= 0 && point.y < SIZE_Y);
    }

    public boolean isPositionValid(Point point) {
        if (isInMatrix(point)) {
            return !isCellOccupied(point);
        }
        return false;
    }

    public boolean isPositionValid(Tetromino tetromino) {
        for (Point point : tetromino.getMinos()) {
            if (!isPositionValid(point)) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        for (Point coordinate : activeTetromino.getMinos()) {
            int x = coordinate.x;
            int y = coordinate.y;

            if (matrix[x][y]) {
                return true;
            }
        }
        return false;
    }
}
