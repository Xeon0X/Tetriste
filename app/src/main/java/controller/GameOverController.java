package controller;

import view.GameOverView;
import view.HomeView;

public class GameOverController {
    private GameOverView gameOverView;

    public GameOverController() {
    }

    public void setView(GameOverView gameOverView) {
        this.gameOverView = gameOverView;
    }

    public void returnHome() {
        if (gameOverView != null) {
            gameOverView.close();
        }

        HomeController homeController = new HomeController();
        HomeView homeView = new HomeView(homeController);
        homeController.setView(homeView);
        homeView.display();
    }
}
