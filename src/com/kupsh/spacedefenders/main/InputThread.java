package com.kupsh.spacedefenders.main;

import java.io.BufferedReader;
import java.lang.reflect.Method;

public class InputThread implements Runnable {

	private Game game;
	private Command c;
	private BufferedReader input;
	private Handler handler;

	private String inputLine;
	private String lastInputLine;

	private boolean running = true;

	public InputThread(Game game, BufferedReader input, Handler handler,
			SpriteSheet ss) {
		this.game = game;
		this.handler = handler;
		this.input = input;
		c = new Command(game, handler, ss, this);
	}

	private void inputLoop() throws Exception {
		while (running) {
			inputLine = input.readLine();
			// System.out.println(inputLine);
			if (inputLine == null) {
				game.setServerId(0);
				game.setServer(false);
				running = false;
			} else if (inputLine.equalsIgnoreCase("Starting")) {
				game.setSession(Session.dualplayer);
				System.out.println("Starting Game");
				game.setState(State.Game);
				game.output.println("Start , " + game.getServerId());
			} else {
				String[] object = inputLine.split("; ");
				c.setError(false);
				for (int i = 0; i <= object.length - 1; i++) {
					String[] words = object[i].split(" , ");
					String[] params = new String[words.length - 1];
					for (int i1 = 1; i1 <= words.length - 1; i1++) {
						params[i1 - 1] = words[i1];
					}
					Class<Command> a = Command.class;
					Method method = a.getMethod(words[0], params.getClass());
					method.invoke(c, (Object) params);
				}
				c.output();
				lastInputLine = inputLine;
				// System.out.println("Done with Set of info: " +
				// lastInputLine);

				// System.out.println("Finished Set of Data");
			}
		}
	}

	public void run() {
		try {
			inputLoop();
		} catch (Exception e) {
			System.out.println(lastInputLine);
			System.out.println(inputLine);
			System.out.println("Handler Legnth: " + handler.object.size());
			e.printStackTrace();
		}
	}

	public void stop() {
		running = false;
	}

	public String getInputLine() {
		return inputLine;
	}

	public void setInputLine(String inputLine) {
		this.inputLine = inputLine;
	}

	public String getLastInputLine() {
		return lastInputLine;
	}

	public void setLastInputLine(String lastInputLine) {
		this.lastInputLine = lastInputLine;
	}

}
