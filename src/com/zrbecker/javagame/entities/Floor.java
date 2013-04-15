package com.zrbecker.javagame.entities;

import org.lwjgl.opengl.GL11;

import com.zrbecker.javagame.graphics.Color;
import com.zrbecker.javagame.graphics.Graphics;

public class Floor extends AbstractEntity {
	
	Color color;
	
	public Floor(double width, double depth, Color color) {
		super(0, 0, 0, width, 1.0, depth, "floor");
		this.color = color;
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glColor3d(color.red, color.green, color.blue);
		GL11.glTranslated(x, y, z);
		GL11.glScaled(width, height, depth);
		Graphics.drawCube();
		GL11.glPopMatrix();
	}

	@Override
	public void update(double seconds) {
		
	}

	@Override
	public void collided(Entity entity) {
		
	}
	
}
