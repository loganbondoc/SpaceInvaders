package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.Moveable;
import invaders.physics.ProjectileStrategy;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;
import invaders.physics.BoxCollider;
import javafx.scene.image.Image;
import java.io.File;

public class Enemy implements Moveable, Damagable, Renderable {

    private final Vector2D position;
    private final Animator anim = null;
    private double health = 100;
    private int value;
    private boolean movingRight = true;

    private final double width = 25;
    private final double height = 30;
    private final Image image;

    private BoxCollider collider;
    private ProjectileStrategy projectileStrategy;

    private boolean shouldRemove = false;

    public Enemy(EnemyType enemyType, Vector2D location) {
        this.position = location;
        this.image = new Image(new File("src/main/resources/" + enemyType.getImageName()).toURI().toString());
        this.value = enemyType.getValue();
        this.projectileStrategy = enemyType.getProjectileStrategy();
        this.collider = new BoxCollider(width, height, position);
    }

    public void setCollider(BoxCollider collider){
        this.collider = collider;
    }

    public BoxCollider getCollider() {
        return collider;
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

    public Projectile shoot(){
        Projectile projectile = new EnemyProjectile(new Vector2D(this.position.getX(), this.position.getY()), projectileStrategy);
        return projectile;
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
