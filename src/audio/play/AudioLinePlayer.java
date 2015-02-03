package audio.play;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioLinePlayer implements Runnable{
	private File fileToPlay;
	private AudioInputStream stream;
	private AudioFormat format; 
	private SourceDataLine dataLine;
	private int BUFFER_SIZE = 4096;
	private boolean runningPlayer;
	
	public AudioLinePlayer(File file){
		this.fileToPlay = file;
		this.runningPlayer = false;
	}
	
	public void setup(){
		
		try {
			this.stream = AudioSystem.getAudioInputStream(this.fileToPlay);
			this.format = this.stream.getFormat();
			 
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, this.format);
			this.dataLine = (SourceDataLine) AudioSystem.getLine(info);
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void play(){
		try {
			this.runningPlayer = true;
			this.dataLine.open(format);
			this.dataLine.start();
			
			byte[] bytesBuffer = new byte[this.BUFFER_SIZE];
			int bytesRead = -1;
			 
			while (((bytesRead = this.stream.read(bytesBuffer)) != -1) && this.runningPlayer) {
			    this.dataLine.write(bytesBuffer, 0, bytesRead);
			}
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Closing player");
			this.dataLine.stop();
			this.dataLine.close();
			try {
				this.stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		this.play();
	}
	public boolean isRunning(){
		return this.dataLine.isActive();
	}
	public void stopDataline(){
		this.runningPlayer = false;
	}
}
