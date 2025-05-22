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
    }

    public void update(Observable o, Object arg) {
        if (arg != null && arg.equals("GAME_OVER")) {
            matrixView.handleGameOver(matrix.getScore());
        }
    }

    public void setView(MatrixView matrixView) {
        this.matrixView = matrixView;
    }

    private void setupKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
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
            return false;
        });
    }
}
