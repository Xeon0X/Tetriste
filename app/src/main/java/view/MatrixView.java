package view;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.Matrix;

public class MatrixView extends JPanel implements Observer {

    private static final int CELL_PIXEL_SIZE = 16;
    private static final int ARC_SIZE = 3;
    private final Matrix matrix;
    Canvas canvas;

    public MatrixView(Matrix _matrix) {
        matrix = _matrix;
        setLayout(new BorderLayout());
        setBackground(Color.black);
        Dimension dimension = new Dimension(
            CELL_PIXEL_SIZE * matrix.SIZE_X,
            CELL_PIXEL_SIZE * matrix.SIZE_Y
        );

        canvas = new Canvas() {
            public void paint(Graphics g) {
                // Draw the grid
                for (int x = 0; x < matrix.SIZE_X; x++) {
                    for (int y = 0; y < matrix.SIZE_Y; y++) {
                        g.setColor(Color.WHITE);
                        g.fillRect(
                            x * CELL_PIXEL_SIZE,
                            y * CELL_PIXEL_SIZE,
                            CELL_PIXEL_SIZE,
                            CELL_PIXEL_SIZE
                        );
                        g.setColor(Color.BLACK);
                        g.drawRoundRect(
                            x * CELL_PIXEL_SIZE,
                            y * CELL_PIXEL_SIZE,
                            CELL_PIXEL_SIZE,
                            CELL_PIXEL_SIZE,
                            ARC_SIZE,
                            ARC_SIZE
                        );
                    }
                }

                // Draw the Tetromino full shape
                int shapeSize = matrix
                    .getActiveTetromino()
                    .getShape()
                    .getSize();
                g.setColor(Color.RED);
                g.drawRoundRect(
                    matrix.getActiveTetromino().getCoordinate().x *
                    CELL_PIXEL_SIZE,
                    matrix.getActiveTetromino().getCoordinate().y *
                    CELL_PIXEL_SIZE,
                    shapeSize * CELL_PIXEL_SIZE,
                    shapeSize * CELL_PIXEL_SIZE,
                    ARC_SIZE,
                    ARC_SIZE
                );

                g.setColor(Color.BLUE);
                for (Point coordinate : matrix
                    .getActiveTetromino()
                    .getCoordinates()) {
                    g.fillRect(
                        coordinate.x * CELL_PIXEL_SIZE,
                        coordinate.y * CELL_PIXEL_SIZE,
                        CELL_PIXEL_SIZE,
                        CELL_PIXEL_SIZE
                    );
                }
                g.setColor(Color.RED);
                g.drawRoundRect(
                    matrix.getActiveTetromino().getCoordinate().x *
                    CELL_PIXEL_SIZE,
                    matrix.getActiveTetromino().getCoordinate().y *
                    CELL_PIXEL_SIZE,
                    CELL_PIXEL_SIZE,
                    CELL_PIXEL_SIZE,
                    ARC_SIZE,
                    ARC_SIZE
                );
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
