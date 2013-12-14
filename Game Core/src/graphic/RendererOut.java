package graphic;

import java.awt.Graphics2D;

import assets.WH;

public abstract interface RendererOut {

	
	public abstract void setupUI();
	public abstract WH getGraphicBounds();	
	public abstract Graphics2D getDrawGraphics();	
	public abstract void graphicDoneCallback();

}
