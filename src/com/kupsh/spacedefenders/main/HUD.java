package com.kupsh.spacedefenders.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.kupsh.spacedefenders.main.PowerUp.Type;

public class HUD {

	public static int Health = 100;
	public static int Ammo = 100;
	public static int Score = 0;

	public static int otherHealth = 100;
	public static int otherAmmo = 100;
	public static int otherScore = 0;

	private int greenValue = 255;
	private int otherGreenValue = 255;

	private Game game;

	public HUD(Game game) {
		this.game = game;
	}

	public void tick() {
		Health = Util.clamp(Health, 0, 100);
		otherHealth = Util.clamp(otherHealth, 0, 100);

		greenValue = Health * 2;
		greenValue = Util.clamp(greenValue, 0, 255);
		otherGreenValue = otherHealth * 2;
		otherGreenValue = Util.clamp(otherGreenValue, 0, 255);
		if (game.getType() != Type.DoublePoints) {
			Score++;
		} else {
			Score += 2;
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		if (game.getSession() == Session.dualplayer)
			g2d.fill(getOtherHealthBarBounds());
		g2d.fill(getHealthBarBounds());
		g2d.setColor(new Color(75, greenValue, 0));
		g2d.fill(getHealthBarAmountBounds());
		if (game.getSession() == Session.dualplayer) {
			g2d.setColor(new Color(75, otherGreenValue, 0));
			g2d.fill(getOtherHealthBarAmountBounds());
		}
		g2d.setColor(Color.black);
		g2d.draw(getHealthBarBounds());
		if (game.getSession() == Session.dualplayer) {
			g2d.draw(getOtherHealthBarBounds());
		}
		g.setColor(Color.white);
		g.drawString("Ammo: " + HUD.Ammo, 25, 75);
		if (game.getSession() == Session.dualplayer)
			g.drawString("Ammo: " + HUD.otherAmmo, Game.Width - 75, 75);
		g.drawString("Score: " + Score, 25, 100);
	}

	private Rectangle getOtherHealthBarBounds() {
		return new Rectangle(Game.Width - 100, 0, 100, 50);
	}

	private Rectangle getOtherHealthBarAmountBounds() {
		return new Rectangle(Game.Width - 100, 0, otherHealth, 50);
	}

	private Rectangle getHealthBarBounds() {
		return new Rectangle(0, 0, 100, 50);
	}

	private Rectangle getHealthBarAmountBounds() {
		return new Rectangle(0, 0, Health, 50);
	}
}
