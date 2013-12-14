package ui;

import org.jbox2d.common.Vec2;

import assets.GuiVec4;

public abstract class GuiContain extends GuiItem{
	public float scrollspeed;
	public boolean overflow_hidden;
	public Vec2 camera_pos;
	public Vec2 total_wh = new Vec2(-1,-1);
	public GuiVec4 padding;
	
	public abstract void populate();
	
}