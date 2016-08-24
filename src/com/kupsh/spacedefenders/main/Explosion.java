package com.kupsh.spacedefenders.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Explosion extends Entity {

	private int width, height = 32;
	private int tick = 0;
	private BufferedImage image;
	private SpriteSheet ss;
	private Handler handler;
	private int column = 1;
	private int row = 7;

	public Explosion(int x, int y, int velx, int vely, ID id, Handler handler,
			SpriteSheet ss, int height, int width) {
		super(x, y, velx, vely, id);
		this.width = width;
		this.height = height;
		this.width = width;
		this.handler = handler;
		this.ss = ss;
		image = ss.grabImage(column, row, width, height);
	}

	public void tick() {
		tick++;
		if(newObject == true)
			newObject = false;
		if(removeObject == true)
			handler.removeObject(this);
		if (tick == 40)
			remove();
		if (tick % 10 == 0) {
			column++;
			image = ss.grabImage(column, row, width, height);
		}
	}

	public void render(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
	}

	private void remove() {
		handler.removeObject(this);
	}

	public Rectangle getbounds() {
		return new Rectangle(x, y, width, height);
	}

}
