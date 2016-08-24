package com.kupsh.spacedefenders.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.kupsh.spacedefenders.main.PowerUp.Type;

public class Player extends Entity {

	private Handler handler;
	private Game game;
	private SpriteSheet ss;

	private int tick;
	private int column = 1;

	public Player(int x, int y, int velx, int vely, ID id, Game game,
			Handler handler, SpriteSheet ss) {
		super(x, y, velx, vely, id);
		this.game = game;
		this.handler = handler;
		this.ss = ss;
	}

	public void tick() {
		if (game.getSession() == Session.singleplayer) {
			x += velx;
			y += vely;
		}

		if (newObject == true)
			newObject = false;
		if (removeObject == true)
			handler.removeObject(this);

		tick++;
		if (tick % 60 == 0) {
			column++;
			if (column > 4)
				column = 1;
		}
	}

	public void render(Graphics g) {
		g.drawImage(ss.grabImage(column, 1, 32, 32), x, y, 32, 32, null);
		collision();
		x = Util.clamp(x, 0, Game.Width - 32);
		y = Util.clamp(y, 0, Game.Height - 32);
	}

	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			Entity tempObject = handler.object.get(i);
			if (tempObject.getbounds().intersects(getbounds())) {
				if (tempObject.getId() == ID.enemy
						|| tempObject.getId() == ID.astroid) {
					handler.removeObject(tempObject);
					if (id == ID.player) {
						if (game.getSession() == Session.singleplayer)
							HUD.Ammo += 1;
						else if (game.getSession() == Session.dualplayer) {
							int ammo = HUD.Ammo + 1;
							game.output.println("ammoChanged , "
									+ game.getServerId() + " , " + ammo);
						}
						if (game.getType() != Type.UnlimitedHealth)
							if (game.getSession() == Session.singleplayer)
								HUD.Health -= 10;
							else if (game.getSession() == Session.dualplayer) {
								int health = HUD.Health - 10;
								game.output.println("healthDamaged , "
										+ game.getServerId() + " , " + health);
							}
					}
				}
			}
		}
	}

	public Rectangle getbounds() {
		return new Rectangle(x, y, 32, 32);
	}
}
