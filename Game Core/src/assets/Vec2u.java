package assets;

public class Vec2u {
	public float x;
	public float y;
	
	public Vec2u(){
		x=0;y=0;
	}
	
	public Vec2u(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vec2u mul(float num){
		this.x *= num;
		this.y *= num;
		return this;
	}
	public Vec2u add(float num){
		this.x += num;
		this.y += num;
		return this;
	}
	public Vec2u add(Vec2u vec){
		this.x += vec.x;
		this.y += vec.y;
		return this;
	}
	public Vec2u mul(Vec2u vec){
		this.x *= vec.x;
		this.y *= vec.y;
		return this;
	}

	public float length(){
		return (float)Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
	}
}
