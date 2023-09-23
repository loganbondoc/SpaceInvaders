package invaders.logic;

import invaders.physics.Vector2D;
import invaders.entities.Enemy;
import java.util.ArrayList;

public interface EnemyMovement {

    public default boolean enemyMoving(Wave wave, double xMax, double xMin, double yMax) {
        // check if enemy has reached the end
        ArrayList<Enemy> finalRow = wave.getEnemyList().get(wave.getEnemyList().size() - 1);
        if (finalRow.get(0).getPosition().getY() >= yMax) {
            return false;
        }

        for (ArrayList<Enemy> enemyRow : wave.getEnemyList()) {
            // if enemy at the beginning is at the far left or end far right
            if (enemyRow.get(0).getPosition().getX() > xMax
                    || enemyRow.get(enemyRow.size() - 1).getPosition().getX() < xMin) {
                // loop through all enemies and move them down
                for (ArrayList<Enemy> enemies : wave.getEnemyList()) {
                    for (int i = 0; i < enemyRow.size(); i++) {
                        enemies.get(i).down();
                        enemies.get(i).setMovingRight(!enemies.get(i).getMovingRight());
                    }
                }
                return true;
            }
        }

        // If not touching edge of the screen, move left or right
        for (ArrayList<Enemy> enemyRows : wave.getEnemyList()) {
            for (int i = 0; i < enemyRows.size(); i++) {
                if (enemyRows.get(i).getMovingRight()) {
                    enemyRows.get(i).right();
                } else {
                    enemyRows.get(i).left();
                }
            }
        }

        return true; // Move the enemies
    }

}
