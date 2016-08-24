package com.kupsh.spacedefenders.main;

public class RenderThread implements Runnable {

	private Game game;
	private boolean running;

	public RenderThread(Game game, boolean running) {
		this.game = game;
		this.running = running;
	}

	private void RenderLoop() {
		while (running) {
			game.render();
		}
	}

	public void run() {
		RenderLoop();
	}

}
