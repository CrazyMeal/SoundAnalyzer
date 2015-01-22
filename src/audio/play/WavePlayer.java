package audio.play;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WavePlayer {
	private File file;
	private byte[] buffer;
	private byte[] byteBuffer;
	private FileInputStream fis;
	private AudioInputStream ais;
	private ByteArrayInputStream bais;
	private AudioFormat format;
	private double duration;
	private float frameRate;
	private int frameSize;
	private int bytesPerSeconds;
	
	public WavePlayer(File file) {
		this.file = file;
	}

	public void setup() {
		try {
			this.fis = new FileInputStream(this.file);
			this.buffer = new byte[(int) this.file.length()];
			this.fis.read(this.buffer);

			this.bais = new ByteArrayInputStream(this.buffer);

			this.ais = AudioSystem.getAudioInputStream(this.bais);
			this.format = this.ais.getFormat();
			this.frameRate = this.format.getFrameRate();
			this.frameSize = this.format.getFrameSize();
			this.bytesPerSeconds = (int) (this.frameSize * this.frameRate);
			
			this.byteBuffer = new byte[(int) (this.ais.getFrameLength() * this.format.getFrameSize())];
			
			double durationMsec = (double) ((this.ais.getFrameLength() * 1000) / this.ais.getFormat().getFrameRate());
			this.duration = (double) (durationMsec / 1000.0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public int[] analyze() {
		int[] audioData = null;
		try {
			int readBytes = 0;
			while((readBytes = this.ais.read(this.byteBuffer)) != -1){
			
				if (format.getSampleSizeInBits() == 16) {
					int nlengthInSamples = this.byteBuffer.length / 2;
					System.out.println("taille calculée> " + nlengthInSamples + "|| taille buffer> " + this.byteBuffer.length);
					audioData = new int[nlengthInSamples];
					if (format.isBigEndian()) {
						for (int i = 0; i < nlengthInSamples; i++) {
							/* First byte is MSB (high order) */
							int MSB = this.byteBuffer[2 * i];
							/* Second byte is LSB (low order) */
							int LSB = this.byteBuffer[2 * i + 1];
							audioData[i] = MSB << 8 | (255 & LSB);
						}
					} else {
						for (int i = 0; i < nlengthInSamples; i++) {
							/* First byte is LSB (low order) */
							int LSB = this.byteBuffer[2 * i];
							/* Second byte is MSB (high order) */
							int MSB = this.byteBuffer[2 * i + 1];
							audioData[i] = MSB << 8 | (255 & LSB);
						}
					}
				} else if (format.getSampleSizeInBits() == 8) {
					int nlengthInSamples = this.byteBuffer.length;
					audioData = new int[nlengthInSamples];
					if (format.getEncoding().toString().startsWith("PCM_SIGN")) {
						// PCM_SIGNED
						for (int i = 0; i < this.byteBuffer.length; i++) {
							audioData[i] = this.byteBuffer[i];
						}
					} else {
						// PCM_UNSIGNED
						for (int i = 0; i < this.byteBuffer.length; i++) {
							audioData[i] = this.byteBuffer[i] - 128;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return audioData;
	}
	public void close(){
		try {
			this.ais.close();
			this.bais.close();
			this.fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getBytesPerSeconds(){
		return this.bytesPerSeconds;
	}
	public int getFrameSize(){
		return this.frameSize;
	}
	public double getFrameRate(){
		return this.frameRate;
	}
	public double getDuration(){
		return this.duration;
	}
}