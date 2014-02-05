package sound;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SFXPlayback {

	private HashMap<String,Clip> loops;
	public HashMap<String,AudioInputStream> refs;
	
	public void loadSounds(String path){
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		for(int i=0;i<listOfFiles.length;i++){
			if(listOfFiles[i].isFile()){
				files = listOfFiles[i].getName();
				if(files.endsWith(".wav")){
					try {
						refs.put(files.substring(0,-4), 
							AudioSystem.getAudioInputStream(
								SFXPlayback.class.getResourceAsStream(path+"/"+files)
							)
						);
					} catch (UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//this is used for 
	public String loopsound(String url){
		int check=0;
		while(loops.containsKey(url+check)) check++;
		try{
			Clip clip = AudioSystem.getClip();
			if(!refs.containsKey(url)) throw new Error("sound nonexsisting");
			clip.open(refs.get(url));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
			e.printStackTrace();
			throw new Error(e);
		}
		return url+check;
	}
	
	public void stopLoop(String loopref){
		if(loops.containsKey(loopref)){
			loops.get(loopref).stop();
			loops.remove(loopref);
		}
		else throw new Error("loop ref nonexsistant");
	}
	
	public synchronized void playSound(final String url){
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(refs.get(url));
			clip.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
