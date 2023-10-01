package invaders.physics;

import invaders.entities.Projectile;

public class SlowProjectileStrategy implements ProjectileStrategy {

    @Override
    public void move(Projectile projectile, double velocity) {
        double speed = 1;
        double currentY = projectile.getPosition().getY();

        // update the projectile's position
        projectile.getPosition().setY(currentY + (velocity * speed));
    }
}
