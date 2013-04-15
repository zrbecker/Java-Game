package com.zrbecker.javagame.graphics;

import org.lwjgl.opengl.GL11;

public class Graphics {

	public static void drawCube() {
		GL11.glBegin(GL11.GL_QUADS);
		
		// Front
		GL11.glVertex3d(-0.5, -0.5, -0.5);
		GL11.glVertex3d( 0.5, -0.5, -0.5);
		GL11.glVertex3d( 0.5,  0.5, -0.5);
		GL11.glVertex3d(-0.5,  0.5, -0.5);
		
		// Back
		GL11.glVertex3d(-0.5,  0.5,  0.5);
		GL11.glVertex3d( 0.5,  0.5,  0.5);
		GL11.glVertex3d( 0.5, -0.5,  0.5);
		GL11.glVertex3d(-0.5, -0.5,  0.5);
		
		// Top
		GL11.glVertex3d(-0.5,  0.5, -0.5);
		GL11.glVertex3d( 0.5,  0.5, -0.5);
		GL11.glVertex3d( 0.5,  0.5,  0.5);
		GL11.glVertex3d(-0.5,  0.5,  0.5);
		
		// Bottom
		GL11.glVertex3d(-0.5, -0.5,  0.5);
		GL11.glVertex3d( 0.5, -0.5,  0.5);
		GL11.glVertex3d( 0.5, -0.5, -0.5);
		GL11.glVertex3d(-0.5, -0.5, -0.5);
		
		// Right
		GL11.glVertex3d( 0.5, -0.5, -0.5);
		GL11.glVertex3d( 0.5, -0.5,  0.5);
		GL11.glVertex3d( 0.5,  0.5,  0.5);
		GL11.glVertex3d( 0.5,  0.5, -0.5);
		
		// Left
		GL11.glVertex3d(-0.5,  0.5, -0.5);
		GL11.glVertex3d(-0.5,  0.5,  0.5);
		GL11.glVertex3d(-0.5, -0.5,  0.5);
		GL11.glVertex3d(-0.5, -0.5, -0.5);
		
		GL11.glEnd();
	}
	
}
