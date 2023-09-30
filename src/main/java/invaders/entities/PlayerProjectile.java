package invaders.entities;

import invaders.physics.ProjectileStrategy;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;
import invaders.physics.BoxCollider;

import java.io.File;

public class PlayerProjectile extends Projectile{

    public PlayerProjectile(Vector2D position, ProjectileStrategy projectileStrategy) {
        super(position, projectileStrategy);
        this.setImage(new Image(new File("src/main/resources/player_projectile.png").toURI().toString(), this.getWidth(), this.getHeight(), true, true));
        this.setVelocity(-1); // projectile moves upwards
        this.setCollider(new BoxCollider(this.getWidth(), this.getHeight(), position));
    }
}
