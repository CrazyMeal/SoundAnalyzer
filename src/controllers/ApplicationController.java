package controllers;

import java.util.HashMap;
import java.util.Map.Entry;

import audio.play.AudioLinePlayer;

public class ApplicationController {
	private HashMap<Integer, AudioLinePlayer> players;
	
	public ApplicationController(){
		this.players = new HashMap<Integer, AudioLinePlayer>();
	}

	public HashMap<Integer, AudioLinePlayer> getPlayers() {
		return players;
	}
	
	public int addPlayer(AudioLinePlayer player){
		int playerId = this.players.size();
		this.players.put(playerId, player);
		return playerId;
	}
	
	public void startPlayer(int playerId){	
		if(this.getRunningPlayer() != -1){
			this.players.get(this.getRunningPlayer()).stopDataline();
		}
	}
	
	public Integer getRunningPlayer(){
		int running = -1;
		for(Entry<Integer,AudioLinePlayer> t : this.players.entrySet()){
			if(t.getValue().isRunning())
				running = t.getKey();
		}
		System.out.println("Stopping player>" + running);
		return running;
	}
}
