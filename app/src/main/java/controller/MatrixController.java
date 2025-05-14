package controller;

import model.Direction;
import model.Matrix;
import view.MatrixView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MatrixController extends JFrame implements Observer {
    static long lastTime = System.currentTimeMillis();
    private final Executor ex = Executors.newSingleThreadExecutor();
    private final JTextField textField = new JTextField("");
    private final JButton button = new JButton("do");
    private final Matrix matrix;
    private final Observer matrixView;

    public MatrixController(Matrix _matrix) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        matrix = _matrix;

        setSize(400, 400);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.NORTH);
        panel.add(button, BorderLayout.SOUTH);

        matrixView = new MatrixView(matrix);

        panel.add((JPanel) matrixView, BorderLayout.CENTER);
        setContentPane(panel);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ex.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("button clicked");
                    }
                });
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            switch (e.getKeyCode()) {
                                case KeyEvent.VK_LEFT -> matrix.action(Direction.LEFT);
                                case KeyEvent.VK_RIGHT -> matrix.action(Direction.RIGHT);
                                case KeyEvent.VK_DOWN -> matrix.action(Direction.SOFT_DROP);
                                case KeyEvent.VK_UP -> matrix.action(Direction.UP);
                                case KeyEvent.VK_SPACE -> matrix.action(Direction.HARD_DROP);
                            }
                        }
                        return false;
                    }
                });


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {
                                           Matrix currentMatrix = new Matrix();
                                           MatrixController matrixController = new MatrixController(currentMatrix);
                                           matrixController.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                           currentMatrix.addObserver(matrixController);
                                           matrixController.setVisible(true);
                                       }
                                   }
        );
    }

    @Override
    public void update(Observable observable, Object arg) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                matrixView.update(observable, arg);
                textField.setText("Elapsed time : " + (System.currentTimeMillis() - lastTime) + "ms - x = " + matrix.getActiveBlock().getCoordinate().x + " y = " + matrix.getActiveBlock().getCoordinate().y);
                lastTime = System.currentTimeMillis();
            }
        });
    }
}
