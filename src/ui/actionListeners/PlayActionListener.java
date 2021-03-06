package ui.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import audio.play.AudioLinePlayer;
import controllers.ApplicationController;

public class PlayActionListener implements ActionListener{
	private File file;
	private ApplicationController appController;
	
	public PlayActionListener(File file, ApplicationController appController){
		this.file = file;
		this.appController = appController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AudioLinePlayer player = new AudioLinePlayer(file);
		player.setup();
		Thread t = new Thread(player);
		t.start();
		int threadId = this.appController.addPlayer(player);
		this.appController.startPlayer(threadId);
	}

}
