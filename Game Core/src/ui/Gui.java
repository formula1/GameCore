package ui;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import assets.GuiVec4;

public class Gui {
	
	


	

	
	public interface GuiSelectable{
		public abstract void back();
		public abstract void select();
	}
	
	public abstract class GuiAction{
		public abstract void doAction();
	}
	
}
