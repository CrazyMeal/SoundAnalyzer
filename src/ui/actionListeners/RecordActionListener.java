package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

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
	private OutputStream out;

	public RecordActionListener(SoundApplication app) {
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(recording){
			rec.stop();
			((JButton)e.getSource()).setText("Record");
			app.addChart(new File(filePath), DatasetUtils.loadFileAndNormalize(filePath));
		}
		else{
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showSaveDialog(app.getRootPane());
			if(returnVal != JFileChooser.APPROVE_OPTION)
		    	return;

			filePath = chooser.getSelectedFile().getPath();
			rec = new AudioRecorder(chooser.getSelectedFile());
			rec.setup();
			out = new ByteArrayOutputStream();
			rec.record(out);
			((JButton)e.getSource()).setText("Stop recording");
		}
		recording = !recording;
	}
}

