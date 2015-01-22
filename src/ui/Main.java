package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import dataset.DatasetUtils;

public class Main extends ApplicationFrame {

    MainPanel chartingPanel;
   
    public Main(String title) {
        super(title);
        this.chartingPanel = new MainPanel();
        
        
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(chartingPanel);
        scroll.setSize(new Dimension(600, 300));
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        JPanel container = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton buttonOpen = new JButton();
        buttonOpen.setText("Open");
        JButton buttonRecord = new JButton();
        buttonRecord.setText("Record");
        
        buttonPanel.add(buttonOpen);
        buttonPanel.add(buttonRecord);
        
        container.add(buttonPanel, BorderLayout.PAGE_START);
        container.add(scroll);
        
        setContentPane(container);
    }
    
    public void addChart(DefaultXYDataset dataset){
    	this.chartingPanel.addChart(dataset);
    	this.chartingPanel.revalidate();
    }
    
    public static void main(String[] args) {

    	Main demo = new Main("Translate Demo 1");
        demo.setPreferredSize(new Dimension(600, 540));
        
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        demo.addChart(DatasetUtils.loadFile("res/sound.wav"));
        //demo.addChart(DatasetUtils.loadFileAndNormalize("res/Music.wav"));

    }
    
    
}