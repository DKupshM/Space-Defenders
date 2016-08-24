package com.kupsh.spacedefenders.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.kupsh.spacedefenders.main.PowerUp.Type;

public class Bullet extends Entity {

	private Handler handler;
	private Random rand;
	private Game game;
	private SpriteSheet ss;

	private int tick;
	private int temptick;
	private int column = 1;
	private int row = 4;

	public Bullet(int x, int y, int velx, int vely, ID id, Game game,
			Handler handler, SpriteSheet ss, Random rand, int direction) {
		super(x, y, velx, vely, id);
		this.game = game;
		this.handler = handler;
		this.ss = ss;
		this.rand = rand;
		if (direction == 1)
			row = 4;
		else if (direction == 2)
			row = 5;
		else if (direction == 3)
			row = 6;
	}

	public void tick() {
		x += velx;
		y += vely;
		collide();
		remove();
		if(newObject == true)
			newObject = false;
		if(removeObject == true)
			handler.removeObject(this);
		tick++;
		if (id == ID.tempBullet || id == ID.othertempBullet) {
			temptick++;
			if (temptick == 20) {
				handler.removeObject(this);
			}
		}
		if (row == 4) {
			if (tick % 60 == 0) {
				column++;
				if (column > 4)
					column = 1;
			}
		}
	}

	private void collide() {
		for (int i = 0; i < handler.object.size(); i++) {
			Entity tempObject = handler.object.get(i);
			if (tempObject.getbounds().intersects(getbounds())) {
				if (tempObject.getId() == ID.astroid) {
					handler.removeObject(tempObject);
					handler.removeObject(this);
					handler.addObject(new Explosion(tempObject.getX(),
							tempObject.getY(), 0, 0, ID.explosion, handler, ss,
							32, 32));
					if (id == ID.bullet && game.getType() != Type.ShrapnelShot) {
						if (game.getSession() == Session.singleplayer) {
							HUD.Ammo += 2;
							if (game.getType() != Type.DoublePoints)
								HUD.Score += 500;
							else
								HUD.Score += 1000;
						} else if (game.getSession() == Session.dualplayer) {
							int ammo = HUD.Ammo + 1;
							game.output.println("ammoChanged , "
									+ game.getServerId() + " , " + ammo);
						}
						if (game.getType() == Type.RegenShot)
							if (game.getSession() == Session.singleplayer)
								HUD.Health += 4;
							else if (game.getSession() == Session.dualplayer) {
								int health = HUD.Health + 4;
								game.output.println("playerDamaged , "
										+ game.getServerId() + " , " + health);
							}
						if (!game.isPowerUpActive()) {
							int powerUpChance = rand.nextInt(100);
							if (powerUpChance < 100) {
								game.setPowerUpActive(true);
								handler.addObject(new PowerUp(100000, 10000, 0,
										0, ID.powerUp, game, handler, rand));
							}
						}
					} else if (id == ID.tempBullet
							|| game.getType() == Type.ShrapnelShot) {
						if (game.getSession() == Session.singleplayer) {
							HUD.Ammo += 2;
							handler.addObject(new Bullet(tempObject.getX() + 8,
									tempObject.getY() + 32, 0, 10,
									ID.tempBullet, game, handler, ss, rand, 1));
							handler.addObject(new Bullet(tempObject.getX() + 8,
									tempObject.getY() + 32, 0, -10,
									ID.tempBullet, game, handler, ss, rand, 1));
							handler.addObject(new Bullet(tempObject.getX() + 8,
									tempObject.getY() + 32, 10, 0,
									ID.tempBullet, game, handler, ss, rand, 2));
							handler.addObject(new Bullet(tempObject.getX() + 8,
									tempObject.getY() + 32, -10, 0,
									ID.tempBullet, game, handler, ss, rand, 3));
							if (game.getType() != Type.DoublePoints)
								HUD.Score += 500;
							else
								HUD.Score += 1000;
						}
					}
				} else if (tempObject.getId() == ID.enemy) {
					handler.removeObject(tempObject);
					handler.removeObject(this);
					handler.addObject(new Explosion(tempObject.getX(),
							tempObject.getY(), 0, 0, ID.explosion, handler, ss,
							32, 32));
					if (id == ID.bullet && game.getType() != Type.ShrapnelShot) {
						if (game.getSession() == Session.singleplayer) {
							HUD.Ammo += 1;
							if (game.getType() != Type.DoublePoints)
								HUD.Score += 750;
							else
								HUD.Score += 1500;
						} else if (game.getSession() == Session.dualplayer) {
							int ammo = HUD.Ammo + 1;
							game.output.println("ammoChanged , "
									+ game.getServerId() + " , " + ammo);
						}
						if (game.getType() == Type.RegenShot) {
							if (game.getSession() == Session.singleplayer)
								HUD.Health += 4;
							else if (game.getSession() == Session.dualplayer) {
								int health = HUD.Health + 4;
								game.output.println("playerDamaged , "
										+ game.getServerId() + " , " + health);
							}
						}
					}  else if (id == ID.tempBullet
							|| game.getType() == Type.ShrapnelShot) {
						if (game.getSession() == Session.singleplayer) {
							HUD.Ammo += 2;
							handler.addObject(new Bullet(tempObject.getX() + 8,
									tempObject.getY() + 32, 0, 10,
									ID.tempBullet, game, handler, ss, rand, 1));
							handler.addObject(new Bullet(tempObject.getX() + 8,
									tempObject.getY() + 32, 0, -10,
									ID.tempBullet, game, handler, ss, rand, 1));
							handler.addObject(new Bullet(tempObject.getX() + 8,
									tempObject.getY() + 32, 10, 0,
									ID.tempBullet, game, handler, ss, rand, 2));
							handler.addObject(new Bullet(tempObject.getX() + 8,
									tempObject.getY() + 32, -10, 0,
									ID.tempBullet, game, handler, ss, rand, 3));
							if (game.getType() != Type.DoublePoints)
								HUD.Score += 500;
							else
								HUD.Score += 1000;
						}
					}
				}
			}
		}
	}

	private void remove() {
		for (int i = 0; i < handler.object.size(); i++) {
			Entity tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.bullet
					|| tempObject.getId() == ID.otherbullet) {
				if (tempObject.getX() < -32 || tempObject.getX() > Game.Width
						|| tempObject.getY() < -32
						|| tempObject.getY() > Game.Height) {
					handler.removeObject(tempObject);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(ss.grabImage(column, row, 16, 16), x, y, 16, 16, null);
	}

	public Rectangle getbounds() {
		return new Rectangle(x, y, 16, 16);
	}

}
