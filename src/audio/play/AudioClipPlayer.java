package audio.play;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClipPlayer {
	private Mixer mixer;
	private Clip clip;
	
	public AudioClipPlayer(){
		Mixer.Info[] infos = AudioSystem.getMixerInfo();
		/*
		for(Mixer.Info info : infos){
			System.out.println(info.getName() + " --- " + info.getDescription());
		}
		*/
		this.mixer = AudioSystem.getMixer(infos[0]);
		
		DataLine.Info info = new DataLine.Info(Clip.class, null);
		try {
			this.clip = (Clip) this.mixer.getLine(info);
			URL soundUrl =  this.getClass().getResource("/res/Music.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
			this.clip.open(audioStream);
			this.clip.start();
			
			do {
				Thread.sleep(10);
			} while(this.clip.isActive());
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
