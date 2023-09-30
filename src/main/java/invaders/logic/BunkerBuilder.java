package invaders.logic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.ArrayList;
import java.util.List;
import invaders.entities.Bunker;
import invaders.physics.Vector2D;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Builder design pattern for bunker creation
 */

public class BunkerBuilder {

    private List<Bunker> bunkers;

    public BunkerBuilder(String configFile) {
        bunkers = new ArrayList<>();
        parseConfig(configFile);
    }

    private void parseConfig(String configFile) {
        JSONParser parser = new JSONParser();

        try {
            // Parse the config.json file
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);

            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;

                JSONArray bunkerConfigs = (JSONArray) jsonObject.get("Bunkers");
                for (Object bunkerObj : bunkerConfigs) {
                    JSONObject bunkerConfig = (JSONObject) bunkerObj;

                    // getting size and position
                    JSONObject position = (JSONObject) bunkerConfig.get("position");
                    JSONObject size = (JSONObject) bunkerConfig.get("size");
                    Long x = (Long) position.get("x");
                    Long y = (Long) position.get("y");
                    Long width = (Long) size.get("x");
                    Long height = (Long) size.get("y");
                    double xDouble = x.doubleValue();
                    double yDouble = y.doubleValue();
                    double widthDouble = width.doubleValue();
                    double heightDouble = height.doubleValue();

                    // build bunker
                    bunkers.add(buildBunker(xDouble, yDouble, widthDouble, heightDouble));
                }
            } else {
                System.err.println("config.json file not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Bunker buildBunker(double x, double y, double width, double height) {
        Vector2D location = new Vector2D(x, y);
        return new Bunker(location, width, height);
    }

    public List<Bunker> getBunkers() {
        return bunkers;
    }
}
