package invaders.logic;

import invaders.entities.Bunker;

/**
 * State indicates bunker has taken three hits
 */
public class BunkerHeavilyDamagedState implements BunkerState {
    @Override
    public void handleDamage(Bunker bunker) {
        bunker.setStillActive(false);
    }
}
