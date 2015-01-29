package main;
import java.awt.Dimension;
import java.io.File;

import org.jfree.ui.RefineryUtilities;

import ui.SoundApplication;
import dataset.DatasetUtils;


public class Main {

	public static void main(String[] args) {
		
    	SoundApplication demo = new SoundApplication("Plateforme Java - SoundAnalyzer");
        demo.setPreferredSize(new Dimension(1000, 600));
        
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        demo.addChart(new File("res/sound.wav"), DatasetUtils.loadFile("res/sound.wav"));
    }
}
