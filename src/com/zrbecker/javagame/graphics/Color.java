package com.zrbecker.javagame.graphics;

import java.util.Random;

public class Color {
	public double red;
	public double green;
	public double blue;
	
	public static Random random = new Random();
	
	public Color(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public static Color getRandom() {
		return new Color(random.nextDouble(), random.nextDouble(), random.nextDouble());
	}
}
