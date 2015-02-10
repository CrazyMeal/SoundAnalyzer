package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRootPane;

import audio.play.AudioLinePlayer;
import audio.record.AudioRecorder;
import controllers.ApplicationController;

public class RecordActionListener implements ActionListener{

	private JRootPane rootPane;
	private boolean recording = false;
	private AudioRecorder rec;

	public RecordActionListener(JRootPane rootPane) {
		this.rootPane = rootPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(recording){
			rec.stop();
			((JButton)e.getSource()).setText("Record");
		}
		else{
			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(rootPane);
			rec = new AudioRecorder(chooser.getSelectedFile());
			rec.setup();
			rec.record();
			((JButton)e.getSource()).setText("Stop recording");
		}
		recording = !recording;
	}
}

