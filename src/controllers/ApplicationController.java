package controllers;

import java.util.ArrayList;

import audio.play.AudioClipPlayer;

public class ApplicationController {
	private ArrayList<AudioClipPlayer> playerList;
	
	public ApplicationController(){
		this.playerList = new ArrayList<AudioClipPlayer>();
	}

	public ArrayList<AudioClipPlayer> getPlayerList() {
		return playerList;
	}
	
	public boolean addPlayer(AudioClipPlayer player){
		return this.playerList.add(player);
	}
	public boolean removePlayer(AudioClipPlayer player){
		return this.playerList.remove(player);
	}
}
