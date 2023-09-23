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
import invaders.entities.Bunker;
import invaders.logic.EnemyMovement;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements EnemyMovement {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private Player player;
	private Wave wave;
	private boolean gameLost;

	private boolean left;
	private boolean right;

	private long lastWaveMoveTime;
	private long waveMoveInterval = 250; // One-second interval (1000 milliseconds)

	public GameEngine(String config){
		// read the config here
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();

		player = new Player(new Vector2D(200, 380));
		renderables.add(player);

		// initial generation of a wave
		wave = new Wave(new ArrayList<ArrayList<Enemy>>());
		wave.generateWave();
		for(ArrayList<Enemy> enemyRow: wave.getEnemyList()){
			for(int i = 0; i < 9; i++){
				renderables.add(enemyRow.get(i));
			}
		}

//		Bunker bunker = new Bunker(new Vector2D(0,0), 50, 50);
//		renderables.add(bunker);

		lastWaveMoveTime = System.currentTimeMillis();
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		movePlayer();
		for(GameObject go: gameobjects){
			go.update();
		}

		long currentTime = System.currentTimeMillis();

		if (currentTime - lastWaveMoveTime >= waveMoveInterval) {
			if (wave.waveEmpty()) {
				// Make a new wave
				wave.clearWave();
				wave.generateWave();
			}

			wave.moveEnemies(640, 0, 400);
			System.out.println("Moved them");

			// Update the last wave move time
			lastWaveMoveTime = currentTime;
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
