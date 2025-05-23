package view;

import model.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;

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
    private final JTextField textField = new JTextField("");
    private boolean isPaused = false;
    private int cellSize;
    private JFrame frame;
    private Consumer<Integer> gameOverHandler;

    public MatrixView(Matrix matrix) {
        this.matrix = matrix;
        setBackground(Color.WHITE);
        calculateCellSize();
        matrix.addObserver(this);

        setupWindow();
    }

    public void setGameOverHandler(Consumer<Integer> handler) {
        this.gameOverHandler = handler;
    }

    public void handleGameOver(int score) {
        if (gameOverHandler != null) {
            gameOverHandler.accept(score);
        }
    }

    private void setupWindow() {
        frame = new JFrame("Matrix Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        textField.setFocusable(false);
        panel.add(this, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setResizable(true);
        frame.addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        repaint();
                    }
                }
        );

        frame.setVisible(true);
    }

    public void close() {
        if (frame != null) {
            frame.dispose();
        }
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
        repaint();
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

        int baseX = 10;
        int spacingY = fontSize + 8;
        int labelX = baseX;
        int valueX = baseX + 60;

        int startY2 = Math.max(30, startY / 2);
        int horzontalSpacing = 10;

        g.drawString("Score:", labelX, startY2);
        g.drawString(String.valueOf(matrix.getScore()), valueX + horzontalSpacing, startY2);

        g.drawString("Level:", labelX, startY2 + spacingY);
        g.drawString(String.valueOf(matrix.getLevel()), valueX + horzontalSpacing, startY2 + spacingY);

        g.drawString("Lines:", labelX, startY2 + 2 * spacingY);
        g.drawString(String.valueOf(matrix.getLinesCleared()), valueX + horzontalSpacing, startY2 + 2 * spacingY);

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
                    g.setColor(Color.WHITE);
                    g.fillRect(startX + x * cellSize + 1, startY + y * cellSize + 1, cellSize - 1, cellSize - 1);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(startX + x * cellSize + 1, startY + y * cellSize + 1, cellSize - 1, cellSize - 1);
                }
            }
        }

        if (matrix.getPreviewTetromino() != null) {
            g.setColor(colors[matrix.getPreviewTetromino().getShape().getLetter().ordinal() % colors.length]);
            for (Point p : matrix.getPreviewTetromino().getMinos()) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(startX + p.x * cellSize + 1, startY + p.y * cellSize + 1, cellSize - 1, cellSize - 1);
            }
        }

        if (matrix.getActiveTetromino() != null) {
            g.setColor(colors[matrix.getActiveTetromino().getShape().getLetter().ordinal() % colors.length]);
            for (Point p : matrix.getActiveTetromino().getMinos()) {
                g.fillRect(startX + p.x * cellSize + 1, startY + p.y * cellSize + 1, cellSize - 1, cellSize - 1);
            }
        }

        if (matrix.getNextTetromino() != null) {
            g.setColor(colors[matrix.getNextTetromino().getShape().getLetter().ordinal() % colors.length]);
            for (Point p : matrix.getNextTetromino().getMinos()) {
                g.fillRect(startX + p.x * cellSize + 1, startY + p.y * cellSize + 1, cellSize - 1, cellSize - 1);
            }
        }

        // Area of the tetromino
        if (matrix.getActiveTetromino() != null) {
            g.setColor(colors[matrix.getActiveTetromino().getShape().getLetter().ordinal() % colors.length]);
            Point start = matrix.getActiveTetromino().getPosition();
            int size = matrix.getActiveTetromino().getShape().getSize();
            g.setColor(Color.RED);
            g.drawRect(startX + start.x * cellSize, startY + start.y * cellSize, size * cellSize, size * cellSize);
        }

        if (isPaused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            Font pauseFont = new Font("Arial", Font.BOLD, Math.max(30, getWidth() / 10));
            g.setFont(pauseFont);

            String pauseText = "PAUSE";
            FontMetrics metrics = g.getFontMetrics(pauseFont);
            int x = (getWidth() - metrics.stringWidth(pauseText)) / 2;
            int y = (getHeight() + metrics.getHeight()) / 2;

            g.drawString(pauseText, x, y);
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