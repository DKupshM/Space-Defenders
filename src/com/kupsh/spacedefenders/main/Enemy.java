package com.kupsh.spacedefenders.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends Entity {

	private Handler handler;
	private Game game;
	private SpriteSheet ss;

	private int column = 1;
	private int ticks = 1;

	public Enemy(int x, int y, int velx, int vely, ID id, Game game,
			Handler handler, SpriteSheet ss) {
		super(x, y, velx, vely, id);
		this.game = game;
		this.handler = handler;
		this.ss = ss;
	}

	public void tick() {

		y += vely;
		x += velx;

		if(newObject == true)
			newObject = false;
		if(removeObject == true)
			handler.removeObject(this);
		
		ticks++;
		if (ticks % 60 == 0) {
			column++;
			if (column > 4)
				column = 1;
		}
		if(game.getSession() != Session.dualplayer)
			remove();
	}

	private void remove() {
		for (int i = 0; i < handler.object.size(); i++) {
			Entity tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.enemy) {
				if (tempObject.getX() < -32 || tempObject.getX() > Game.Width
						|| tempObject.getY() < -32
						|| tempObject.getY() > Game.Height) {
					handler.removeObject(tempObject);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawImage(ss.grabImage(column, 2, 32, 32), x, y, 32, 32, null);
	}

	public Rectangle getbounds() {
		return new Rectangle(x, y, 32, 32);
	}
}
