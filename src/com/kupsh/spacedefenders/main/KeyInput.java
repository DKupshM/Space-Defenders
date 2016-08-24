package com.kupsh.spacedefenders.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.kupsh.spacedefenders.main.PowerUp.Type;

public class KeyInput extends KeyAdapter {

	private Game game;
	private Handler handler;
	private Random rand;
	private SpriteSheet ss;

	public KeyInput(Game game, Handler handler, Random rand, SpriteSheet ss) {
		this.game = game;
		this.handler = handler;
		this.rand = rand;
		this.ss = ss;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE)
			System.exit(0);
		if (game.getState() == State.Game) {
			for (int i = 0; i < handler.object.size(); i++) {
				Entity tempObject = handler.object.get(i);
				if (tempObject.getId() == ID.player) {
					if (key == KeyEvent.VK_W) {
						tempObject.setVely(-5);
					} else if (key == KeyEvent.VK_S) {
						tempObject.setVely(5);
					}
					if (key == KeyEvent.VK_D) {
						tempObject.setVelx(5);
					} else if (key == KeyEvent.VK_A) {
						tempObject.setVelx(-5);
					}
					if (key == KeyEvent.VK_SPACE) {
						game.setSpaceActive(true);
						if (game.getSession() == Session.singleplayer) {
							if (game.getType() == Type.UnlimitedAmmo) {
								handler.addObject(new Bullet(
										tempObject.getX() + 8, tempObject
												.getY() + 32, 0, 10, ID.bullet,
										game, handler, ss, rand, 1));
							} else if (HUD.Ammo > 0) {
								handler.addObject(new Bullet(
										tempObject.getX() + 8, tempObject
												.getY() + 32, 0, 10, ID.bullet,
										game, handler, ss, rand, 1));

								if (game.getType() == Type.TripleShot) {
									// velx = 10
									handler.addObject(new Bullet(tempObject
											.getX() + 8,
											tempObject.getY() + 32, 10, 0,
											ID.bullet, game, handler, ss, rand,
											2));
									// velx = -10
									handler.addObject(new Bullet(tempObject
											.getX() + 8,
											tempObject.getY() + 32, -10, 0,
											ID.bullet, game, handler, ss, rand,
											3));
								}
								HUD.Ammo--;
							}
						}
					}
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (game.getState() == State.Game) {
			for (int i = 0; i < handler.object.size(); i++) {
				Entity tempObject = handler.object.get(i);
				if (tempObject.getId() == ID.player) {
					if (key == KeyEvent.VK_W) {
						tempObject.setVely(0);
					} else if (key == KeyEvent.VK_S) {
						tempObject.setVely(0);
					}
					if (key == KeyEvent.VK_D) {
						tempObject.setVelx(0);
					} else if (key == KeyEvent.VK_A) {
						tempObject.setVelx(0);
					}
					if (key == KeyEvent.VK_SPACE) {
						game.setSpaceActive(false);
					}
				}
			}
		}
	}
}
