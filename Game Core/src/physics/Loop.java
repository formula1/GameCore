package physics;

import graphic.GameRenderer;
import graphic.GraphicManager;
import graphic.RendererOut;




import controller.Player;



import abstracts.Game;

public class Loop implements Runnable{
	GraphicManager dt;
	Player[] p;
	Game game;
	PhysicsManager pm;
	

	/*

	The point of this is to make sure every environment can deliver the necessary foundation
	for the rest of the program to run


	*/
	
	public Loop(Game g, RendererOut ra, GameRenderer gr){
		pm = new PhysicsManager();
		game = g;
		game.setWorld(pm);
		pm.setContactListener(game);
		dt = new GraphicManager(ra,game,gr);
	}

	public Loop(PhysicsManager pl, Game g, RendererOut ra, GameRenderer gr){
		pm = pl;
		game = g;
		pm.setContactListener(game);
		dt = new GraphicManager(ra,game,gr);
	}	
		
	long beginLoopTime;
	long endLoopTime;
	long currentUpdateTime = System.nanoTime();
	long lastUpdateTime;
	long deltaLoop;
	public boolean running = true;
	long temp;
	long desiredFPS = 60;
	long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
	
	
	public void setPlayers(Player[] ps){
		p = ps;
		game.setPlayers(ps);
	}


	public void run() {
		Thread[] players = new Thread[p.length];
		for(int i=0;i<p.length;i++){
			p[i].addListener(game);
			players[i] = new Thread(p[i]);
		}
		for(int i=0;i<p.length;i++)
			players[i].start();
		game.worldInit();
		dt.cameraAnimation();
		while(running){
			beginLoopTime = System.nanoTime();
			dt.display();
			lastUpdateTime = currentUpdateTime;
			currentUpdateTime = System.nanoTime();
			temp = (int) ((currentUpdateTime - lastUpdateTime)/(1000*1000));
			pm.step(((float)temp/1000),pm.velIterations,pm.posIterations);
			game.t(temp);
			endLoopTime = System.nanoTime();
			deltaLoop = endLoopTime - beginLoopTime;
			
			if(deltaLoop >= desiredDeltaLoop){
			//Do nothing. We are already late.
			}else{
				try{
					Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
				}catch(InterruptedException e){
				//Do nothing
					e.printStackTrace();
				}
			}

		}
	}

}
