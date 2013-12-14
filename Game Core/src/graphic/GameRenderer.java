package graphic;

import java.util.Collection;

import physics.PhysicsManager;




import assets.SinCosTable;

public abstract class GameRenderer {
	protected SinCosTable sct;

	public GameRenderer(){
		sct = new SinCosTable(13);
	}
	
	public abstract Collection<GameGraphic> getGraphics(GraphicManager gr);
	
	public abstract void setCamera(GraphicManager gr);
}
