package invaders.entities;

import invaders.physics.Vector2D;

public interface ProjectileFactory {
    Projectile createProjectile(Vector2D position);
}
