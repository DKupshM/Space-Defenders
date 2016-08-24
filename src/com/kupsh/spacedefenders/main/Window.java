package com.kupsh.spacedefenders.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	private JFrame frame;

	public void window(String Title, Game game) {
		game.setPreferredSize(new Dimension(640, 480));
		game.setMinimumSize(new Dimension(640, 480));
		frame = new JFrame(Title);
		frame.setSize(640, 480 + 19);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(game, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
	}
}
