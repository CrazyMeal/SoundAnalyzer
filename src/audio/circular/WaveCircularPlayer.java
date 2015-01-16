package audio.circular;

import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class WaveCircularPlayer {
	private File file;
	private byte[] buffer;
	private byte[] byteBuffer;
	private FileInputStream fis;
	private AudioInputStream ais;
	private ByteArrayInputStream bais;
	private AudioFormat format;

	public WaveCircularPlayer(File file) {
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
			this.byteBuffer = new byte[4096];

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public void analyze() {
		System.out.println("Frame length of audioInputStream> "
				+ this.ais.getFrameLength());
		System.out.println("Frame size from file format> "
				+ this.format.getFrameSize());

		// lire le header wav
		try {
			int headerBytes = this.ais.read(this.byteBuffer, 0, 12);
			System.out.println(headerBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private long getLE(byte[] buffer, int pos, int numBytes) {
		numBytes--;
		pos += numBytes;

		long val = buffer[pos] & 0xFF;
		for (int b = 0; b < numBytes; b++)
			val = (val << 8) + (buffer[--pos] & 0xFF);

		return val;
	}

	public void close() {
		try {
			this.ais.close();
			this.bais.close();
			this.fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ZoomableChart chart = new ZoomableChart();
		ITrace2D trace = new Trace2DSimple();
		chart.addTrace(trace);
		JFrame frame = new JFrame("Amplitude Chart");
		frame.getContentPane().add(chart);
		frame.setSize(700, 300);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
		try {

			WavFile wavFile = WavFile.openWavFile(new File("res/sqr-10-3Hz.wav"));
			wavFile.display();
			// Get the number of audio channels in the wav file
			int numChannels = wavFile.getNumChannels();

			// Create a buffer of 100 frames
			double[] buffer = new double[100 * numChannels];

			int framesRead;
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;
			int i = 0;
			do {
				
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, 100);
				// Loop through frames and look for minimum and maximum value
				for (int s = 0; s < framesRead * numChannels; s++) {
					if (buffer[s] > max)
						max = buffer[s];
					if (buffer[s] < min)
						min = buffer[s];
					if((i % 10) == 0)
						trace.addPoint(i, buffer[s]);
					i++;
				}
			} while (framesRead != 0);

			// Close the wavFile
			wavFile.close();
			//frame.setVisible(true);
			// Output the minimum and maximum value
			System.out.printf("Min: %f, Max: %f\n", min, max);
		} catch (IOException | WavFileException e) {
			e.printStackTrace();
		}
		/*
		 * WaveCircularPlayer p = new WaveCircularPlayer(new
		 * File("res/Music.wav")); p.setup(); p.analyze(); p.close();
		 */
	}
}