package com.kupsh.spacedefenders.main;

import java.util.Random;

public class Spawner {

	private Game game;
	private Handler handler;
	private Random rand;
	private SpriteSheet ss;

	private int ticks;

	public Spawner(Game game, Handler handler, Random random, SpriteSheet ss) {
		this.game = game;
		this.rand = random;
		this.handler = handler;
		this.ss = ss;
	}

	public void tick() {
		ticks++;
		if (ticks % 60 == 0)
			spawnAsteroid();

		if (ticks % 120 == 0)
			spawnEnemy();
	}

	private void spawnEnemy() {
		int vely = 0;
		int velx = 0;
		vely = rand.nextInt(2);
		if (vely == 0)
			vely = 2;
		vely *= -1;
		handler.addObject(new Enemy(rand.nextInt(Game.Width), Game.Height,
				velx, vely, ID.enemy, game, handler, ss));
	}

	private void spawnAsteroid() {
		int vely = 0;
		int velx = 0;

		int side = rand.nextInt(3);
		vely = rand.nextInt(3);
		if (vely == 0)
			vely = 2;
		vely *= -1;

		velx = rand.nextInt(4);
		if (side == 0) {
			velx *= 1;
			handler.addObject(new Astroid(0, rand.nextInt(Game.Height), velx,
					vely, ID.astroid, game, handler, ss));
		} else if (side == 1) {
			int xvalue = rand.nextInt(2);
			if (xvalue == 1) {
				velx *= -1;
			}
			handler.addObject(new Astroid(rand.nextInt(Game.Width),
					Game.Height, velx, vely, ID.astroid, game, handler, ss));
		} else if (side == 2) {
			velx *= -1;
			handler.addObject(new Astroid(Game.Width,
					rand.nextInt(Game.Height), velx, vely, ID.astroid, game,
					handler, ss));
		}
	}
}
