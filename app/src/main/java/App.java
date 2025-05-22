import controller.HomeController;
import view.HomeView;

public class App {
    public static void main(String[] args) {
        HomeController controller = new HomeController();
        HomeView view = new HomeView(controller);
        controller.setView(view);
        view.display();
    }
}
