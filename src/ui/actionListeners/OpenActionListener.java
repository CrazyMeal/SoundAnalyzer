package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.kc7bfi.jflac.apps.Decoder;

import ui.SoundApplication;
import dataset.DatasetUtils;

public class OpenActionListener implements ActionListener {
	
	private SoundApplication app;

	public OpenActionListener(SoundApplication app) {
		this.app = app;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV & FLAC Sounds", "wav", "flac");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(app.getRootPane());
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			if(chooser.getSelectedFile().getName().endsWith("flac")){
				Decoder dec = new Decoder();
				try {
					dec.decode(chooser.getSelectedFile().getPath(), chooser.getSelectedFile().getPath().replace(".flac", ".wav"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				app.addChart(chooser.getSelectedFile(),DatasetUtils.loadFileAndNormalize(chooser.getSelectedFile().getPath().replace(".flac", ".wav")));
			}
			else if(chooser.getSelectedFile().getName().endsWith("wav")){
				app.addChart(chooser.getSelectedFile(),DatasetUtils.loadFileAndNormalize(chooser.getSelectedFile()));
			}
		}


	}

}
