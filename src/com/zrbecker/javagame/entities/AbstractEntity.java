package com.zrbecker.javagame.entities;

public abstract class AbstractEntity implements Entity {
	double x, y, z;
	double width, height, depth;
	String tag;
	
	public AbstractEntity(double x, double y, double z, String tag) {
		this(x, y, z, 0, 0, 0, tag);
	}
	
	public AbstractEntity(double x, double y, double z, double width, double height, double depth, String tag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.tag = tag;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public void setPosition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void move(double dx, double dy, double dz) {
		x += dx;
		y += dy;
		z += dz;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}
	
	@Override
	public double getDepth() {
		return depth;
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public void setHeight(double height) {
		this.height = height;
	}
	
	@Override
	public void setDepth(double depth) {
		this.depth = depth;
	}
	
	@Override
	public void setDimensions(double width, double height, double depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	@Override
	public boolean collides(Entity entity) {
		if (x - width / 2 >= entity.getX() + entity.getWidth() / 2)
			return false;
		if (x + width / 2 <= entity.getX() - entity.getWidth() / 2)
			return false;

		if (y - height / 2 >= entity.getY() + entity.getHeight() / 2)
			return false;
		if (y + height / 2 <= entity.getY() - entity.getHeight() / 2)
			return false;

		if (z - depth / 2 >= entity.getZ() + entity.getDepth() / 2)
			return false;
		if (z + depth / 2 <= entity.getZ() - entity.getDepth() / 2)
			return false;
		
		return true;
	}

	@Override
	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String getTag() {
		return tag;
	}
}
