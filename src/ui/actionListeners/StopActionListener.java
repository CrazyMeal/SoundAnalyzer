package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import audio.play.AudioLinePlayer;
import controllers.ApplicationController;

public class StopActionListener implements ActionListener{
	private File file;
	private ApplicationController appController;
	
	public StopActionListener(ApplicationController appController){
		this.appController = appController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.appController.startPlayer(-1);
	}

}
