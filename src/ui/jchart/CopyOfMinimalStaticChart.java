package ui.jchart;

import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javaFlacEncoder.FLAC_FileEncoder;

import javax.swing.JFrame;

import audio.analysis.AmplitudeDatas;
import audio.play.WavePlayer;

public class CopyOfMinimalStaticChart {

	public static void main(String[] args) {
		File file = new File("res/Music.wav");
		File outputFile = new File("res/sqr-10-3Hz.flac");
		
		WavePlayer player = new WavePlayer(file);
		player.setup();
		
		ZoomableChart chart = new ZoomableChart();
		ITrace2D trace = new Trace2DSimple();
		chart.addTrace(trace);
		JFrame frame = new JFrame("Amplitude Chart");
		frame.getContentPane().add(chart);
		frame.setSize(700, 300);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		frame.setVisible(true);
		
		AmplitudeDatas datas = new AmplitudeDatas(player.analyze(), player.getDuration());
		player.close();
		
		/*
		FLAC_FileEncoder fe = new FLAC_FileEncoder();
		fe.useThreads(true);
		fe.encode(file, outputFile);
		*/
		System.out.println("frameRate> " + player.getFrameRate());
		System.out.println("frameSize> " + player.getFrameSize());
		System.out.println("bytesPerSeconds> " + player.getBytesPerSeconds());
		System.out.println("min> " + datas.getMin() + " max> " + datas.getMax());
		System.out.println("duration> " + datas.getMinutes() + "min " + datas.getSeconds() + "s");

		int j = 0;
		for(double i : datas.getNormalizedDatas()){
			trace.addPoint(j,i);
			j++;
		}	
	}

}
