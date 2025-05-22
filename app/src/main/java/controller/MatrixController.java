package controller;

import model.Action;
import model.Matrix;
import view.MatrixView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;

public class MatrixController {

    private final Matrix matrix;
    private MatrixView matrixView;

    public MatrixController(Matrix matrix) {
        this.matrix = matrix;
        matrix.addObserver(this::update);
        setupKeyListener();

        this.matrixView = new MatrixView(matrix);
        this.matrixView.setGameOverHandler(this::handleGameOver);
    }

    public void update(Observable o, Object arg) {
        if (arg != null && arg.equals("GAME_OVER")) {
            matrixView.handleGameOver(matrix.getScore());
        }
    }

    private void handleGameOver(int score) {
        if (matrixView != null) {
            matrixView.close();
        }

        GameOverController gameOverController = new GameOverController(score);
    }

    private void setupKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    matrix.togglePause();
                } else if (!matrix.isPaused()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_Z -> matrix.moveTetromino(Action.UP);
                        case KeyEvent.VK_S -> matrix.moveTetromino(Action.SOFT_DROP);
                        case KeyEvent.VK_Q -> matrix.moveTetromino(Action.LEFT);
                        case KeyEvent.VK_D -> matrix.moveTetromino(Action.RIGHT);
                        case KeyEvent.VK_SPACE -> matrix.moveTetromino(Action.HARD_DROP);
                        case KeyEvent.VK_E -> matrix.moveTetromino(Action.CLOCKWISE);
                        case KeyEvent.VK_A -> matrix.moveTetromino(Action.COUNTER_CLOCKWISE);
                    }
                }
            }
            return false;
        });
    }
}