package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javaFlacEncoder.FLAC_FileEncoder;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ui.MainPanel;
import ui.SoundApplication;
import audio.play.AudioLinePlayer;
import controllers.ApplicationController;

public class ExportActionListener implements ActionListener{
	private File file;
	private MainPanel app;
	
	public ExportActionListener(File file, MainPanel app){
		this.file = file;
		this.app = app;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("FLAC", "flac");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(app.getRootPane());
	    if(returnVal != JFileChooser.APPROVE_OPTION)
	    	return;
	    
	    FLAC_FileEncoder fe = new FLAC_FileEncoder();
		fe.useThreads(true);
		fe.encode(file, chooser.getSelectedFile());
	}

}
