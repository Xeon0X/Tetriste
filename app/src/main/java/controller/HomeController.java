package controller;

import model.Matrix;
import model.PreconfigMatrix;
import view.HomeView;

public class HomeController {

    private HomeView view;

    public HomeController() {
        this.view = new HomeView();
        this.view.setDifficultyActionListener(e -> {
            int difficulty;
            String buttonText = e.getActionCommand();

            if ("Easy".equals(buttonText)) {
                difficulty = 0;
            } else if ("Medium".equals(buttonText)) {
                difficulty = 1;
            } else {
                difficulty = 2;
            }

            startGame(difficulty);
        });
        this.view.display();
    }

    private void startGame(int difficulty) {
        if (view != null) {
            view.close();
        }

        int sizeX = 10;
        int sizeY = switch (difficulty) {
            case 0 -> 24;
            case 1 -> 20;
            case 2 -> 16;
            default -> 24;
        };

        Matrix currentMatrix = Matrix.builder()
                .sizeX(sizeX)
                .sizeY(sizeY)
                .matrix(PreconfigMatrix.emptyLineLeft(sizeX, sizeY))
                .build();

        new MatrixController(currentMatrix);
    }
}