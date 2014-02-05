package ui;


import assets.GuiVec4;
import assets.Vec2u;

public abstract class GuiItem{
	public GuiVec4 margins;
	public Vec2u offset;
	public Vec2u drawing_offset; //this is used for drawing purposes, don't change this
	public Vec2u width_height;
	
	
	public GuiItem(){
		margins = new GuiVec4();
		offset = new Vec2u();
	}
	
	public abstract Vec2u width_height();
	
}
