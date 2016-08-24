package com.kupsh.spacedefenders.main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {

	protected int x, y;
	protected ID id;
	protected int velx, vely;
	protected boolean newObject = true;
	protected boolean removeObject = false;

	public Entity(int x, int y, int velx, int vely, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.velx = velx;
		this.vely = vely;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getbounds();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}

	public int getVely() {
		return vely;
	}

	public void setVely(int vely) {
		this.vely = vely;
	}

	public boolean isNewObject() {
		return newObject;
	}

	public void setNewObject(boolean newObject) {
		this.newObject = newObject;
	}

	public boolean isRemoveObject() {
		return removeObject;
	}

	public void setRemoveObject(boolean removeObject) {
		this.removeObject = removeObject;
	}

}
