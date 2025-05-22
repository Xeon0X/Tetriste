package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public class Matrix extends Observable implements Runnable {

    public final int SIZE_X;
    public final int SIZE_Y;
    private final Object pauseLock = new Object();
    private boolean[][] matrix;
    private Scheduler scheduler;
    private Tetromino activeTetromino;
    private Tetromino nextTetromino;
    private Tetromino previewTetromino;
    private int score;
    private int level;
    private int linesCleared = 0;
    private boolean gameover = false;
    private AtomicBoolean paused = new AtomicBoolean(false);

    @Builder
    public Matrix(int sizeX, int sizeY, boolean[][] matrix) {
        this.SIZE_X = sizeX > 0 ? sizeX : 10;
        this.SIZE_Y = sizeY > 0 ? sizeY : 20;
        this.matrix = new boolean[SIZE_X][SIZE_Y];
        for (int x = 0; x < SIZE_X; x++) {
            // System.out.println("x: " + x + " y: " + y + " value: " + matrix[x][y]);
            System.arraycopy(matrix[x], 0, this.matrix[x], 0, SIZE_Y);
        }
        this.score = 0;

        this.scheduler = new Scheduler(this);
        this.scheduler.start();

        this.nextTetromino = new Tetromino.TetrominoBuilder()
                .position(new Point(SIZE_X + 4, 0))
                .shape(new Shape(ShapeLetter.values()[(int) (Math.random() * ShapeLetter.values().length)]))
                .build();

        spawnNewTetromino();

        setChanged();
        notifyObservers();
    }

    @Override
    public void run() {
        if (gameover) {
            return;
        }

        synchronized (pauseLock) {
            if (paused.get()) {
                try {
                    pauseLock.wait();
                    return;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        try {
            if (!testAndApplyMoveTetromino(Action.SOFT_DROP)) {
                validateTetromino();
                spawnNewTetromino();
            }
            setChanged();
            notifyObservers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePreview() {
        previewTetromino.setPosition(activeTetromino.getPosition());
        previewTetromino.setShape(activeTetromino.getShape());
        previewTetromino.setPosition(computeLowestCoordinate().position);
    }

    public void togglePause() {
        synchronized (pauseLock) {
            boolean wasPaused = paused.getAndSet(!paused.get());
            if (wasPaused) {
                pauseLock.notifyAll();
                setChanged();
            } else {
                setChanged();
            }
        }
    }

    public void moveTetromino(Action action) {
        switch (action) {
            case HARD_DROP -> {
                DropResult dropResult = computeLowestCoordinate();
                this.activeTetromino.setPosition(dropResult.position);
                updateScore(dropResult.dropDepth * 2);
                validateTetromino();
                spawnNewTetromino();
            }
            case SOFT_DROP -> {
                if (testAndApplyMoveTetromino(action)) {
                    updateScore(1);
                }
            }
            default -> {
                testAndApplyMoveTetromino(action);
            }
        }
        updatePreview();
        setChanged();
        notifyObservers();
    }

    public boolean testAndApplyMoveTetromino(Action action) {
        if (this.isPositionValid(this.getActiveTetromino().precompute(action))) {
            this.getActiveTetromino().applyAction(action);
            return true;
        } else if ((action == Action.COUNTER_CLOCKWISE) || (action == Action.CLOCKWISE)) {
            if (this.isPositionValid(this.getActiveTetromino().precompute(action).precompute(Action.LEFT))) {
                this.getActiveTetromino().applyAction(action).applyAction(Action.LEFT);
                return true;
            }
            if (this.isPositionValid(this.getActiveTetromino().precompute(action).precompute(Action.RIGHT))) {
                this.getActiveTetromino().applyAction(action).applyAction(Action.RIGHT);
                return true;
            }
            // Shape I
            if (this.getActiveTetromino().getShape().getSize() == 4) {
                if (
                        this.isPositionValid(
                                this.getActiveTetromino().precompute(action).precompute(Action.LEFT).precompute(Action.LEFT)
                        )
                ) {
                    this.getActiveTetromino().applyAction(action).applyAction(Action.LEFT).applyAction(Action.LEFT);
                    return true;
                }
                if (
                        this.isPositionValid(
                                this.getActiveTetromino()
                                        .precompute(action)
                                        .precompute(Action.RIGHT)
                                        .precompute(Action.RIGHT)
                        )
                ) {
                    this.getActiveTetromino().applyAction(action).applyAction(Action.RIGHT).applyAction(Action.RIGHT);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public DropResult computeLowestCoordinate() {
        Tetromino precompute = this.getActiveTetromino().copy();
        int dropDepth = 0;
        while (this.isPositionValid(precompute.precompute(Action.SOFT_DROP))) {
            precompute.applyAction(Action.SOFT_DROP);
            dropDepth++;
        }
        return new DropResult(precompute.getPosition(), dropDepth);
    }

    private void spawnNewTetromino() {
        if (!this.gameover) {
            this.activeTetromino = new Tetromino.TetrominoBuilder()
                    .position(new Point(SIZE_X / 2 - 2, 0))
                    .shape(new Shape(nextTetromino.getShape()))
                    .build();
            this.nextTetromino = new Tetromino.TetrominoBuilder()
                    .position(new Point(SIZE_X + 4, 0))
                    .shape(new Shape(ShapeLetter.values()[(int) (Math.random() * ShapeLetter.values().length)]))
                    .build();
            this.previewTetromino = activeTetromino.copy();
            updatePreview();
        }

        if (!isPositionValid(activeTetromino)) {
            this.gameover = true;
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
        this.linesCleared += 1;
        updateLevel();
        setChanged();
        notifyObservers();
    }

    private void updateScoreLine(int linesCleared) {
        // System.out.println(score);
        switch (linesCleared) {
            case 1 -> updateScore(100);
            case 2 -> updateScore(300);
            case 3 -> updateScore(500);
            case 4 -> updateScore(800);
        }
    }

    private void updateLevel() {
        while (this.linesCleared > this.level) {
            this.linesCleared -= this.level;
            this.level += 1;
            double intervalSec = Math.max(0.016, 1 - (this.level * 0.2));
            this.scheduler.interval = (long) (intervalSec * 1000);
            setChanged();
            notifyObservers();
        }
    }

    private void updateScore(int score) {
        this.score += score;
    }

    public boolean isCellOccupied(Point point) {
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

    public boolean isPaused() {
        return this.paused.get();
    }

    public record DropResult(Point position, int dropDepth) {
    }
}
