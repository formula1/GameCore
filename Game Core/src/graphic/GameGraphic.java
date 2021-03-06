package graphic;

import java.awt.Graphics2D;


public abstract class GameGraphic implements Comparable<GameGraphic>{
	public int layer;
	public boolean scaled = true;
	public Object userdata = null;
	public GameGraphic(){
		layer = 0;
	}
	public GameGraphic(int layer){
		this.layer = layer;
	}
	public GameGraphic(int layer, Object userdata){
		this.layer = layer;
		this.userdata = userdata;
	}
	public GameGraphic(int layer, boolean scaled, Object userdata){
		this.layer = layer;
		this.scaled = scaled;
		this.userdata = userdata;
	}
	public GameGraphic(int layer, boolean scaled){
		this.layer = layer;
		this.scaled = scaled;
	}
	
	public abstract void draw(Graphics2D g);
	
	public int compareTo(GameGraphic other){
		int ret = this.layer - other.layer;
		if(ret == 0) ret = this.hashCode() - other.hashCode();
		return ret;
	}

}
