package invaders.entities;

import invaders.physics.Vector2D;

/**
 * Factory Method for projectile creation
 */
public interface ProjectileFactory {
    Projectile createProjectile(Vector2D position);
}
