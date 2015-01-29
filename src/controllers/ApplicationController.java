package controllers;

import java.util.HashMap;

public class ApplicationController {
	private HashMap<Integer, Thread> players;
	
	public ApplicationController(){
		this.players = new HashMap<Integer, Thread>();
	}

	public HashMap<Integer, Thread> getPlayers() {
		return players;
	}
	
	public int addPlayer(Thread player){
		int playerId = this.players.size();
		this.players.put(playerId, player);
		return playerId;
	}
	
	public void startPlayer(int playerId){
		this.players.get(playerId).start();
	}
}
