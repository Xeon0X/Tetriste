package view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.Matrix;

public class MatrixView extends JPanel implements Observer {

    private static final float BASE_SCORE_FONT_SIZE = 16f;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private final Matrix matrix;
    private final Color[] colors = {
        Color.CYAN,
        Color.BLUE,
        Color.ORANGE,
        Color.YELLOW,
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
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

    private int calculateScaleFontSize() {
        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) return (int) BASE_SCORE_FONT_SIZE;
        float scaleFactor = Math.min((float) width / WIDTH, (float) height / HEIGHT);

        return Math.max(12, Math.round(BASE_SCORE_FONT_SIZE * scaleFactor));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculateCellSize();

        int gridWidth = cellSize * matrix.SIZE_X;
        int gridHeight = cellSize * matrix.SIZE_Y;

        int startX = (getWidth() - gridWidth) / 2;
        int startY = (getHeight() - gridHeight) / 2;

        int fontSize = calculateScaleFontSize();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, fontSize));

        int scoreX = 10;
        int scoreY1 = Math.max(30, startY / 2);
        int scoreY2 = scoreY1 + fontSize + 5;

        g.drawString("SCORE:", scoreX, scoreY1);
        g.drawString(String.valueOf(matrix.getScore()), scoreX, scoreY2);

        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= matrix.SIZE_X; x++) {
            g.drawLine(startX + x * cellSize, startY, startX + x * cellSize, startY + gridHeight);
        }
        for (int y = 0; y <= matrix.SIZE_Y; y++) {
            g.drawLine(startX, startY + y * cellSize, startX + gridWidth, startY + y * cellSize);
        }

        for (int x = 0; x < matrix.SIZE_X; x++) {
            for (int y = 0; y < matrix.SIZE_Y; y++) {
                if (matrix.isPositionValid(new Point(x, y))) {
                    g.setColor(Color.BLACK);
                    g.fillRect(startX + x * cellSize + 1, startY + y * cellSize + 1, cellSize - 1, cellSize - 1);
                }
            }
        }

        if (matrix.getActiveTetromino() != null) {
            g.setColor(colors[matrix.getActiveTetromino().getShape().getLetter().ordinal() % colors.length]);
            for (Point p : matrix.getActiveTetromino().getMinos()) {
                g.fillRect(startX + p.x * cellSize + 1, startY + p.y * cellSize + 1, cellSize - 1, cellSize - 1);
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
        return new Dimension(matrix.SIZE_X * cellSize + 20, matrix.SIZE_Y * cellSize + 20);
    }
}
