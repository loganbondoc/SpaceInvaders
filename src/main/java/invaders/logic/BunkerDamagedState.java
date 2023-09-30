package invaders.logic;

import invaders.entities.Bunker;

/**
 * State indicates bunker has taken two hits
 */
public class BunkerDamagedState implements BunkerState {
    @Override
    public void handleDamage(Bunker bunker) {
        if (bunker.getTimesHit() == 2) {
            bunker.setState(new BunkerHeavilyDamagedState());
            bunker.changeImage();
        }
    }
}
