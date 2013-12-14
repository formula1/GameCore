package ui;

import org.jbox2d.common.Vec2;

import assets.GuiVec4;

public abstract class GuiItem{
	public GuiVec4 margins;
	public Vec2 offset;
	public Vec2 drawing_offset; //this is used for drawing purposes, don't change this
	public Vec2 width_height;
	
	
	public GuiItem(){
		margins = new GuiVec4();
		offset = new Vec2();
	}
	
	public abstract Vec2 width_height();
	
}
