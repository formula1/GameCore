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
	

	/*

	The point of this is to make sure every environment can deliver the necessary foundation
	for the rest of the program to run


	*/
	
	public Loop(Game g, RendererOut ra, GameRenderer gr){
		game = g;
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
		game.ready();
	}


	public void run() {
		Thread[] players = new Thread[p.length];
		for(int i=0;i<p.length;i++){
			p[i].addListener(game);
			players[i] = new Thread(p[i]);
		}
		for(int i=0;i<p.length;i++)
			players[i].start();
		dt.cameraAnimation();
		while(running){
			beginLoopTime = System.nanoTime();

				dt.display();
	
				lastUpdateTime = currentUpdateTime;
				currentUpdateTime = System.nanoTime();
	
				temp = (int) ((currentUpdateTime - lastUpdateTime)/(1000*1000));
				game.time(temp);

			endLoopTime = System.nanoTime();
			deltaLoop = endLoopTime - beginLoopTime;
			if(deltaLoop >= desiredDeltaLoop){
			//Do nothing. We are already late.
				System.out.println("late");
			}else{
				try{
					Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
				}catch(InterruptedException e){
				//Do nothing
					e.printStackTrace();
				}
			}

		}
		for(int i=0;i<p.length;i++)
			p[i].end();
	}

}
