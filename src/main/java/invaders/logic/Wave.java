package invaders.logic;

import invaders.entities.Enemy;
import invaders.entities.EnemyType;
import invaders.physics.Vector2D;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Wave {
    private ArrayList<ArrayList<Enemy>> enemyList;

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

    public void generateWave() {
        JSONParser parser = new JSONParser();

        try {
            // Parse the config.json file
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.json");

            if (inputStream != null) {
                // Parse the config.json file
                InputStreamReader reader = new InputStreamReader(inputStream);
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;

                // Get the "Enemies" array from the config
                JSONArray enemiesArray = (JSONArray) jsonObject.get("Enemies");

                int rowIndex = 0;
                ArrayList<Enemy> currentRow = new ArrayList<>();

                // Loop through the enemies in the config and create Enemy objects
                for (Object enemyObj : enemiesArray) {
                    JSONObject enemyJson = (JSONObject) enemyObj;

                    // Get the x and y positions from the config
                    long xPosition = (long) ((JSONObject) enemyJson.get("position")).get("x");
                    long yPosition = (long) ((JSONObject) enemyJson.get("position")).get("y");

                    // Create a Vector2D for the location
                    Vector2D location = new Vector2D(xPosition, yPosition);

                    // Get the projectile type (you can adjust this as needed)
                    String projectile = (String) enemyJson.get("projectile");

                    // Create an EnemyType based on the projectile type (you can adjust this as needed)
                    EnemyType enemyType = getEnemyTypeFromProjectile(projectile);

                    // Create the Enemy object
                    Enemy enemy = new Enemy(enemyType, location);

                    // Add the enemy to the current row
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


    // Helper method to map projectile type to EnemyType (you can adjust this as needed)
    private EnemyType getEnemyTypeFromProjectile(String projectile) {
        switch (projectile) {
            case "fast_straight":
                return EnemyType.TYPE1;
            case "slow_straight":
                return EnemyType.TYPE2;
            default:
                return EnemyType.TYPE3; // Default enemy type
        }
    }

    // setters and getters
    public void setEnemyList(ArrayList<ArrayList<Enemy>> enemyList) {
        this.enemyList = enemyList;
    }

    public ArrayList<ArrayList<Enemy>> getEnemyList() {
        return enemyList;
    }
}




