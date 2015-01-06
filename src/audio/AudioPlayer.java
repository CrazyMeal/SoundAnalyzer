package audio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	private ArrayList<AudioInputStream> audioStreamList;
	
	public AudioPlayer() {
		this.audioStreamList = new ArrayList<AudioInputStream>();
	}
	
	public void readStreams(){
		for(AudioInputStream stream : this.audioStreamList){
			this.readAStream(stream);
		}
	}
	
	public byte[] readAStream(AudioInputStream stream){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		int totalFramesRead = 0;
		int bytesPerFrame = stream.getFormat().getFrameSize();
		if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
			bytesPerFrame = 1;
		}
		int numBytes = 1024 * bytesPerFrame;
		byte[] audioBytes = new byte[numBytes];
		try {
			int numBytesRead = 0;
			int numFramesRead = 0;
			while ((numBytesRead = stream.read(audioBytes)) != -1) {
				// Calculate the number of frames actually read.
				numFramesRead = numBytesRead / bytesPerFrame;
				totalFramesRead += numFramesRead;
				outputStream.write(audioBytes);
				// Here, do something useful with the audio data that's
				// now in the audioBytes array...
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		byte[] finalDatas = outputStream.toByteArray();
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return finalDatas;
	}
	
	public void createStreamFromFile(String path){
		try {
			File fileIn = new File(path);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
			this.audioStreamList.add(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}
	public int getStreamQuantity(){
		return this.audioStreamList.size();
	}
	public void addStreamList(AudioInputStream stream){
		this.audioStreamList.add(stream);
	}
	public void deleteStream(AudioInputStream stream){
		try {
			stream.close();
			this.audioStreamList.remove(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void clearStreamList(){
		try{
			for(AudioInputStream stream : this.audioStreamList){
				stream.close();
			}
			this.audioStreamList.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
