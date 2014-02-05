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
	public static HashMap<String, Float>[] players;
	ArrayList<TimeListener> timelisteners;

//	protected Player[] players;
	
	@SuppressWarnings("unchecked")
	
	
	public Game(){
		timelisteners = new ArrayList<TimeListener>();

	}
	
	public abstract GameRenderer getRenderer();
	
	public void setPlayers(Player[] ps){
		Game.players = new HashMap[ps.length];
		for(int i=0;i<Game.players.length;i++){
			Game.players[i] = new HashMap<String,Float>();
			for(String but : Player.butnames){
				Game.players[i].put(but, 0f);
			}
		}
	}
	
	public abstract void worldInit();
	
	public abstract void playerEnter(int playernumber);
	public abstract void playerLeave(int playernumber);
	public abstract void playerAction(int playernumber, String button, float amount);

	
	public void addTimeListener(TimeListener t){
		timelisteners.add(t);
	}

	public void t(long time){
		for(TimeListener l:timelisteners) l.time(time);

		time(time);
	}
	public abstract void time(long time);
	
	//implements Contact Listener

//end inputs
	
//begin outputs
	
	public abstract void deleteAssociated(Object o);	
}//End Game Class
