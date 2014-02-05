package controller;

import java.util.HashMap;

public interface PlayerListener {

	public void playerEvent(int player_number, String input, Float value);
	public abstract void playerEnter(int playernumber);
	public abstract void playerLeave(int playernumber);
	
	
}
