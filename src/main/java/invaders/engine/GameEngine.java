package invaders.engine;

import java.util.ArrayList;
import java.util.List;
import invaders.GameObject;
import invaders.entities.Bunker;
import invaders.entities.Player;
import invaders.entities.Enemy;
import invaders.logic.BunkerBuilder;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.logic.Wave;
import invaders.entities.Projectile;
import java.util.Iterator;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private Player player;
	private Wave wave;
	private boolean gameLost = false;

	private boolean left;
	private boolean right;

	private long lastWaveMoveTime;
	private long waveMoveInterval = 250; // interval in milliseconds

	private List<Projectile> playerProjectiles;
	private List<Projectile> enemyProjectiles;
	private List<Bunker> bunkers;
	
	private int lives = 3;

	public GameEngine(String config){
		// setup for lists
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();
		playerProjectiles = new ArrayList<Projectile>();
		enemyProjectiles = new ArrayList<Projectile>();

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

		// generate bunkers
		BunkerBuilder bunkerBuilder = new BunkerBuilder("config.json");
		bunkers = bunkerBuilder.getBunkers();
		for (Bunker bunker : bunkers) {
			renderables.add(bunker);
		}

		// time since last enemy move
		lastWaveMoveTime = System.currentTimeMillis();
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){

		if(lives <= 0){
			gameLost = true;
		}

		// stop game if lost
		if(gameLost == true){
			return;
		}

		movePlayer();
		for(GameObject go: gameobjects){
			go.update();
		}

		// loop through player projectiles
		// Create a list to store projectiles that need to be removed
		List<Projectile> projectilesToRemove = new ArrayList<>();

		for (Projectile p : playerProjectiles) {
			// Check if offscreen
			if (p.offscreen()) {
				projectilesToRemove.add(p);
				renderables.remove(p);
			}

			// if collides with an enemy
			for (ArrayList<Enemy> enemyRow : wave.getEnemyList()) {
				Iterator<Enemy> enemyIterator = enemyRow.iterator();
				while (enemyIterator.hasNext()) {
					Enemy enemy = enemyIterator.next();
					if (p.getCollider().isColliding(enemy.getCollider())) {
						enemyIterator.remove();
						renderables.remove(enemy);
						renderables.remove(p);
						projectilesToRemove.add(p);
						waveMoveInterval -= 5;
					}
				}
			}

			// if collides with bunker, remove projectile
			for (Bunker bunker : bunkers) {
				Iterator<Bunker> bunkerIterator = bunkers.iterator();
				while (bunkerIterator.hasNext()) {
					Bunker b = bunkerIterator.next();
					if (p.getCollider().isColliding(b.getCollider()) && bunker.getStillActive() == true) {
						renderables.remove(p);
						projectilesToRemove.add(p);
					}
				}
			}
		}

		// Remove the projectiles that need to be removed
		playerProjectiles.removeAll(projectilesToRemove);


		// Loop through enemy projectiles
		// Create a list to store enemy projectiles that need to be removed
		List<Projectile> enemyProjectilesToRemove = new ArrayList<>();

		for (Projectile p : enemyProjectiles) {
			// Check if offscreen
			if (p.offscreen()) {
				enemyProjectilesToRemove.add(p);
				renderables.remove(p);
			}

			// If hits player
			if (p.getCollider().isColliding(player.getCollider())) {
				lives--;
				enemyProjectilesToRemove.add(p);
				renderables.remove(p);
			}

			// If hits bunker
			for(Bunker bunker : bunkers){
				if (p.getCollider().isColliding(bunker.getCollider()) && bunker.getStillActive() == true) {
					bunker.takeDamage(1);
					enemyProjectilesToRemove.add(p);
					if(bunker.getTimesHit() > 3){
						renderables.remove(bunker);
					}
					renderables.remove(p);
				}
			}
		}

		// Remove the enemy projectiles that need to be removed
		enemyProjectiles.removeAll(enemyProjectilesToRemove);

		long currentTime = System.currentTimeMillis();

		if (currentTime - lastWaveMoveTime >= waveMoveInterval) {
			if (wave.waveEmpty()) {
				// Make a new wave
				wave.clearWave();
				wave.generateWave();
			}

			wave.moveEnemies(640, 0, 400);
			if(enemyProjectiles.size() < 3){
				Projectile shot = wave.enemiesShoot();
				enemyProjectiles.add(shot);
				renderables.add(shot);
			}

			// Update the last wave move time
			lastWaveMoveTime = currentTime;
		}

		for(Projectile projectile: enemyProjectiles){
			projectile.move();
		}

		for(Projectile projectile: playerProjectiles){
			projectile.move();
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
		Projectile projectile = player.shoot();
		if (playerProjectiles.size() >= 1){
			return false;
		}
		playerProjectiles.add(projectile);
		renderables.add(projectile);
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

	public int getLives(){
		return lives;
	}
}
