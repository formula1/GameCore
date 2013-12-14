package physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import abstracts.Game;

public class PhysicsManager extends World {

	/*
	 * The point of this class is to be the world
	 * Let the "game" simply be an interface that listens for commands and relays them
	 * not the actual thread
	 * 
	 * The actual threads are here, graphics, sound, inputs
	 * if the game wants to do mini threads, sure. But thats on its poragative
	 * 
	 */
		
		public int velIterations=6;
		public int posIterations=2;
		
		public PhysicsManager() {
			super(new Vec2(0,0));
			// TODO Auto-generated constructor stub
		}
		
		public void setContactListener(Game g){
			super.setContactListener(g);
		}

		
		public void empty(){
			Body temp = this.getBodyList();
			do{
				this.destroyBody(temp);
			}while(temp.getNext() != null);
		}
	
}
