package invaders.engine;

import java.util.List;
import java.util.ArrayList;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import invaders.entities.Enemy;
import javafx.scene.shape.Rectangle;

public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private Renderable background;

    private List<Enemy> enemiesToMarkForDelete;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;

    private Text gameOverText;
    private Text livesText;

	public GameWindow(GameEngine model, int width, int height){
		this.width = width;
        this.height = height;
        this.model = model;
        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        entityViews = new ArrayList<EntityView>();
        enemiesToMarkForDelete = new ArrayList<Enemy>();

    }

	public void run() {
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }

    private void draw(){
        model.update();
        // draw rectangle to hide score overlay
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setWidth(100);
        rectangle.setHeight(50);
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.BLACK);
        pane.getChildren().add(rectangle);


        // drawing lives
        livesText = new Text(Integer.toString(model.getLives()));
        livesText.setFont(Font.font("Arial", 36));
        livesText.setFill(Color.WHITE);
        livesText.setX(50);
        livesText.setY(50);
        pane.getChildren().add(livesText);

        List<Renderable> renderables = model.getRenderables();
        List<Renderable> renderablesToRemove = new ArrayList<>();

        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        // Remove from screen enemies if removed from renderables
        for (EntityView entityView : entityViews) {
            boolean found = false;
            for (Renderable entity : renderables) {
                if (entityView.matchesEntity(entity)) {
                    found = true;
                    entityView.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (!found) {
                entityView.markForDelete();
            }
        }


        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);

        if(model.getLives() <= 0){
            // set up for "Game Over" text
            gameOverText = new Text("Game Over");
            gameOverText.setFont(Font.font("Arial", 36));
            gameOverText.setFill(Color.RED);
            gameOverText.setX(200);
            gameOverText.setY(200);

            pane.getChildren().add(gameOverText);
        }
    }

	public Scene getScene() {
        return scene;
    }
}
