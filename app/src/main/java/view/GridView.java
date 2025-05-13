package view;

import model.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Observable;
import java.util.Observer;

public class GridView extends JPanel implements Observer {
    private final static int CELL_PIXEL_SIZE = 16;
    private final static int ARC_SIZE = 3;
    private final Grid grid;
    Canvas canvas;

    public GridView(Grid _grid) {
        grid = _grid;
        setLayout(new BorderLayout());
        setBackground(Color.black);
        Dimension dimension = new Dimension(CELL_PIXEL_SIZE * grid.SIZE_X, CELL_PIXEL_SIZE * grid.SIZE_Y);

        canvas = new Canvas() {
            public void paint(Graphics g) {
                for (int x = 0; x < grid.SIZE_X; x++) {
                    for (int y = 0; y < grid.SIZE_Y; y++) {
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
                g.fillRect(grid.getActiveCell().getCoordinate().x * CELL_PIXEL_SIZE,
                           grid.getActiveCell().getCoordinate().y * CELL_PIXEL_SIZE,
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