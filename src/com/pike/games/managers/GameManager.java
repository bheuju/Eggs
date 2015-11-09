package com.pike.games.managers;

//Singleton design pattern

public class GameManager {
	
	private static GameManager pInstance = new GameManager();
	
	private GameManager() {
		
	}
	
	public static GameManager getInstance() {
		if (pInstance == null) {
			pInstance = new GameManager();
		}
		return pInstance;
	}
}
