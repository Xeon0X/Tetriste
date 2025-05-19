package controller;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.*;
import model.Action;
import model.Matrix;
import model.Tetromino;
import view.MatrixView;

public class MatrixController extends JFrame {

    static long lastTime = System.currentTimeMillis();
    private final Executor ex = Executors.newSingleThreadExecutor();
    private final JTextField textField = new JTextField("");
    private final Matrix matrix;

    public MatrixController(Matrix _matrix) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        matrix = _matrix;

        setSize(400, 400);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new BorderLayout());
        textField.setFocusable(false);
        panel.add(textField, BorderLayout.NORTH);

        MatrixView matrixView = new MatrixView(matrix);
        matrix.addObserver(matrixView);

        panel.add((JPanel) matrixView, BorderLayout.CENTER);
        setContentPane(panel);

        setResizable(true);
        addComponentListener(
            new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    ((MatrixView) matrixView).repaint();
                }
            }
        );

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(
                new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
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
                    }
                }
            );
    }
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(
    //         new Runnable() {
    //             public void run() {
    //                 Matrix currentMatrix = Matrix.builder().sizeX(10).sizeY(20).build();
    //                 MatrixController matrixController = new MatrixController(currentMatrix);
    //                 matrixController.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //                 matrixController.setVisible(true);
    //             }
    //         }
    //     );
    // }
    // @Override
    // public void update(Observable observable, Object arg) {
    //     if ("GAME_OVER".equals(arg)) {
    //         showGameOverScreen();
    //         return;
    //     }

    //     SwingUtilities.invokeLater(
    //         new Runnable() {
    //             public void run() {
    //                 textField.setText(
    //                     "Elapsed time : " +
    //                     (System.currentTimeMillis() - lastTime) +
    //                     "ms - x = " +
    //                     matrix.getActiveTetromino().getPosition().x +
    //                     " y = " +
    //                     matrix.getActiveTetromino().getPosition().y
    //                 );
    //                 lastTime = System.currentTimeMillis();
    //             }
    //         }
    //     );
    // }

    // private void showGameOverScreen() {
    //     dispose();
    //     SwingUtilities.invokeLater(() -> {
    //         GameOverController gameOverController = new GameOverController(matrix.getScore());
    //         gameOverController.setVisible(true);
    //     });
    // }
}
