package com.kupsh.spacedefenders.main;

import java.awt.Color;
import java.awt.Graphics;

public class FindingScreen {

	public FindingScreen(Game game) {
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Finding Match", 100, 100);
		g.drawRect(100, 100, 100, 100);
	}
}
