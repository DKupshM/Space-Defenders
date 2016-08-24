package com.kupsh.spacedefenders.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput extends MouseAdapter implements MouseListener {

	private Game game;

	public MouseInput(Game game) {
		this.game = game;
	}

	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (game.getState() == State.menu) {
			if (mx >= 120 && mx <= 520) {

				// SinglePlayer
				if (my >= 120 && my <= 170) {
					game.setSession(Session.singleplayer);
					game.newGame();
					game.setState(State.Game);
				}

				// MultiPlayer
				else if (my >= 183 && my <= 233) {
					if (game.isServer() == true) {
						game.output.println("StartSearch");
						game.setSession(Session.dualplayer);
						game.setState(State.finding);
					}
				}

				// Options
				else if (my >= 246 && my <= 290) {

				}

				// Quit
				else if (my >= 310 && my <= 360) {
					System.out.println("Quitting");
					System.exit(0);
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
