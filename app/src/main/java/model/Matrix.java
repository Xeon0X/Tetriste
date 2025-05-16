package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Observable;

@Getter
@Setter
public class Matrix extends Observable implements Runnable {

    public final int SIZE_X;
    public final int SIZE_Y;
    private boolean[][] grid;

    private Tetromino activeTetromino;

    @Builder
    public Matrix(int sizeX, int sizeY) {
        this.SIZE_X = sizeX > 0 ? sizeX : 10;
        this.SIZE_Y = sizeY > 0 ? sizeY : 20;
        this.grid = new boolean[SIZE_X][SIZE_Y];

        spawnNewTetromino();
        new Scheduler(this).start();
    }

    private void spawnNewTetromino() {
        this.activeTetromino = new Tetromino.TetrominoBuilder()
                .matrix(this)
                .coordinate(new Point(SIZE_X / 2 - 2, 0))
                .shape(Shape.values()[(int) (Math.random() * Shape.values().length)])
                .build();

        if (isGameOver()) {
            setChanged();
            notifyObservers("GAME_OVER");
        }
    }

    @Override
    public void run() {
        if (activeTetromino.canMoveInDirection(Direction.SOFT_DROP)) {
            activeTetromino.action(Direction.SOFT_DROP);
        } else {
            validateTetromino();
            spawnNewTetromino();
        }
        setChanged();
        notifyObservers();
    }

    private void validateTetromino() {
        for (Point p : activeTetromino.getCoordinates()) {
            if (p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y) {
                grid[p.x][p.y] = true;
            }
        }
        checkAndClearLines();
    }

    private void checkAndClearLines() {
        for (int y = SIZE_Y - 1; y >= 0; y--) {
            boolean isLineComplete = true;
            for (int x = 0; x < SIZE_X; x++) {
                if (!grid[x][y]) {
                    isLineComplete = false;
                    break;
                }
            }

            if (isLineComplete) {
                clearLine(y);
                y++;
            }
        }
    }

    private void clearLine(int lineY) {
        for (int y = lineY; y > 0; y--) {
            for (int x = 0; x < SIZE_X; x++) {
                grid[x][y] = grid[x][y - 1];
            }
        }

        for (int x = 0; x < SIZE_X; x++) {
            grid[x][0] = false;
        }
    }

    public void action(Direction direction) {
        if (direction == Direction.HARD_DROP) {
            while (activeTetromino.canMoveInDirection(Direction.SOFT_DROP)) {
                activeTetromino.action(Direction.SOFT_DROP);
            }
            validateTetromino();
            spawnNewTetromino();
        } else {
            activeTetromino.action(direction);
        }
        setChanged();
        notifyObservers();
    }

    public boolean isCellOccupied(int x, int y) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
            return true;
        }
        return grid[x][y];
    }

    public boolean isGameOver() {
        for (Point coordinate : activeTetromino.getCoordinates()) {
            int x = coordinate.x;
            int y = coordinate.y;

            if (grid[x][y]) {
                return true;
            }
        }
        return false;
    }
}