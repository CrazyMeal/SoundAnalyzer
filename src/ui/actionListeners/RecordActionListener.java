package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRootPane;

import ui.SoundApplication;
import audio.play.AudioLinePlayer;
import audio.record.AudioRecorder;
import controllers.ApplicationController;
import dataset.DatasetUtils;

public class RecordActionListener implements ActionListener{

	private SoundApplication app;
	private boolean recording = false;
	private AudioRecorder rec;
	private String filePath;

	public RecordActionListener(SoundApplication app) {
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(recording){
			rec.stop();
			((JButton)e.getSource()).setText("Record");
			app.addChart(new File(filePath), DatasetUtils.loadFile(filePath));
		}
		else{
			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(app.getRootPane());
			filePath = chooser.getSelectedFile().getPath();
			rec = new AudioRecorder(chooser.getSelectedFile());
			rec.setup();
			rec.record();
			((JButton)e.getSource()).setText("Stop recording");
		}
		recording = !recording;
	}
}
