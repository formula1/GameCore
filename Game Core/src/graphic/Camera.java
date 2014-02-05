package graphic;


import assets.Vec2u;

public class Camera{
	public Vec2u bounds;
	public float angle;
	public Vec2u position;
	private Camera tweenCamera;
	private int tweenTime;
	public boolean anim=false;
	
	public Camera(Vec2u pos, Vec2u bounds, Float ang){
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
	
	
	public void setTween(Vec2u scale,Float angle, Vec2u pos, int time){
		tweenCamera = new Camera(
				new Vec2u(pos.x - position.x, pos.y - position.y),
				scale.add(this.bounds.mul(-1)),
				angle - this.angle
		);
		this.tweenTime = time;
		anim = true;
	}
	public void setTween(float scale,Float angle, Vec2u pos, int time){
		tweenCamera = new Camera(
				new Vec2u(pos.x - position.x, pos.y - position.y),
				this.bounds.mul(-1).add(new Vec2u(scale,scale)),
				angle - this.angle
		);
		this.tweenTime = time;
		anim = true;
	}
}