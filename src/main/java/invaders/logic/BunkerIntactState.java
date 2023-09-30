package invaders.logic;
import invaders.entities.Bunker;

/**
 * State indicates bunker has taken one hit
 */
public class BunkerIntactState implements BunkerState {
    @Override
    public void handleDamage(Bunker bunker) {
        if (bunker.getTimesHit() == 1) {
            bunker.setState(new BunkerDamagedState());
            bunker.changeImage();
        }
    }
}
