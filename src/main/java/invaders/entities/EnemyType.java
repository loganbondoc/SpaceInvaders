package invaders.entities;

import invaders.physics.FastProjectileStrategy;
import invaders.physics.ProjectileStrategy;
import invaders.physics.SlowProjectileStrategy;

/**
 * The set of available enemy types
 */
public enum EnemyType {
    TYPE1(10, "enemy1.png", new SlowProjectileStrategy()),
    TYPE2(20, "enemy2.png", new FastProjectileStrategy()),
    TYPE3(30, "enemy3.png", new FastProjectileStrategy());

    private final int value;
    private final String imageName;
    private final ProjectileStrategy projectileStrategy;

    EnemyType(int value, String imageName, ProjectileStrategy projectileStrategy) {
        this.value = value;
        this.imageName = imageName;
        this.projectileStrategy = projectileStrategy;
    }

    public int getValue() {
        return value;
    }

    public String getImageName() {
        return imageName;
    }

    public ProjectileStrategy getProjectileStrategy(){
        return projectileStrategy;
    }
}


