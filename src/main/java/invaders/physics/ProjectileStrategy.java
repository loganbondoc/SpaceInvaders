package invaders.physics;
import invaders.entities.Projectile;

/**
 * Strategy design pattern for projectile movement behaviour
 */
public interface ProjectileStrategy {
    void move(Projectile projectile, double velocity);
}
