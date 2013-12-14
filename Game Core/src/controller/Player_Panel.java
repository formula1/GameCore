package controller;

import java.awt.Dimension;

import javax.swing.JPanel;

import abstracts.Game;

public abstract class Player_Panel extends JPanel{

	public Player_Panel(){
		super();
		setPreferredSize(new Dimension(500,400));

	}

	public abstract void applyPlayers(Player[] players, Game game);

	
	public abstract Player[] getPlayers();
	
	public abstract void end() throws InterruptedException;
	
}
