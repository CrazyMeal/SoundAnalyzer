package main;
import java.awt.Dimension;
import org.jfree.ui.RefineryUtilities;

import ui.SoundApplication;


public class Main {

	public static void main(String[] args) {
		
    	SoundApplication demo = new SoundApplication("Plateforme Java - SoundAnalyzer");
        demo.setPreferredSize(new Dimension(1000, 600));
        
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
}
