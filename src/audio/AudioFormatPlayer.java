package audio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioFormatPlayer {
	private AudioFormat audioFormat;
	private AudioInputStream audioInputStream;
	private SourceDataLine sourceDataLine;
	private byte playingBuffer[] = new byte[10000];

	public AudioFormatPlayer() {
		this.playingBuffer = new byte[10000];
	}

	public void setup(byte dataToPlay[], AudioFormat audioFormat) throws LineUnavailableException {
		this.audioFormat = audioFormat;
		InputStream byteArrayInputStream = new ByteArrayInputStream(dataToPlay);
		
		this.audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, dataToPlay.length / audioFormat.getFrameSize());
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
		this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
	}

	public void play() {
		try {
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			int cnt;
			try {
				while ((cnt = audioInputStream.read(this.playingBuffer, 0,
						this.playingBuffer.length)) != -1) {
					if (cnt > 0) {
						this.sourceDataLine.write(this.playingBuffer, 0, cnt);
					}
				}
				sourceDataLine.drain();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				sourceDataLine.close();
			}
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
}
