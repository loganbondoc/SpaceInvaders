package invaders.logic;

import invaders.physics.Vector2D;
import invaders.entities.Enemy;

public interface EnemyMovement {

    public default void changeCoords(Enemy enemy){
        double x = enemy.getPosition().getX();
        double y = enemy.getPosition().getY();

        enemy.getPosition().setX(x += 1);
        // if its equal to window width then make it start counting down
        enemy.getPosition().setX(x += 1);
    }
    // get new coords
    // take the position of an enemy
    // spit out new position

    // go through each enemy
    // change overwrite the position
}
