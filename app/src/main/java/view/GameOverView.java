package view;

import controller.GameOverController;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameOverView extends JPanel {
    private static final float BASE_TITLE_SIZE = 36f;
    private static final float BASE_SCORE_SIZE = 24f;
    private static final float BASE_BUTTON_SIZE = 20f;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private final GameOverController gameOverController;
    private JLabel titleLabel;
    private JLabel scoreLabel;
    @Getter
    private JButton homeButton;

    public GameOverView(GameOverController gameOverController, int score) {
        this.gameOverController = gameOverController;
        gameOverController.setView(this);
        setLayout(new BorderLayout());

        this.paintComponent(score);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateFonts();
            }
        });

        this.setupWindow();
    }

    private void updateFonts() {
        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) return;

        float scaleFactor = Math.min((float) width / WIDTH, (float) height / HEIGHT);

        titleLabel.setFont(new Font("Arial", Font.BOLD,
                Math.max(16, Math.round(BASE_TITLE_SIZE * scaleFactor))));
        scoreLabel.setFont(new Font("Arial", Font.PLAIN,
                Math.max(12, Math.round(BASE_SCORE_SIZE * scaleFactor))));
        homeButton.setFont(new Font("Arial", Font.PLAIN,
                Math.max(10, Math.round(BASE_BUTTON_SIZE * scaleFactor))));

        int buttonSize = Math.max(60, Math.round(100 * scaleFactor));
        homeButton.setPreferredSize(new Dimension(buttonSize, buttonSize));

        revalidate();
    }

    private void paintComponent(int score) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        titleLabel = new JLabel("Game Over");
        titleLabel.setFont(new Font("Arial", Font.BOLD, (int) BASE_TITLE_SIZE));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel scorePanel = new JPanel();
        scorePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, (int) BASE_SCORE_SIZE));
        scorePanel.add(scoreLabel);
        add(scorePanel, BorderLayout.CENTER);

        JPanel homePanel = new JPanel(new GridLayout());
        homePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, (int) BASE_BUTTON_SIZE));
        homeButton.setPreferredSize(new Dimension(100, 100));
        homeButton.addActionListener(e -> {
            gameOverController.returnHome();
        });

        homePanel.add(homeButton);
        add(homePanel, BorderLayout.SOUTH);
    }

    private void setupWindow() {
        JFrame frame = new JFrame("Matrix Game");
        frame.setTitle("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(this);
    }

    public void close() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.dispose();
        }
    }
}