package com.kupsh.spacedefenders.main;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ComponentInput implements ComponentListener {
	public void componentResized(ComponentEvent e) {
		Game.Height = e.getComponent().getHeight() - 22;
		Game.Width = e.getComponent().getWidth();
		System.out.println("New Demensions are: " + Game.Height + " , "
				+ Game.Width);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
}
