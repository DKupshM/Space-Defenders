package com.kupsh.spacedefenders.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class LoadingScreen {
	private Game game;

	private int ticks;
	private int loadingamount = 0;
	private int loadingamountper;
	private int loadingamountmax;
	private BufferedImage LoadingScreen;

	public LoadingScreen(Game game, BufferedImageLoader loader) {
		this.game = game;
		// load image from resource directory
		LoadingScreen = loader.loadImage("");
		loadingamountper = (int) (Game.Width / 6.4);
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(LoadingScreen, 0, 0, Game.Width, Game.Height, null);

		g.drawRect((int) (Game.Width / 64), (int) (Game.Height / 48),
				(int) (Game.Width / 1.066), (int) (Game.Height / 19.2));
		g.fillRect((int) (Game.Width / 64), (int) (Game.Height / 48),
				(int) (loadingamount * (Game.Width / 640)),
				(int) (Game.Height / 19.2));

	}

	public void tick() {
		loadingamountmax = (int) (Game.Width / 6.4);
		loadingamountper = (int) (loadingamountmax / 100);
		if (loadingamount < loadingamountmax) {
			loadingamount += loadingamountper;
		}
		ticks++;
		if (ticks == 100) {
			game.init();
		}
		if (ticks == 200) {
			game.setState(State.menu);
			System.out.println("Starting");
		}
	}
}
