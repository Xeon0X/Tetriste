package controller;

import model.Matrix;
import view.HomeView;
import view.MatrixView;

import javax.swing.*;
import java.awt.event.ActionListener;

public class HomeController extends JFrame {

    private final HomeView homeView;

    public HomeController() {
        setTitle("Tetriste");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        homeView = new HomeView();
        setContentPane(homeView);

        setupListeners();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeController homeController = new HomeController();
            homeController.setVisible(true);
        });
    }

    private void setupListeners() {
        ActionListener difficultyListener = e -> {
            JButton source = (JButton) e.getSource();

            int difficulty;
            if (source == homeView.getEasyButton()) {
                difficulty = 0;
            } else if (source == homeView.getMediumButton()) {
                difficulty = 1;
            } else {
                difficulty = 2;
            }

            startGame(difficulty);
        };

        homeView.setDifficultyActionListener(difficultyListener);
    }

    private void startGame(int difficulty) {
        dispose();

        SwingUtilities.invokeLater(() -> {
            int sizeX = 10;
            int sizeY =
                    switch (difficulty) {
                        case 0 -> 24;
                        case 1 -> 20;
                        case 2 -> 16;
                        default -> 24;
                    };

            Matrix currentMatrix = Matrix.builder().sizeX(sizeX).sizeY(sizeY).build();
            MatrixController matrixController = new MatrixController(currentMatrix);
            MatrixView matrixView = new MatrixView(currentMatrix, matrixController);
        });
    }
}
