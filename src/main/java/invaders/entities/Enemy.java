package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;

public class Enemy implements Moveable, Damagable, Renderable {

    private final Vector2D position;
    private final Animator anim = null;
    private double health = 100;
    private int value;
    private boolean movingRight = true;
//    private boolean movingDown = false;

    private final double width = 25;
    private final double height = 30;
    private final Image image;

    public Enemy(EnemyType enemyType, Vector2D location) {
        this.position = location;
        this.image = new Image(new File("src/main/resources/" + enemyType.getImageName()).toURI().toString());
        this.value = enemyType.getValue();
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
    public void up() {
        return;
    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() + 10);
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - 10);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + 10);
    }

    public void shoot(){
        //todo
    }

    // getters
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

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean getMovingRight() {
        return movingRight;
    }

}
