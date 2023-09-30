package invaders.logic;

import invaders.physics.Vector2D;
import invaders.entities.Enemy;
import invaders.entities.EnemyType;


/**
 * Builder design pattern for enemy creation
 */
public class EnemyBuilder {
    private EnemyType enemyType;
    private Vector2D location;

    public EnemyBuilder(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public EnemyBuilder setLocation(Vector2D location) {
        this.location = location;
        return this;
    }

    public Enemy build() {
        if (enemyType == null) {
            throw new IllegalStateException("EnemyType must be set before building an Enemy.");
        }

        Vector2D finalLocation;
        if (location != null) {
            finalLocation = location;
        } else {
            finalLocation = new Vector2D(0, 0); // default location
        }

        return new Enemy(enemyType, finalLocation);
    }
}


