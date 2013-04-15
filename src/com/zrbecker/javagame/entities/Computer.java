package com.zrbecker.javagame.entities;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.zrbecker.javagame.game.Game;
import com.zrbecker.javagame.game.ScoreBoard;
import com.zrbecker.javagame.graphics.Color;
import com.zrbecker.javagame.graphics.Graphics;

public class Computer extends AbstractEntity {
	public static final double WIDTH = 2.5;
	public static final double HEIGHT = 10.0;
	public static final double DEPTH = 2.5;

	public static final double SPEED = 20.0;
	public static final double TURN_SPEED = 90.0;
	
	public static Random random = new Random();

	public static enum MoveState { FORWARD_LEFT, FORWARD_RIGHT, BACKWARD_LEFT, BACKWARD_RIGHT };
	public static enum TurnState { LEFT, RIGHT };
	
	TurnState turnState = TurnState.LEFT;
	MoveState moveState = MoveState.FORWARD_LEFT;
	double stateTimer = 0.0;
	
	double direction = 0.0;
	Color color;
	double dx = 0.0;
	double dz = 0.0;
	
	public static final double GUN_COOLDOWN = 0.4;
	double gunTimer = 0.0;

	public static final int MAX_HITPOINTS = 5;
	int hitPoints = MAX_HITPOINTS;
	
	public Computer(double x, double z, Color color) {
		super(x, HEIGHT / 2, z, WIDTH, HEIGHT, DEPTH, "computer");
		this.color = color;
	}
	
	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glColor3d(color.red, color.green, color.blue);
		GL11.glTranslated(x, y, z);
		GL11.glRotated(direction, 0, 1, 0);
		GL11.glScaled(width, height, depth);
		Graphics.drawCube();
		GL11.glPopMatrix();
	}

	@Override
	public void update(double seconds) {
		dx = 0.0;
		dz = 0.0;
		
		stateTimer += seconds;
		if (stateTimer > 0.5) {
			switch (random.nextInt(4)) {
			case 0: moveState = MoveState.FORWARD_LEFT; break;
			case 1: moveState = MoveState.FORWARD_RIGHT; break;
			case 2: moveState = MoveState.BACKWARD_LEFT; break;
			case 3: moveState = MoveState.BACKWARD_RIGHT; break;
			}
			
			switch (random.nextInt(2)) {
			case 0: turnState = TurnState.LEFT; break;
			case 1: turnState = TurnState.RIGHT; break;
			}
			stateTimer = 0.0;
		}
		
		boolean left, right, forward, backward;
		switch (moveState) {
		case FORWARD_LEFT:
			forward = true; left = true; backward = false; right = false;
			break;
		case FORWARD_RIGHT:
			forward = true; left = false; backward = false; right = true;
			break;
		case BACKWARD_LEFT:
			forward = false; left = true; backward = true; right = false;
			break;
		case BACKWARD_RIGHT:
			forward = true; left = false; backward = true; right = false;
			break;
		default:
			forward = left = backward = right = false;
			break;
		}

		if (forward) {
			dx -= SPEED * Math.sin(Math.toRadians(direction)) * seconds;
			dz -= SPEED * Math.cos(Math.toRadians(direction)) * seconds;
		}
		if (backward) {
			dx += SPEED * Math.sin(Math.toRadians(direction)) * seconds;
			dz += SPEED * Math.cos(Math.toRadians(direction)) * seconds;
		}
		
		if (left) {
			dx -= SPEED * Math.cos(Math.toRadians(direction)) * seconds;
			dz += SPEED * Math.sin(Math.toRadians(direction)) * seconds;
		}
		if (right) {
			dx += SPEED * Math.cos(Math.toRadians(direction)) * seconds;
			dz -= SPEED * Math.sin(Math.toRadians(direction)) * seconds;
		}
		
		move(dx, 0.0, dz);
		
		boolean turnLeft, turnRight;
		switch (turnState) {
		case LEFT: turnLeft = true; turnRight = false; break;
		case RIGHT: turnLeft = false; turnRight = true; break;
		default: turnLeft = turnRight = false; break;
		}
		
		if (turnLeft)
			direction += TURN_SPEED * seconds;
		if (turnRight)
			direction -= TURN_SPEED * seconds;
		
		gunTimer += seconds;
		if (gunTimer > GUN_COOLDOWN) {
			double bulletX = x - 1.5 * WIDTH * Math.sin(Math.toRadians(direction));
			double bulletZ = z - 1.5 * WIDTH * Math.cos(Math.toRadians(direction));
			Game.getInstance().addBullet(new Bullet(bulletX, bulletZ, direction, color, "computer bullet"));
			gunTimer = 0.0;
		}
	}

	@Override
	public void collided(Entity entity) {
		if (entity.getTag().equals("wall") || entity.getTag().equals("computer")) {
			move(-dx, 0.0, -dz);
			dx = dz = 0.0;
			direction += 180.0;
			stateTimer = 0.5;
		}
		if (entity.getTag().equals("player bullet")) {
			hitPoints -= 1;
			if (hitPoints == 0) {
				// Respawn
				hitPoints = MAX_HITPOINTS;
				setPosition(Game.random.nextInt(180) - 90, getY(), Game.random.nextInt(180) - 90);
				ScoreBoard.computerDeaths += 1;
				ScoreBoard.playerKills += 1;
			}
		}
		if (entity.getTag().equals("computer bullet")) {
			hitPoints -= 1;
			if (hitPoints == 0) {
				// Respawn
				hitPoints = MAX_HITPOINTS;
				setPosition(Game.random.nextInt(180) - 90, getY(), Game.random.nextInt(180) - 90);
				ScoreBoard.computerDeaths += 1;
				ScoreBoard.computerKills += 1;
			}
		}
	}

}
