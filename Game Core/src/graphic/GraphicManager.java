package graphic;




import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;




import abstracts.Game;
import assets.Vec2u;
import assets.WH;

public class GraphicManager {
/*
 * 
 * Takes in as inputs
 * -Physics
 * -Game
 * 
 * 
 * Layers are important for UI on top of objects and backgrounds
 * 
 * 
 * Uses either a preloaded graphic as an instance graphic or a generated one
 * Either way, they need to be treated the same in terms of adding and removing
 * 
 * Binding + offset
 * 
 */
	boolean running;
	public Camera curCam;
	int time = 0;
	Long lastAnim = 0L;

	RendererOut out;
	public Game game;
	
	boolean default_render = false;
	GameRenderer dr;
	
	Graphics2D g;
	public WH graphicWH;
	
	ArrayList<GameGraphic> currentGraphics;
	
	
	
	public GraphicManager(RendererOut out, Game game){
		this.out = out;
		this.game = game;
		graphicWH = new WH(out.getDrawGraphics().getClipBounds().width,out.getDrawGraphics().getClipBounds().height);
		this.curCam = new Camera(new Vec2u(0,0),new Vec2u(1,1),0f);
	}
	
	public GraphicManager(RendererOut out, Game game, GameRenderer dr){
		this.out = out;
		this.game = game;
		default_render = true;
		this.dr = dr;
		graphicWH = out.getGraphicBounds();
		this.curCam = new Camera(new Vec2u(graphicWH.width/2,graphicWH.height/2),new Vec2u(2,2),0f);
	}

	
	public void setCamera(Vec2u scale,Float angle, Vec2u pos){
		curCam = new Camera(pos, scale, angle);
	}
	public void tweenCamera(Vec2u scale,Float angle, Vec2u pos, int time){
		curCam.setTween(scale,angle,pos,time);
	}
	public void tweenCamera(float scale,Float angle, Vec2u pos, int time){
		curCam.setTween(scale,angle,pos,time);
	}
	
	public void cameraAnimation(){
		if(curCam.anim){
			curCam.tween(((float)(System.currentTimeMillis()-lastAnim)/(float)time));
		}	
	}
	
	
	
	public void display(){

		currentGraphics = new ArrayList<GameGraphic>();

		dr.setCamera(this);
		
		if(default_render){
			currentGraphics.addAll(dr.getGraphics(this));
		}
		
		TreeMap<Integer, BufferedImage> buffs = new TreeMap<Integer, BufferedImage>();
		HashMap<Integer, Graphics2D> gs = new HashMap<Integer, Graphics2D>();
		

		Vec2u camerascale = new Vec2u(graphicWH.width/curCam.bounds.x,graphicWH.height/curCam.bounds.y);
		Vec2u scaledpos = new Vec2u(curCam.position.x*-camerascale.x,curCam.position.y*-camerascale.y);
		scaledpos = scaledpos.add(new Vec2u(graphicWH.width/2,graphicWH.height/2));
		
		while(currentGraphics.size() > 0){
			if(buffs.get(currentGraphics.get(0).layer) == null){
				buffs.put(currentGraphics.get(0).layer, new BufferedImage(graphicWH.width, graphicWH.height, BufferedImage.TYPE_4BYTE_ABGR));
				gs.put(currentGraphics.get(0).layer, buffs.get(currentGraphics.get(0).layer).createGraphics());
			}

			if(currentGraphics.get(0).scaled){
				gs.get(currentGraphics.get(0).layer).translate(scaledpos.x, scaledpos.y);
				gs.get(currentGraphics.get(0).layer).scale(camerascale.x,camerascale.y);
				gs.get(currentGraphics.get(0).layer).rotate(curCam.angle);
			}else{
				gs.get(currentGraphics.get(0).layer).translate(0,graphicWH.height/2);
				gs.get(currentGraphics.get(0).layer).scale(1,-1);
				gs.get(currentGraphics.get(0).layer).translate(0,-graphicWH.height/2);
			}

			currentGraphics.get(0).draw(gs.get(currentGraphics.get(0).layer));
			if(currentGraphics.get(0).scaled){
				gs.get(currentGraphics.get(0).layer).rotate(-curCam.angle);
				gs.get(currentGraphics.get(0).layer).scale(1/camerascale.x, 1/camerascale.y);
				gs.get(currentGraphics.get(0).layer).translate(-scaledpos.x, -scaledpos.y);
			}else{
				gs.get(currentGraphics.get(0).layer).translate(0,graphicWH.height/2);
				gs.get(currentGraphics.get(0).layer).scale(1,-1);
				gs.get(currentGraphics.get(0).layer).translate(0,-graphicWH.height/2);
			}

			currentGraphics.remove(0);
		}
		
		for(Map.Entry<Integer, Graphics2D> m : gs.entrySet()){
			m.getValue().dispose();
		}
		gs.clear();

		
		//draw layers to screen
		g = (Graphics2D) out.getDrawGraphics();

		g.setColor(Color.black);
		g.setClip(0, 0, graphicWH.width, graphicWH.height);
		g.fillRect(0,0,graphicWH.width,graphicWH.height);
		
		g.translate(0,graphicWH.height/2);
		g.scale(1,-1);
		g.translate(0,-graphicWH.height/2);

		
		for(Entry<Integer,BufferedImage> m: buffs.entrySet()){
			g.drawImage(m.getValue(), null, 0, 0);
		}
		
		g.dispose();
		out.graphicDoneCallback();
	}	

	
/*	
	public abstract class BodyGraphic extends GameGraphic{
		private Body body;
		public BodyGraphic(Body b, int layer){
			super(layer);
			this.body = b;
		}
		public void draw(Graphics2D g){
			Vec2 pos = new Vec2(
					body.getPosition().x,
					body.getPosition().y
			);
			drawGraphic(g, pos, body.getAngle());
		}
		public abstract void drawGraphic(Graphics2D g, Vec2 position, float angle);

	}
	public abstract class JointGraphic extends GameGraphic{
		private Joint j;
		public JointGraphic(Joint joint, int layer){
			super(layer);
			this.j = joint;
		}
		public void draw(Graphics2D g){
			Vec2 pos = new Vec2(
					j.getBodyA().getPosition().x,
					j.getBodyB().getPosition().y
			);
			drawGraphic(g, pos, j);
		}
		public abstract void drawGraphic(Graphics2D g, Vec2 position, Joint j);

	}
	
	public abstract class FixtureGraphic extends GameGraphic{
		protected Fixture fix;
		public FixtureGraphic(Fixture f, int layer){
			super(layer);
			this.fix = f;
		}
		public void draw(Graphics2D g){
			Vec2 bpos = new Vec2(
					fix.getBody().getPosition().x,
					fix.getBody().getPosition().y
			);
			if(fix.getType() == ShapeType.CIRCLE ){
				CircleShape retype = (CircleShape)fix.getShape();
				bpos.x += retype.m_p.x;
				bpos.y += retype.m_p.y;
			}else if(fix.getType() == ShapeType.POLYGON){
				PolygonShape retype = (PolygonShape)fix.getShape();
				Vec2 avg = new Vec2();
				for(int i=0;i<retype.m_vertices.length;i++){
					avg.x += retype.m_vertices[i].x/retype.m_vertices.length;
					avg.y += retype.m_vertices[i].y/retype.m_vertices.length;
				}
//				bpos.x += avg.x*curCam.scale.y+curCam.position.y;
//				bpos.y += avg.y*curCam.scale.y+curCam.position.y;
			}
			
			drawGraphic(g, bpos, fix.getBody().getAngle());
		}
		public abstract void drawGraphic(Graphics2D g, Vec2 position, float angle);

	}
	public abstract class CollisionGraphic extends GameGraphic{
		private Contact col;
		
		public CollisionGraphic(Contact c, int layer){
			super(layer);
			col = c;
		}
		
		public void draw(Graphics2D g){
			Manifold m = col.getManifold();
			ManifoldPoint[] mp = m.points;
			Vec2 avg = new Vec2();
			for(int i=0;i<mp.length;i++){
				avg.x += mp[i].localPoint.x/mp.length;
				avg.y += mp[i].localPoint.y/mp.length;				
			}
			
			drawCollisionGraphic(g, avg, 
					col.getFixtureA().m_body.getLinearVelocity(), 
					col.getFixtureB().m_body.getLinearVelocity()
			);
		}
		public abstract void drawCollisionGraphic(
				Graphics2D g,
				Vec2 average_location,
				Vec2 Velocities_of_A,
				Vec2 Velocities_of_B
			);
	}

	public abstract class VelocityGraphic extends GameGraphic{
		private Body body;
		public VelocityGraphic(Body b, int layer){
			super(layer);
			body = b;
		}
		public void draw(Graphics2D g){
			drawVelocity(g, body.getLinearVelocity());
		}
		
		public abstract void drawVelocity(Graphics2D g, Vec2 velocities);

	}

	public abstract class UIGraphic extends GameGraphic{
		
		public UIGraphic(int layer){
			super(layer);
			this.scaled = false;
		}
		public void draw(Graphics2D g) {
			drawUI(g, curCam.position, curCam.angle, curCam.scale, game);
		}

		public abstract void drawUI(Graphics2D g, Vec2 cam_position, float camera_rotation, Vec2 camera_scale, Game game);
	}
	
*/

	
}
