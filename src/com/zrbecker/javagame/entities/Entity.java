package com.zrbecker.javagame.entities;

public interface Entity {	
	public double getX();
	public double getY();
	public double getZ();
	public void setX(double x);
	public void setY(double y);
	public void setZ(double z);
	public void setPosition(double x, double y, double z);
	public void move(double dx, double dy, double dz);
	
	public double getWidth();
	public double getHeight();
	public double getDepth();
	public void setWidth(double width);
	public void setHeight(double height);
	public void setDepth(double depth);
	public void setDimensions(double width, double height, double depth);
	
	public void setTag(String tag);
	public String getTag();
	
	public void render();
	public void update(double seconds);
	
	public boolean collides(Entity entity);
	public void collided(Entity entity);
}
