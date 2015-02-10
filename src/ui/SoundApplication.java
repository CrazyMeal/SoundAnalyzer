package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;

import ui.actionListeners.RecordActionListener;
import audio.record.AudioRecorder;
import controllers.ApplicationController;
import dataset.DatasetUtils;

public class SoundApplication extends ApplicationFrame {
	private static final long serialVersionUID = -4667093160682321127L;
	private MainPanel chartingPanel;
	private ApplicationController appController;
	
    public SoundApplication(String title) {
        super(title);
        this.appController = new ApplicationController();
        
        JPanel container = new JPanel(new BorderLayout());
        container.add(this.createMenuPanel(), BorderLayout.PAGE_START);
        container.add(this.createScrollPanel());
        
        this.setContentPane(container);
    }
    
    
    
    public JScrollPane createScrollPanel(){
    	this.chartingPanel = new MainPanel(this.appController);
    	
    	JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(this.chartingPanel);
        scroll.setSize(new Dimension(600, 300));
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        return scroll;
    }
    
    public JPanel createMenuPanel(){
    	JPanel menuPanel = new JPanel(new FlowLayout());
        
        JButton buttonOpen = new JButton();
        buttonOpen.setText("Open");
        buttonOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV & FLAC Sounds", "wav", "flac");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(getRootPane());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       addChart(chooser.getSelectedFile(),DatasetUtils.loadFileAndNormalize(chooser.getSelectedFile()));
			       System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			    }
			}
		});
        
        JButton buttonRecord = new JButton();
        buttonRecord.setText("Record");
        buttonRecord.addActionListener(new RecordActionListener(this));
        
        menuPanel.add(buttonOpen);
        menuPanel.add(buttonRecord);
        
        return menuPanel;
    }
    
    public void addChart(File file, DefaultXYDataset dataset){
    	this.chartingPanel.addChart(file, dataset);
    	this.chartingPanel.revalidate();
    }
}
