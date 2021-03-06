package abstracts;




import graphic.GameRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import physics.TimeListener;


import controller.Player;
import controller.PlayerListener;

abstract public class Game implements PlayerListener{
/*
 * The game is the base for everything
 * The game isn't a thread but rather a communication engine
 * -If a player does something, then it will tell the game to do something
 * -If time is = x, then it will do something
 * -if a collision happens, then it will do something
 * 
 * the game communicates with three outputs
 * -The Physics Engine
 * -The Graphics
 * -The Sound
 * 
 * the game takes 3 inputs
 * -Players (technically this may be unlimited)
 * -Time change
 * -Collision detection
 * 
 * Again, a game doesn't have threads, but rather its ment to be a communication interface
 * That reads inputs and sets outputs
 * 
 */
	public static Player[] players;
	ArrayList<TimeListener> timelisteners;

//	protected Player[] players;
	
	@SuppressWarnings("unchecked")
	
	
	public Game(){
		timelisteners = new ArrayList<TimeListener>();
	}
	
	
	public abstract GameRenderer getRenderer();
	public abstract void ready();
	
	public void setPlayers(Player[] ps){
		Game.players = ps;
	}

	public abstract void time(long time);
	
	public abstract void deleteAssociated(Object o);	
}//End Game Class
