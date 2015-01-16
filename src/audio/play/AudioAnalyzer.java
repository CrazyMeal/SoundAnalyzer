package audio.play;

import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;

public class AudioAnalyzer {
	public AudioAnalyzer() {

		// Create a global buffer size
		final int EXTERNAL_BUFFER_SIZE = 128000;

		// Get the location of the sound file
		File soundFile = new File("sin300.wav");

		// Load the Audio Input Stream from the file
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(this.getClass()
					.getResource("/res/sqr-10-3Hz.wav"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Get Audio Format information
		AudioFormat audioFormat = audioInputStream.getFormat();

		// Handle opening the line
		SourceDataLine line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Calculate the sample rate
		float sample_rate = audioFormat.getSampleRate();
		System.out.println("sample rate = " + sample_rate);

		// Calculate the length in seconds of the sample
		float T = audioInputStream.getFrameLength()
				/ audioFormat.getFrameRate();
		System.out
				.println("T = " + T + " (length of sampled sound in seconds)");

		// Calculate the number of equidistant points in time
		int n = (int) (T * sample_rate) / 2;
		System.out.println("n = " + n + " (number of equidistant points)");

		// Calculate the time interval at each equidistant point
		float h = (T / n);
		System.out.println("h = " + h
				+ " (length of each time interval in seconds)");
		// Start playing the sound
		line.start();

		// Write the sound to an array of bytes
		int nBytesRead = 0;
		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
		while (nBytesRead != -1) {
			
			try {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				int nBytesWritten = line.write(abData, 0, nBytesRead);
			}

		}
		//Determine the original Endian encoding format
		boolean isBigEndian = audioFormat.isBigEndian();
		// close the line
		line.drain();
		line.close();
		//this array is the value of the signal at time i*h
		int x[] = new int[n];

		//convert each pair of byte values from the byte array to an Endian value
		for (int i = 0; i < n*2; i+=2) {
			int b1 = abData[i];
			int b2 = abData[i + 1];
			if (b1 < 0) b1 += 0x100;
			if (b2 < 0) b2 += 0x100;

			int value;

			//Store the data based on the original Endian encoding format
			if (!isBigEndian) value = (b1 << 8) + b2;
			else value = b1 + (b2 << 8);
			x[i/2] = value;
		}
		ZoomableChart chart = new ZoomableChart();
		ITrace2D trace = new Trace2DSimple();
		chart.addTrace(trace);
		
		//do the DFT for each value of x sub j and store as f sub j
		double f[] = new double[n/2];
		for (int j = 0; j < n/2; j++) {

			double firstSummation = 0;
			double secondSummation = 0;

			for (int k = 0; k < n; k++) {
		     		double twoPInjk = ((2 * Math.PI) / n) * (j * k);
		     		firstSummation +=  x[k] * Math.cos(twoPInjk);
		     		secondSummation += x[k] * Math.sin(twoPInjk);
			}

		        f[j] = Math.abs( Math.sqrt(Math.pow(firstSummation,2) + 
		        Math.pow(secondSummation,2)) );

			double amplitude = 2 * f[j]/n;
			double frequency = j * h / T * sample_rate;
			System.out.println("frequency = "+frequency+", amp = "+amplitude);
			
				trace.addPoint(frequency, amplitude);
		}
		JFrame frame = new JFrame("Amplitude Chart");
		frame.getContentPane().add(chart);
		frame.setSize(700, 300);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		AudioAnalyzer aa = new AudioAnalyzer();
	}
}
