package com.zrbecker.javagame.entities;

import org.lwjgl.input.Keyboard;

import com.zrbecker.javagame.game.Game;
import com.zrbecker.javagame.game.ScoreBoard;
import com.zrbecker.javagame.graphics.Color;

public class Player extends AbstractEntity {
	public static final double WIDTH = 2.5;
	public static final double HEIGHT = 10.0;
	public static final double DEPTH = 2.5;
	
	public static final double SPEED = 20;
	public static final double TURN_SPEED = 90;
	
	private double direction = 0.0;
	
	double dx = 0.0;
	double dz = 0.0;
	
	public static final double GUN_COOLDOWN = 0.2;
	double gunTimer = 0.0;
	
	public static final int MAX_HITPOINTS = 5;
	int hitPoints = MAX_HITPOINTS;
	
	Color color;
	
	public Player(double x, double z, Color color) {
		super(x, HEIGHT / 2, z, WIDTH, HEIGHT, DEPTH, "player");
		this.color = color;
	}
	
	public double getDirection() {
		return direction;
	}
	
	@Override
	public void render() {
		
	}

	@Override
	public void update(double seconds) {
		dx = 0.0;
		dz = 0.0;

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			dx -= SPEED * Math.sin(Math.toRadians(direction)) * seconds;
			dz -= SPEED * Math.cos(Math.toRadians(direction)) * seconds;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			dx += SPEED * Math.sin(Math.toRadians(direction)) * seconds;
			dz += SPEED * Math.cos(Math.toRadians(direction)) * seconds;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			dx -= SPEED * Math.cos(Math.toRadians(direction)) * seconds;
			dz += SPEED * Math.sin(Math.toRadians(direction)) * seconds;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			dx += SPEED * Math.cos(Math.toRadians(direction)) * seconds;
			dz -= SPEED * Math.sin(Math.toRadians(direction)) * seconds;
		}
		
		gunTimer += seconds;
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && gunTimer > GUN_COOLDOWN) {
			double bulletX = x - 1.5 * WIDTH * Math.sin(Math.toRadians(direction));
			double bulletZ = z - 1.5 * WIDTH * Math.cos(Math.toRadians(direction));
			Game.getInstance().addBullet(new Bullet(bulletX, bulletZ, direction, color, "player bullet"));
			gunTimer = 0.0;
		}
		
		move(dx, 0.0, dz);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			direction += TURN_SPEED * seconds;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			direction -= TURN_SPEED * seconds;
	}

	@Override
	public void collided(Entity entity) {
		if (entity.getTag().equals("wall") || entity.getTag().equals("computer")) {
			move(-dx, 0.0, -dz);
			dx = dz = 0.0;
		}
		if (entity.getTag().equals("player bullet")) {
			System.out.println("You shot yourself!?");
		}
		if (entity.getTag().equals("computer bullet")) {
			hitPoints -= 1;
			if (hitPoints == 0) {
				// Respawn
				hitPoints = MAX_HITPOINTS;
				setPosition(Game.random.nextInt(180) - 90, getY(), Game.random.nextInt(180) - 90);
				ScoreBoard.playerDeaths += 1;
				ScoreBoard.computerKills += 1;
			}
		}
	}
}
