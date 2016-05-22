package com.rlvj.vanhackathon.axiomzen.game;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameControl {

	//TODO: Need to change to a database
	private Map<String, GameBean> gameControl = new LinkedHashMap<String, GameBean>();
	
	public GameBean newGame(String gameKey, String user) {
		GameBean gameBean = new GameBean(user);
		gameControl.put(gameBean.getGameKey(), gameBean);
		
		return gameBean;
	}
	
	public GameBean guess(String gameKey, String code) {
		GameBean bean = gameControl.get(gameKey);
		if (bean == null) {
			throw new RuntimeException("Invalid gameKey");
		}
		
		if (bean.isValid()) {
			bean.checkGuess(code);
		}
		
		return bean;
	}
}
