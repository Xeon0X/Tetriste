package controller;

import model.Matrix;
import model.PreconfigMatrix;
import view.HomeView;
import view.MatrixView;

public class HomeController {

    private HomeView view;

    public HomeController() {
    }

    public void setView(HomeView view) {
        this.view = view;
    }

    public void startGame(int difficulty) {
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
        MatrixController matrixController = new MatrixController(currentMatrix);
        MatrixView matrixView = new MatrixView(currentMatrix, matrixController);
    }
}
