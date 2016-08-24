package com.kupsh.spacedefenders.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

	private Game game;

	public Menu(Game game) {
		this.game = game;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt = new Font("arial", Font.BOLD, 25);
		g.setFont(fnt);

		// fill Rectangles
		g2d.setColor(Color.white);
		g2d.fill(SinglePlayer());
		g2d.fill(Options());
		g2d.fill(Quit());
		if (game.isServer() == false)
			g2d.setColor(Color.red);
		g2d.fill(MultiPlayer());

		g2d.setColor(Color.black);
		Util.CenterIntoRect(g, "Single Player", fnt, SinglePlayer());
		Util.CenterIntoRect(g, "Multi Player", fnt, MultiPlayer());
		Util.CenterIntoRect(g, "Options", fnt, Options());
		Util.CenterIntoRect(g, "Quit", fnt, Quit());
	}

	public Rectangle Quit() {
		return new Rectangle(120, 310, 400, 50);
	}

	public Rectangle Options() {
		return new Rectangle(120, 246, 400, 50);
	}

	public Rectangle MultiPlayer() {
		return new Rectangle(120, 183, 400, 50);
	}

	public Rectangle SinglePlayer() {
		return new Rectangle(120, 120, 400, 50);
	}
}
