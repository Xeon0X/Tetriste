package view;

import model.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Observable;
import java.util.Observer;

public class MatrixView extends JPanel implements Observer {
    private final static int CELL_PIXEL_SIZE = 16;
    private final static int ARC_SIZE = 3;
    private final Matrix matrix;
    Canvas canvas;

    public MatrixView(Matrix _matrix) {
        matrix = _matrix;
        setLayout(new BorderLayout());
        setBackground(Color.black);
        Dimension dimension = new Dimension(CELL_PIXEL_SIZE * matrix.SIZE_X, CELL_PIXEL_SIZE * matrix.SIZE_Y);

        canvas = new Canvas() {
            public void paint(Graphics g) {
                for (int x = 0; x < matrix.SIZE_X; x++) {
                    for (int y = 0; y < matrix.SIZE_Y; y++) {
                        g.setColor(Color.WHITE);
                        g.fillRect(x * CELL_PIXEL_SIZE, y * CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRoundRect(x * CELL_PIXEL_SIZE,
                                        y * CELL_PIXEL_SIZE,
                                        CELL_PIXEL_SIZE,
                                        CELL_PIXEL_SIZE,
                                        ARC_SIZE,
                                        ARC_SIZE);
                    }
                }
                g.setColor(Color.BLUE);
                g.fillRect(matrix.getActiveBlock().getCoordinate().x * CELL_PIXEL_SIZE,
                           matrix.getActiveBlock().getCoordinate().y * CELL_PIXEL_SIZE,
                           CELL_PIXEL_SIZE,
                           CELL_PIXEL_SIZE);
            }
        };

        canvas.setPreferredSize(dimension);
        add(canvas, BorderLayout.CENTER);
    }

    @Override
    public void update(Observable observable, Object arg) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy(); // bs + dispose + show : double buffering to avoid flickering
        if (bufferStrategy == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();
        canvas.paint(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }
}