package com.zrbecker.javagame.game;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.zrbecker.javagame.entities.Bullet;
import com.zrbecker.javagame.entities.Computer;
import com.zrbecker.javagame.entities.Floor;
import com.zrbecker.javagame.entities.Player;
import com.zrbecker.javagame.entities.Wall;
import com.zrbecker.javagame.graphics.Color;


public class Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Java Game";
	
	public static final int NUM_COMPUTERS = 20;
	
	public static final double ARENA_SIZE = 200.0;
	public static final double WALL_SIZE = 25.0;
	
	public static Random random = new Random();
	
	Player player;
	Wall[] walls;
	Floor floor;
	
	List<Computer> computers;
	List<Bullet> bullets;
	
	public static Game instance = null;
	
	public Game() throws Exception {
		if (instance != null)
			throw new Exception("Only one game may exist!");
		instance = this;
		
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		Display.setTitle(TITLE);
		
		double last = ((double) Sys.getTime()) / Sys.getTimerResolution();
		double delta = 0.0;
		
		double lastUpdate = 0.0;
		double tickUpdate = 1.0 / 60.0;
		
		double lastFPSReport = 0.0;
		int frames = 0;
		
		initialize();
		
		while (!Display.isCloseRequested()) {
			double now = ((double) Sys.getTime()) / Sys.getTimerResolution();
			delta = now - last;
			last = now;
			
			lastUpdate += delta;
			while (lastUpdate > tickUpdate) {
				update(tickUpdate);
				lastUpdate -= tickUpdate;
			}
			
			lastFPSReport += delta;
			while (lastFPSReport > 1.0) {
				Display.setTitle(TITLE + "  |  " + frames + " fps");
				frames = 0;
				lastFPSReport -= 1.0;
			}
			
			render();
			++frames;
			
			Display.update();
		}
		
		System.out.println("Score Board");
		System.out.println("Player Kills: " + ScoreBoard.playerKills);
		System.out.println("Player Deaths: " + ScoreBoard.playerDeaths);
		System.out.println("Computer Kills: " + ScoreBoard.computerKills);
		System.out.println("Computer Deaths: " + ScoreBoard.computerDeaths);
		
		Display.destroy();
	}
	
	public static Game getInstance() {
		return instance;
	}
	
	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}
	
	private void initialize() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-1.0, 1.0, -1.0, 1.0, 1.5, 1024.0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		player = new Player(random.nextInt(180) - 90, random.nextInt(180) - 90, Color.getRandom());
		
		floor = new Floor(ARENA_SIZE, ARENA_SIZE, Color.getRandom());
		walls = new Wall[4];
		walls[0] = new Wall(  0.0,  ARENA_SIZE / 2,  ARENA_SIZE, WALL_SIZE,   5.0, Color.getRandom()); // North Wall
		walls[1] = new Wall(  0.0, -ARENA_SIZE / 2,  ARENA_SIZE, WALL_SIZE,   5.0, Color.getRandom()); // South Wall
		walls[2] = new Wall( ARENA_SIZE / 2,   0.0,    5.0, WALL_SIZE, ARENA_SIZE, Color.getRandom()); // East Wall
		walls[3] = new Wall(-ARENA_SIZE / 2,   0.0,    5.0, WALL_SIZE, ARENA_SIZE, Color.getRandom()); // West Wall

		computers = new LinkedList<Computer>();
		bullets = new LinkedList<Bullet>();
		
		for (int i = 0; i < NUM_COMPUTERS; ++i)
			computers.add(new Computer(random.nextInt(180) - 90, random.nextInt(180) - 90, Color.getRandom()));
	}
	
	private void update(double seconds) {
		player.update(seconds);
		for (Computer computer : computers)
			computer.update(seconds);
		for (Bullet bullet : bullets)
			bullet.update(seconds);
		
		// Check wall collisions
		for (Wall wall : walls) {
			if (wall.collides(player))
				player.collided(wall);
			for (Computer computer : computers) {
				if (wall.collides(computer))
					computer.collided(wall);
			}
		}
		// Check person collisions
		for (Computer computer : computers) {
			if (computer.collides(player)) {
				computer.collided(player);
				player.collided(computer);
			}
			for (Computer computer2 : computers) {
				if (computer != computer2 && computer.collides(computer2)) {
					computer.collided(computer2);
					computer2.collided(computer);
				}
			}
		}
		// Check bullet collisions
		for (Bullet bullet : bullets) {
			if (bullet.collides(player)) {
				bullet.collided(player);
				player.collided(bullet);
			}
			for (Computer computer : computers) {
				if (bullet.collides(computer)) {
					bullet.collided(computer);
					computer.collided(bullet);
				}
			}
		}
		
		// Remove dead bullets
		Iterator<Bullet> bullet_it = bullets.iterator();
		while (bullet_it.hasNext()) {
			if (bullet_it.next().isDead()) {
				bullet_it.remove();
			}
		}
	}
	
	private void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();

		// Position Camera
		GL11.glRotated(-player.getDirection(), 0, 1, 0);
		GL11.glTranslated(-player.getX(), -player.getHeight() * 0.75, -player.getZ());
		
		player.render();
		
		floor.render();
		for (Wall wall : walls)
			wall.render();
		
		for (Computer computer : computers)
			computer.render();
		
		for (Bullet bullet : bullets)
			bullet.render();
	}

	public static void main(String[] args) {
		try {
			new Game();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
