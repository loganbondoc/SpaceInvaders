package invaders.entities;

import invaders.physics.ProjectileStrategy;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;
import invaders.physics.BoxCollider;

import java.io.File;

public class EnemyProjectile extends Projectile{

    public EnemyProjectile(Vector2D position, ProjectileStrategy projectileStrategy) {
        super(position, projectileStrategy);
        this.setImage(new Image(new File("src/main/resources/enemy_projectile.png").toURI().toString(), this.getWidth(), this.getHeight(), true, true));
        this.setVelocity(1); // controls direction
        this.setCollider(new BoxCollider(this.getWidth(), this.getHeight(), position));
    }
}
