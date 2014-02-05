package ui;


import assets.GuiVec4;
import assets.Vec2u;

public abstract class GuiContain extends GuiItem{
	public float scrollspeed;
	public boolean overflow_hidden;
	public Vec2u camera_pos;
	public Vec2u total_wh = new Vec2u(-1,-1);
	public GuiVec4 padding;
	
	public abstract void populate();
	
}