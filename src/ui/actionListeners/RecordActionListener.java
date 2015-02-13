package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import ui.SoundApplication;
import audio.record.AudioRecorder;
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
			rec.record();
			((JButton)e.getSource()).setText("Stop recording");
		}
		recording = !recording;
	}
}

