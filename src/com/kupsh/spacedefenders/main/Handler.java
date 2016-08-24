package com.kupsh.spacedefenders.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	@SuppressWarnings("unused")
	private Game game;

	public Handler(Game game) {
		this.game = game;
	}

	LinkedList<Entity> object = new LinkedList<Entity>();

	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			Entity tempObject = object.get(i);
			tempObject.tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			Entity tempObject = object.get(i);
			try {
				tempObject.render(g);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addObject(Entity object) {
		this.object.add(object);
	}

	public void removeObject(Entity object) {
		this.object.remove(object);
	}
}
