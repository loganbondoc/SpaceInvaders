package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;

public class Bunker implements Damagable, Renderable {

    private final Vector2D position;
    private final Animator anim = null;
    private double health = 100;
    private int value;

    private final double width = 50;
    private final double height = 60;
    private final Image image;

    public Bunker(Vector2D location, double width, double height) {
        this.position = location;
        this.image = new Image(new File("src/main/resources/bunker1.png").toURI().toString(), width, height, true, true);
    }

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
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
}
