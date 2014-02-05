package ui;

import java.util.ArrayList;

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
