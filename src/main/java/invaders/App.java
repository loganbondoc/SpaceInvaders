package invaders;

import javafx.application.Application;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;
import javafx.scene.layout.Pane;

import java.util.Map;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Map<String, String> params = getParameters().getNamed();

        Pane root = new Pane(); // Create the root Pane

        GameEngine model = new GameEngine("/resources/config.json"); // Pass the root Pane to the GameEngine
        GameWindow window = new GameWindow(model, 640, 400);
        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}
