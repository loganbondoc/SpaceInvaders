package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;
import invaders.logic.BunkerState;
import invaders.logic.BunkerIntactState;

import javafx.scene.image.Image;

import java.io.File;

public class Bunker implements Damagable, Renderable {

    private final Vector2D position;
    private final Animator anim = null;
    private double health = 100;
    private int timesHit = 0;

    private final double width = 50;
    private final double height = 60;
    private Image image;

    private BoxCollider collider;
    private BunkerState state;
    private boolean stillActive = true; // if bunker still impacts collision

    // list of images for different bunker states
    private static final String[] bunkerImagePaths = {
            "bunker1.png",
            "bunker2.png",
            "bunker3.png"
    };
    private int imageIndex = 0;

    public Bunker(Vector2D location, double width, double height) {
        this.position = location;
        this.image = new Image(new File("src/main/resources/"+ bunkerImagePaths[0]).toURI().toString(), width, height, true, true);
        this.state = new BunkerIntactState();
        this.collider = new BoxCollider(width, height, position);
    }

    public void setStillActive(boolean active){
        this.stillActive = active;
    }

    public boolean getStillActive(){
        return this.stillActive;
    }

    public void setCollider(BoxCollider collider){
        this.collider = collider;
    }

    public BoxCollider getCollider() {
        return collider;
    }

    @Override
    public void takeDamage(double amount) {
        state.handleDamage(this);
        this.timesHit += 1;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    public void setState(BunkerState state) {
        this.state = state;
    }

    public int getTimesHit(){
        return timesHit;
    }

    /**
     * Change bunker image for damage indication
     */
    public void changeImage() {
        if (imageIndex < bunkerImagePaths.length - 1) {
            imageIndex++;
            this.image = new Image(new File("src/main/resources/"+ bunkerImagePaths[imageIndex]).toURI().toString(), width, height, true, true);
        }
    }

}
