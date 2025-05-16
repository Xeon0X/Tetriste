package controller;

import view.GameOverView;
import view.HomeView;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameOverController extends JFrame {
    private final GameOverView gameOverView;

    public GameOverController() {
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        gameOverView = new GameOverView();
        setContentPane(gameOverView);

        setupListeners();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameOverController gameOverController = new GameOverController();
            gameOverController.setVisible(true);
        });
    }

    private void setupListeners() {
        ActionListener homeListener = e -> {
            dispose(); // Close the current window

            // Create and show the home controller
            SwingUtilities.invokeLater(() -> {
                HomeController homeController = new HomeController();
                homeController.setVisible(true);
            });
        };

        gameOverView.getHomeButton().addActionListener(homeListener);
    }
}
