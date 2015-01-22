package ui;

import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import dataset.DatasetUtils;

public class Main extends ApplicationFrame {

    MainPanel mainPanel;
   
    public Main(String title) {
        super(title);
        mainPanel = new MainPanel();
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(mainPanel);
        scroll.setSize(new Dimension(600, 300));
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(scroll);
    }

    public static void main(String[] args) {

    	Main demo = new Main("Translate Demo 1");

        demo.setPreferredSize(new Dimension(600, 540));
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        demo.addChart(DatasetUtils.loadFile("res/Music.wav"));
        demo.addChart(DatasetUtils.loadFileAndNormalize("res/Music.wav"));

    }
    
    public void addChart(DefaultXYDataset dataset){
    	mainPanel.addChart(dataset);
    	mainPanel.revalidate();
    }
    
    
}