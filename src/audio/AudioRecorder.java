package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder {
	private AudioFormat format;
	private TargetDataLine targetLine;

	public AudioRecorder() {
		this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	}

	public void setup() {
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, this.format);
		try {
			this.targetLine = (TargetDataLine) AudioSystem.getLine(info);
			this.targetLine.open();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void record() {
		System.out.println("Start recording");
		this.targetLine.start();

		Thread t = new Thread() {
			@Override
			public void run() {
				AudioInputStream audioStream = new AudioInputStream(targetLine);
				File audioFile = new File("record.wav");
				try {
					AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE,
							audioFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.targetLine.stop();
		this.targetLine.close();
		System.out.println("Stop recording");
	}
}
