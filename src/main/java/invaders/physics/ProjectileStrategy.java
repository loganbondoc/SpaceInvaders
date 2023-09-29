package invaders.physics;
import invaders.entities.Projectile;

public interface ProjectileStrategy {
    void move(Projectile projectile, double velocity);
}
