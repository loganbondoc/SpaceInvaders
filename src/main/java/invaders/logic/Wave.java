package invaders.logic;

import invaders.entities.Enemy;
import invaders.entities.EnemyType;
import invaders.physics.Vector2D;
import invaders.entities.Projectile;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Random;

/**
 * Overarching class that manages all enemies on screen
 */
public class Wave {
    private ArrayList<ArrayList<Enemy>> enemyList;
    private boolean gameLost = false;

    public Wave(ArrayList<ArrayList<Enemy>> enemyList){
        this.enemyList = enemyList;
    }

    public boolean waveEmpty(){
        for (ArrayList<Enemy> enemies : enemyList) {
            if (!enemies.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void clearWave(){
        for (ArrayList<Enemy> enemyRow : enemyList){
            enemyRow.clear();
        }
    }

    /**
     * Generates enemy wave based on config.json
     */
    public void generateWave() {
        JSONParser parser = new JSONParser();

        try {
            // parse enemies from config.json file
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.json");

            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;
                JSONArray enemiesArray = (JSONArray) jsonObject.get("Enemies");
                int rowIndex = 0;
                ArrayList<Enemy> currentRow = new ArrayList<>();

                // Loop config enemies and initialise position, type and projectile
                for (Object enemyObj : enemiesArray) {
                    JSONObject enemyJson = (JSONObject) enemyObj;
                    long xPosition = (long) ((JSONObject) enemyJson.get("position")).get("x");
                    long yPosition = (long) ((JSONObject) enemyJson.get("position")).get("y");
                    Vector2D location = new Vector2D(xPosition, yPosition);
                    String projectile = (String) enemyJson.get("projectile");
                    EnemyType enemyType = getEnemyTypeFromProjectile(projectile);

                    // create EnemyBuilder and set enemyType and location
                    EnemyBuilder enemyBuilder = new EnemyBuilder(enemyType)
                            .setLocation(location);

                    // build enemy
                    Enemy enemy = enemyBuilder.build();
                    currentRow.add(enemy);

                    // If the current row is full, add it to the enemyList
                    if (currentRow.size() >= 9) {
                        enemyList.add(currentRow);
                        currentRow = new ArrayList<>();
                        rowIndex++;
                    }
                }

                // Add any remaining enemies to the enemyList
                if (!currentRow.isEmpty()) {
                    enemyList.add(currentRow);
                }
            } else {
                System.err.println("config.json file not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // map projectile type to EnemyType
    public EnemyType getEnemyTypeFromProjectile(String projectile) {
        switch (projectile) {
            case "slow_straight":
                return EnemyType.TYPE1;
            case "fast_straight":
                return EnemyType.TYPE2;
            default:
                return EnemyType.TYPE3; // Default enemy type
        }
    }

    public boolean shouldMoveDown(double xMax, double xMin){
        // check if enemies should move down
        for (ArrayList<Enemy> enemyRow: this.enemyList){
            for(Enemy enemy: enemyRow){
                if((enemy.getPosition().getX() -10 - enemy.getWidth()) <= xMin
                        || (enemy.getPosition().getX() + 10 + enemy.getWidth()) >= xMax){
                    return true;
                }
            }
        }
        return false;
    }

    public void moveEnemies(double xMax, double xMin, double yMax) {

        // check if enemies should move down
        if (this.shouldMoveDown(xMax, xMin)) {
            for (ArrayList<Enemy> enemyRow : this.enemyList) {
                for(Enemy enemy: enemyRow){
                    if (enemy.getPosition().getY() + enemy.getHeight() >= yMax){
                        gameLost = true;
                        break;
                    }
                    enemy.down();
                    enemy.setMovingRight(!enemy.getMovingRight());
                    if (enemy.getMovingRight()) {
                        enemy.right();
                    } else {
                        enemy.left();
                    }
                }
            }
        } else {
            for (ArrayList<Enemy> enemyRow : this.enemyList) {
                for (Enemy enemy : enemyRow) {
                    if (enemy.getMovingRight()) {
                        enemy.right();
                    } else {
                        enemy.left();
                    }
                }
            }
        }
    }

    public Projectile enemiesShoot() {
        // Generate a random row index within the enemyList size
        int selectedRow = new Random().nextInt(enemyList.size());

        // Get the selected row of enemies
        ArrayList<Enemy> selectedEnemyRow = enemyList.get(selectedRow);

        // Generate a random index within the selected row
        int randomEnemyIndex = new Random().nextInt(selectedEnemyRow.size());

        // Get the random enemy from the selected row
        Enemy randomEnemy = selectedEnemyRow.get(randomEnemyIndex);

        // Call the shoot method of the random enemy to make it shoot
        Projectile enemyProjectile = randomEnemy.shoot();
        return enemyProjectile; // Return the created projectile
    }

    // setters and getters
    public void setEnemyList(ArrayList<ArrayList<Enemy>> enemyList) {
        this.enemyList = enemyList;
    }

    public ArrayList<ArrayList<Enemy>> getEnemyList() {
        return enemyList;
    }

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    public boolean getGameLost() {
        return gameLost;
    }
}