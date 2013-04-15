package com.zrbecker.javagame.entities;

import org.lwjgl.opengl.GL11;

import com.zrbecker.javagame.graphics.Color;
import com.zrbecker.javagame.graphics.Graphics;

public class Bullet extends AbstractEntity {

	public static final double SIZE = 1.0;
	public static final double ALTITUDE = 7.5;
	
	public static final double SPEED = 30.0;
	
	double timer = 0.0;
	
	public double direction;
	Color color;
	
	double dx, dz;
	boolean dead = false;
	
	public Bullet(double x, double z, double direction, Color color, String tag) {
		super(x, ALTITUDE, z, SIZE, SIZE, SIZE, tag);
		this.direction = direction;
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
		dx -= SPEED * Math.sin(Math.toRadians(direction)) * seconds;
		dz -= SPEED * Math.cos(Math.toRadians(direction)) * seconds;
		
		timer += seconds;
		if (timer > 1.0)
			dead = true;
		
		move(dx, 0.0, dz);
	}
	
	public boolean isDead() {
		return dead;
	}

	@Override
	public void collided(Entity entity) {
		// dead = true;
	}

}
