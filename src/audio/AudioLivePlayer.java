package audio;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioLivePlayer {
	public AudioLivePlayer(){
		
		try {
			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
			
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			final SourceDataLine sourceLine = (SourceDataLine)AudioSystem.getLine(info);
			sourceLine.open();
			
			info = new DataLine.Info(TargetDataLine.class, format);
			final TargetDataLine targetLine = (TargetDataLine)AudioSystem.getLine(info);
			targetLine.open();
			
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			Thread source = new Thread(){
				@Override
				public void run(){
					sourceLine.start();
					while(true){
						sourceLine.write(outputStream.toByteArray(), 0, outputStream.size());
					}
				}
			};
			Thread target = new Thread(){
				@Override
				public void run(){
					targetLine.start();
					byte[] data = new byte[targetLine.getBufferSize() / 5];
					int readBytes;
					while(true){
						readBytes = targetLine.read(data, 0, data.length);
						outputStream.write(data, 0, readBytes);
					}
				}
			};
			System.out.println("Start recording");
			target.start();
			Thread.sleep(5000);
			targetLine.stop();
			targetLine.close();
			System.out.println("End recording");
			
			System.out.println("Start playing");
			source.start();
			Thread.sleep(5000);
			sourceLine.stop();
			sourceLine.close();
			System.out.println("End playing");
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
