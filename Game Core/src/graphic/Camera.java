package graphic;


import org.jbox2d.common.Vec2;

public class Camera{
	public Vec2 bounds;
	public float angle;
	public Vec2 position;
	private Camera tweenCamera;
	private int tweenTime;
	public boolean anim=false;
	
	public Camera(Vec2 pos, Vec2 bounds, Float ang){
		if(bounds != null) this.bounds = bounds;
		if(ang != null) this.angle = ang;
		if(pos != null) this.position = pos;
	}
	
	public void tween(float div){
		this.bounds = this.bounds.add(tweenCamera.bounds.mul(1/div));
		this.angle += tweenCamera.angle/div;
		this.position.x += tweenCamera.position.x/div;
		this.position.y += tweenCamera.position.y/div;
		if(this.equals(tweenCamera)){
			anim=false;
		}
	}
	
	
	public void setTween(Vec2 scale,Float angle, Vec2 pos, int time){
		tweenCamera = new Camera(
				new Vec2(pos.x - position.x, pos.y - position.y),
				scale.sub(this.bounds),
				angle - this.angle
		);
		this.tweenTime = time;
		anim = true;
	}
	public void setTween(float scale,Float angle, Vec2 pos, int time){
		tweenCamera = new Camera(
				new Vec2(pos.x - position.x, pos.y - position.y),
				this.bounds.mul(-1).add(new Vec2(scale,scale)),
				angle - this.angle
		);
		this.tweenTime = time;
		anim = true;
	}
}