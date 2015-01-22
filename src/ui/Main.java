package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        buttonOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			    		"WAV & FLAC Sounds", "wav", "flac");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(getRootPane());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       addChart(DatasetUtils.loadFile(chooser.getSelectedFile()));
			    	System.out.println("You chose to open this file: " +
			            chooser.getSelectedFile().getName());
			    }
			}
		});
        
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
        demo.setPreferredSize(new Dimension(700, 540));
        
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        demo.addChart(DatasetUtils.loadFile("res/sound.wav"));
        //demo.addChart(DatasetUtils.loadFileAndNormalize("res/Music.wav"));

    }
    
    
}