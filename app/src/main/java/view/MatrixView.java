package view;

import model.Matrix;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MatrixView extends JPanel implements Observer {
    private final Matrix matrix;
    private final Color[] colors = {
            Color.CYAN, Color.BLUE, Color.ORANGE,
            Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.RED
    };
    private int cellSize;

    public MatrixView(Matrix matrix) {
        this.matrix = matrix;
        setBackground(Color.WHITE);
        calculateCellSize();
    }

    private void calculateCellSize() {
        Dimension size = getSize();
        int width = size.width;
        int height = size.height;

        if (width == 0 || height == 0) {
            cellSize = 20;
            return;
        }

        int cellSizeX = (width - 20) / matrix.SIZE_X;
        int cellSizeY = (height - 20) / matrix.SIZE_Y;

        cellSize = Math.max(10, Math.min(cellSizeX, cellSizeY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculateCellSize();

        int gridWidth = cellSize * matrix.SIZE_X;
        int gridHeight = cellSize * matrix.SIZE_Y;

        int startX = (getWidth() - gridWidth) / 2;
        int startY = (getHeight() - gridHeight) / 2;

        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= matrix.SIZE_X; x++) {
            g.drawLine(startX + x * cellSize, startY,
                    startX + x * cellSize, startY + gridHeight);
        }
        for (int y = 0; y <= matrix.SIZE_Y; y++) {
            g.drawLine(startX, startY + y * cellSize,
                    startX + gridWidth, startY + y * cellSize);
        }

        for (int x = 0; x < matrix.SIZE_X; x++) {
            for (int y = 0; y < matrix.SIZE_Y; y++) {
                if (matrix.isCellOccupied(x, y)) {
                    g.setColor(Color.BLACK);
                    g.fillRect(startX + x * cellSize + 1,
                            startY + y * cellSize + 1,
                            cellSize - 1, cellSize - 1);
                }
            }
        }

        if (matrix.getActiveTetromino() != null) {
            g.setColor(colors[matrix.getActiveTetromino().getShape().ordinal() % colors.length]);
            for (Point p : matrix.getActiveTetromino().getCoordinates()) {
                g.fillRect(startX + p.x * cellSize + 1,
                        startY + p.y * cellSize + 1,
                        cellSize - 1, cellSize - 1);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        calculateCellSize();
        return new Dimension(matrix.SIZE_X * cellSize + 20,
                matrix.SIZE_Y * cellSize + 20);
    }
}