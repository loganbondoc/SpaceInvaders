package invaders.engine;

import java.util.ArrayList;
import java.util.List;

import invaders.GameObject;
import invaders.entities.Player;
import invaders.entities.Enemy;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.logic.Wave;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private Player player;
	private Enemy enemy;
	private Wave wave;
	private boolean gameLost;

	private boolean left;
	private boolean right;

	public GameEngine(String config){
		// read the config here
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();

		player = new Player(new Vector2D(200, 380));
		renderables.add(player);

		wave = new Wave(new ArrayList<ArrayList<Enemy>>());
		wave.generateWave();
		for(ArrayList<Enemy> enemyRow: wave.getEnemyList()){
			for(int i = 0; i < 9; i++){
				renderables.add(enemyRow.get(i));
			}
		}


		// builder for Aliens
		// create each Alien
		// create a new vector2d
		// read from json
		// set the aliens one to that
		// move onto the next

//		waveRow = new ArrayList<Alien>();
//		wave = new Wave;
		// add them to wave
		// add each one to renderables

		// Loop for a game
//		while (gameLost == false){
//			// call wavecheck
////			if wave
//		}
			// call wavecheck
			// if wave < 0
				// do nothing
			// else
				// generateWave builder thing
				// enemy = new Enemy(new Vector2D(0, 0));
				// renderables.add(enemy);
				// add all characters from wave into renderable list
		// if player dies do game over screen
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		movePlayer();
		for(GameObject go: gameobjects){
			go.update();
		}

		// ensure that renderable foreground objects don't go off-screen
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= 640) {
				ro.getPosition().setX(639-ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(1);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= 400) {
				ro.getPosition().setY(399-ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		player.shoot();
		return true;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}
}
