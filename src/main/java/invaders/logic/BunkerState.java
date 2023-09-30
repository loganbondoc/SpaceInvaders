package invaders.logic;

import invaders.entities.Bunker;

/**
 * State design pattern for bunker damage indication
 */
public interface BunkerState {
    void handleDamage(Bunker bunker);
}

