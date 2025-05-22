package controller;

import view.GameOverView;

public class GameOverController {
    private GameOverView gameOverView;

    public GameOverController(int score) {
        this.gameOverView = new GameOverView(score);
        this.gameOverView.setHomeButtonListener(e -> returnHome());
        this.gameOverView.display();
    }

    private void returnHome() {
        if (gameOverView != null) {
            gameOverView.close();
        }

        // HomeController creates its own view in its constructor
        new HomeController();
    }
}