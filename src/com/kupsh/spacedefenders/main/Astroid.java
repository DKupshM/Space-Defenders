package com.kupsh.spacedefenders.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Astroid extends Entity {

	private Handler handler;
	private Game game;
	private SpriteSheet ss;

	public Astroid(int x, int y, int velx, int vely, ID id, Game game,
			Handler handler, SpriteSheet ss) {
		super(x, y, velx, vely, id);
		this.handler = handler;
		this.game = game;
		this.ss = ss;
	}

	public void tick() {

		y += vely;
		x += velx;
		
		if(newObject == true)
			newObject = false;
		if(removeObject == true)
			handler.removeObject(this);
		if(game.getSession() != Session.dualplayer)
			remove();
	}

	private void remove() {
		for (int i = 0; i < handler.object.size(); i++) {
			Entity tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.astroid) {
				if (tempObject.getX() < -32 || tempObject.getX() > Game.Width
						|| tempObject.getY() < -32
						|| tempObject.getY() > Game.Height) {
					handler.removeObject(tempObject);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.drawImage(ss.grabImage(1, 3, 32, 32), x, y, 32, 32, null);
	}

	public Rectangle getbounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
