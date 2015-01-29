package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import audio.play.AudioClipPlayer;

public class PlayActionListener implements ActionListener{
	private AudioClipPlayer player;
	
	public PlayActionListener(AudioClipPlayer player){
		this.player = player;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.player.play();
	}

}
