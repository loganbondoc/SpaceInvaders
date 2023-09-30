package invaders.entities;

import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;
import invaders.physics.ProjectileStrategy;

public class Projectile implements Renderable {
    private Vector2D position;
    private String direction;
    private String speed;

    private Image image;
    private double width = 17;
    private double height = 15;
    private double velocity; // sets direction it moves in

    private final ProjectileStrategy projectileStrategy;
    private BoxCollider collider;

    public Projectile(Vector2D position, ProjectileStrategy projectileStrategy) {
        this.position = position;
        this.projectileStrategy = projectileStrategy;
        this.collider = new BoxCollider(width, height, position);
    }

    public void move(){
        projectileStrategy.move(this, velocity);
    }

    public void setCollider(BoxCollider collider){
        this.collider = collider;
    }

    public BoxCollider getCollider() {
        return collider;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public boolean offscreen() {
        if((this.position.getY() - this.height) > 400 || (this.position.getY() + this.height) < 0){
            return true;
        }
        return false;
    }

    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Image getImage(){ return this.image; }

    @Override
    public double getWidth(){ return this.width; }
    @Override
    public double getHeight(){ return this.height; }
    @Override
    public Renderable.Layer getLayer(){return Layer.EFFECT;}

}
