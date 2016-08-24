package com.kupsh.spacedefenders.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class PowerUp extends Entity {

	private Game game;
	private Handler handler;
	private Random rand;

	private boolean init = false;

	private int ticks;
	private int chance = 0;

	public enum Type {
		None(), TripleShot(), ShrapnelShot(), RegenShot(), MoreHealth(), UnlimitedAmmo(), MoreAmmo(), ClearScreen(), DoublePoints(), UnlimitedHealth();
	}

	public PowerUp(int x, int y, int velx, int vely, ID id, Game game,
			Handler handler, Random rand) {
		super(x, y, velx, vely, id);
		this.game = game;
		this.handler = handler;
		this.rand = rand;
		newPowerUp();
	}

	private void newPowerUp() {
		chance = rand.nextInt(100);
		if (chance < 10)
			game.setType(Type.TripleShot);
		else if (chance >= 10 && chance < 20)
			game.setType(Type.ShrapnelShot);
		else if (chance >= 20 && chance < 30)
			game.setType(Type.RegenShot);
		else if (chance >= 30 && chance < 40)
			game.setType(Type.MoreHealth);
		else if (chance >= 40 && chance < 50)
			game.setType(Type.UnlimitedAmmo);
		else if (chance >= 50 && chance < 60)
			game.setType(Type.MoreAmmo);
		else if (chance >= 60 && chance < 70)
			game.setType(Type.ClearScreen);
		else if (chance >= 70 && chance < 80)
			game.setType(Type.TripleShot);
		else if (chance >= 80 && chance < 90)
			game.setType(Type.DoublePoints);
		else if (chance >= 90 && chance < 100)
			game.setType(Type.UnlimitedHealth);
		System.out.println("New Power Up, Type: " + game.getType());

		// Put In Image Type

		if (game.getType() == Type.DoublePoints)
			game.setPowerUpImage(game.getImage("PowerUpDoublePointsImage"));
		else if (game.getType() == Type.UnlimitedHealth)
			game.setPowerUpImage(game.getImage("PowerUpUnlimitedHealthImage"));
		else if (game.getType() == Type.MoreHealth)
			game.setPowerUpImage(game.getImage("PowerUpHealthImage"));
		else if (game.getType() == Type.MoreAmmo)
			game.setPowerUpImage(game.getImage("PowerUpAmmoImage"));
		else if (game.getType() == Type.UnlimitedAmmo)
			game.setPowerUpImage(game.getImage("PowerUpUnlimitedAmmoImage"));
		else if (game.getType() == Type.ClearScreen)
			game.setPowerUpImage(game.getImage("PowerUpClearScreenImage"));
		else if (game.getType() == Type.RegenShot)
			game.setPowerUpImage(game.getImage("PowerUpRegenShotImage"));
		else if (game.getType() == Type.TripleShot)
			game.setPowerUpImage(game.getImage("PowerUpTripleShotImage"));
		else
			game.setPowerUpImage(game.getImage("PowerUpImage"));

	}

	public void tick() {
		if (ticks == 0) {
			if (game.getSession() == Session.dualplayer) {
				if (game.getType() == Type.ClearScreen) {
					game.output.println("typeChange , " + game.getServerId()
							+ " , 1");
				}
			}
			game.getSound("PowerUp").play();
			game.setPowerUpScreen(true);
		}
		if (game.getType() == Type.ClearScreen && init == false) {
			for (int i = 0; i < handler.object.size(); i++) {
				Entity tempObject = handler.object.get(i);
				if (tempObject.id == ID.astroid
						|| tempObject.getId() == ID.enemy) {
					handler.removeObject(tempObject);
				}
			}
			init = true;
		} else if (game.getType() == Type.MoreHealth && init == false) {
			HUD.Health += 50;
			init = true;
		} else if (game.getType() == Type.MoreAmmo && init == false) {
			HUD.Ammo += 100;
			init = true;
		}

		if (ticks == 80)
			game.setPowerUpScreen(false);
		if (ticks == 801) {
			remove();
			if (game.getSession() == Session.dualplayer)
				game.output.println("typeChange , " + game.getServerId()
						+ " , 2");
		}
		ticks++;
	}

	private void remove() {
		for (int i = 0; i < handler.object.size(); i++) {
			Entity tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.powerUp) {
				handler.removeObject(tempObject);
				game.setPowerUpActive(false);
				System.out.println("Removed Power Up, Type: " + game.getType());
				game.setType(Type.None);
			}
		}
	}

	// not really needed but has to be here to extend entity
	public void render(Graphics g) {

	}

	// not really needed but has to be here to extend entity
	public Rectangle getbounds() {
		return new Rectangle(x, y, 1, 1);
	}
}