package controller;

import model.Action;
import model.Matrix;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MatrixController {

    private final Matrix matrix;

    public MatrixController(Matrix matrix) {
        this.matrix = matrix;
        setupKeyListener();
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
