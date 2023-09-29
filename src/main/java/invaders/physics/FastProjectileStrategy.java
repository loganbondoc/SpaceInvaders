package invaders.physics;

import invaders.entities.Projectile;

public class FastProjectileStrategy implements ProjectileStrategy{
    @Override
    public void move(Projectile projectile, double velocity) {
        double speed = 2;
        // Implement the movement logic for slow projectiles
        double currentY = projectile.getPosition().getY();

        // Update the projectile's position (move upward in this case)
        projectile.getPosition().setY(currentY + (velocity * speed));

        // You can add additional logic like checking for collisions or removing projectiles that are out of bounds
    }
}
