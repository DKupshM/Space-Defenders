package com.kupsh.spacedefenders.main;

public class Command {

	private Game game;
	private Handler handler;
	private SpriteSheet ss;
	private InputThread iT;

	private boolean error = false;

	public Command(Game game, Handler handler, SpriteSheet ss, InputThread iT) {
		this.game = game;
		this.iT = iT;
		this.ss = ss;
		this.handler = handler;
	}

	public void enemy(String... params) {
		int number = Integer.parseInt(params[0]);
		int x = Integer.parseInt(params[1]);
		int y = Integer.parseInt(params[2]);
		boolean newObject = Boolean.parseBoolean(params[3]);
		boolean removeObject = Boolean.parseBoolean(params[4]);
		// System.out.println("[Enemy]: X =" + x + " , Y =" + y + " , Number = "
		// + handlerNumber);
		// System.out.println("Handler Legnth: " + handler.object.size());
		if (newObject == true && removeObject == false) {
			// System.out.println("New Enemy " + handlerNumber);
			handler.addObject(new Enemy(x, y, 0, 0, ID.enemy, game, handler, ss));
			// System.out.println("Handler Legnth: " + handler.object.size());
		} else if (newObject == false) {
			try {
				Entity tempObject = handler.object.get(number);
				if (tempObject.getId() != ID.enemy)
					error();
				tempObject.setX(x);
				tempObject.setY(y);
				if (removeObject == true)
					tempObject.setRemoveObject(true);
			
			} catch (Exception e) {
				error();
				System.out.println(e);
			}
		}
	}

	public void astroid(String... params) {
		int number = Integer.parseInt(params[0]);
		int x = Integer.parseInt(params[1]);
		int y = Integer.parseInt(params[2]);
		boolean newObject = Boolean.parseBoolean(params[3]);
		boolean removeObject = Boolean.parseBoolean(params[4]);
		// System.out.println("[Asteroid]: X =" + x + " , Y =" + y
		// + " , Number = " + handlerNumber);
		// System.out.println("Handler Legnth: " + handler.object.size());
		if (newObject == true) {
			// System.out.println("New Asteroid " + handlerNumber);
			handler.addObject(new Astroid(x, y, 0, 0, ID.astroid, game,
					handler, ss));
			// System.out.println("Handler Legnth: " + handler.object.size());
			if (removeObject == true) {
				Entity tempObject = handler.object.get(number);
				tempObject.setRemoveObject(true);
			}
		} else if (newObject == false) {
			try {
				Entity tempObject = handler.object.get(number);
				if (tempObject.getId() != ID.astroid)
					error();
				tempObject.setX(x);
				tempObject.setY(y);
				if (removeObject == true)
					tempObject.setRemoveObject(true);
			} catch (Exception e) {
				error();
				System.out.println(e);
			}
		}
	}

	public void player(String... params) {
		int number = Integer.parseInt(params[0]);
		int player = Integer.parseInt(params[1]);
		int x = Integer.parseInt(params[2]);
		int y = Integer.parseInt(params[3]);
		boolean newObject = Boolean.parseBoolean(params[4]);
		boolean removeObject = Boolean.parseBoolean(params[5]);
		if (player == game.getServerId()) {
			// System.out.println("[This Player]: X = " + x + " Y = " + y);
			// System.out.println("Handler Legnth: " + handler.object.size());
			if (newObject == true && removeObject == false) {
				// System.out.println("New Player");
				handler.addObject(new Player(x, y, 0, 0, ID.player, game,
						handler, ss));
				if (removeObject == true) {
					Entity tempObject = handler.object.get(number);
					tempObject.setRemoveObject(true);

				} else if (newObject == false) {
					Entity tempObject = handler.object.get(number);
					tempObject.setX(x);
					tempObject.setY(y);
					if (removeObject == true)
						tempObject.setRemoveObject(true);
				}
			}
		} else {
			// System.out.println("[Other Player]: X = " + x + " Y = " + y);
			// System.out.println("Handler Legnth: " +
			// handler.object.size());
			if (newObject == true) {
				// System.out.println("New Player");
				handler.addObject(new Player(x, y, 0, 0, ID.otherPlayer, game,
						handler, ss));
			} else if (newObject == false) {
				Entity tempObject = handler.object.get(number);
				tempObject.setX(x);
				tempObject.setY(y);
				if (removeObject == true)
					tempObject.setRemoveObject(true);
			}
		}

	}

	public void bullet(String... params) {
		int player = Integer.parseInt(params[0]);
		int x = Integer.parseInt(params[1]);
		int y = Integer.parseInt(params[2]);
		// boolean newObject = Boolean.parseBoolean(params[3]);
		// boolean removeObject = Boolean.parseBoolean(params[4]);
		if (player == game.getServerId()) {
			System.out.println("[This Player's Bullet]: X = " + x + "Y = " + y);
			// Handle This Player's Bullet
		} else {
			System.out
					.println("[Other Player's Bullet]: X = " + x + "Y = " + y);
			// Handle Other Player's Bullet
		}
	}

	public void output() {
		String output = "";
		if (game.getState() == State.Game) {
			for (int i = 0; i < handler.object.size(); i++) {
				Entity tempObject = handler.object.get(i);
				if (tempObject.getId() == ID.player) {
					output += "Player , " + tempObject.getVelx() + " , "
							+ tempObject.getVely() + "; " + "Space , "
							+ game.isSpaceActive() + "; ";
				}
			}
		}
		System.out.println(output);
		game.output.println(output);
	}

	private void error() {
		if (error == false) {
			String output = "";
			for (int i = 0; i < handler.object.size(); i++) {
				Entity tempObject = handler.object.get(i);
				output += tempObject.id.toString() + " , " + i + " , "
						+ tempObject.x + " , " + tempObject.y + " , "
						+ tempObject.isNewObject() + " , "
						+ tempObject.isRemoveObject() + "; ";
			}
			System.out.println();
			System.out.println("Error Server Input:");
			System.out.println(iT.getLastInputLine());
			System.out.println(iT.getInputLine());
			System.out.println("Client Input:");
			System.out.println(output);
			System.out.println();
			error = true;
		}
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}