package invaders.physics;

import invaders.entities.Projectile;

public class SlowProjectileStrategy implements ProjectileStrategy {

    // read json for speed
    // TODO
    final double speed = 1;

    @Override
    public void move(Projectile projectile, double velocity) {
        double speed = 1;
        // Implement the movement logic for slow projectiles
        double currentY = projectile.getPosition().getY();

        // Update the projectile's position (move upward in this case)
        projectile.getPosition().setY(currentY + (velocity * speed));

        // You can add additional logic like checking for collisions or removing projectiles that are out of bounds
    }
}
