package audio.play;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClipPlayer {
	private Mixer mixer;
	private Clip clip;
	private AudioInputStream audioStream;
	
	public AudioClipPlayer(){
		Mixer.Info[] infos = AudioSystem.getMixerInfo();
		/*
		for(Mixer.Info info : infos){
			System.out.println(info.getName() + " --- " + info.getDescription());
		}
		*/
		this.mixer = AudioSystem.getMixer(infos[0]);
	}
	
	public void setup(){
		DataLine.Info info = new DataLine.Info(Clip.class, null);
		try {
			this.clip = (Clip) this.mixer.getLine(info);
			URL soundUrl =  this.getClass().getResource("/res/Music.wav");
			this.audioStream = AudioSystem.getAudioInputStream(soundUrl);
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void play(){
		try {
			this.clip.open(this.audioStream);
			this.clip.start();
			do {
				Thread.sleep(10);
			} while(this.clip.isActive());
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
