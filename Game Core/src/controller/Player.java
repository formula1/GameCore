package controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class Player implements Runnable{
	
	public int playernumber=0;
	public static final String[] butnames = {
										"up","down","left","right",
										"a", "b", "x", "y",
										"r1", "r2", "l1", "l2",
										"start", "select"
									};
	
	public HashMap<String,Float> buttons;
	protected ArrayList<PlayerListener> listeners;
	protected boolean debug = false;

	public void setDebug(boolean d){
		debug = d;
	}

	private boolean running = true;
	public void run(){
		running=true;
		System.out.println("is runnning...");
		HashMap<String,Float> be;
		while(running){
			if((be = getButtonEvents()).size() > 0)
				for(Map.Entry<String, Float> e : be.entrySet()){
					buttons.put(e.getKey(), e.getValue());
					for(int i=0;i<listeners.size();i++) listeners.get(i).playerEvent(playernumber, e.getKey(), e.getValue());
				}
			
			try {
				Thread.sleep((int)(1000/60));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public abstract HashMap<String,Float> getButtonEvents();
	public abstract void cleanup();
	
	public void end(){
		cleanup();
		running = false;
	}
	
	public void addListener(PlayerListener g){
		listeners.add(g);
//		listeners.get(listeners.size()-1).setInputs(playernumber, buttons);
	}
	
	
	
	public void removeListener(PlayerListener g){
		listeners.remove(g);
	}

	public Player(){
		listeners = new ArrayList<PlayerListener>();
		buttons = new HashMap<String,Float>();
		for(int i=0;i<butnames.length;i++)
			buttons.put(butnames[i], 0f);
 	}

	public Player(PlayerListener bs){
		this();
		listeners.add(bs);
 	}

	public Player(PlayerListener bs, int pn){
		this();
		playernumber = pn;
		listeners.add(bs);
 	}
	
	public Player(int pn){
		this();
		playernumber = pn;
 	}
	
	
	public static int UP = 1;
	public static int RIGHT = 2;
	public static int DOWN = 4;
	public static int LEFT = 8;
	

	
}//End Player Class
