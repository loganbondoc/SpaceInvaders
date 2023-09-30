package invaders.physics;

import invaders.entities.Projectile;

public class FastProjectileStrategy implements ProjectileStrategy{
    @Override
    public void move(Projectile projectile, double velocity) {
        double speed = 2; // speed that projectile moves

        double currentY = projectile.getPosition().getY();

        // update the projectile's position
        projectile.getPosition().setY(currentY + (velocity * speed));
    }
}
