package controller;

import java.util.ArrayList;

public class PlayerManager {
	public ArrayList<Player_Panel> panels;
	public Player[] players;
	public int current = -1;

	public PlayerManager(Player_Panel[] player_panels){
		panels = new ArrayList<Player_Panel>();
		for(Player_Panel p : player_panels)
			panels.add(p);
	}
	
	public Player[] getPlayers(){
		return players;
	}
	
	public Player_Panel getCurrent(){
		return panels.get(current);
	}
	
	private void removeCurrent(){
		panels.get(current).setVisible(false);
		players = panels.get(current).getPlayers();
		for(Player p : players)
			p.clearListeners();
		try {
			panels.get(current).end();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Player_Panel next() throws Exception{
		if(panels.size() < 1 ) throw new Exception("Need Player Panels");
		if(current+1 < panels.size() ){
			if(current > -1){
				removeCurrent();
				panels.get(current+1).applyPlayers(players);
			}
			current++;
			panels.get(current).setVisible(true);
			return panels.get(current);
			
		}else{
			removeCurrent();
			players = panels.get(current).getPlayers();
			return null;
		}
		
	}

}
