package com.rlvj.vanhackathon.axiomzen;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rlvj.vanhackathon.axiomzen.game.GameBean;
import com.rlvj.vanhackathon.axiomzen.game.GameControl;

@RestController
@RequestMapping(path = "/mastermind")
public class MastermindController {
	
	GameControl instance = new GameControl();

	@RequestMapping(path = "/")
	public String welcome() {
		return "Be Welcome! Resources available [/new_game, /guess]";
	}
	
//	@RequestMapping(path = "/new_game", method = RequestMethod.POST)
//	public ResponseEntity<GameBean> newGame(@RequestParam String user) {
//		GameBean gameBean = instance.newGame("", user);
//		
//		return ResponseEntity.ok().body(gameBean);
//	}
	
	@RequestMapping(path = "/new_game", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> newGame(@RequestParam String user) {
		GameBean gameBean = instance.newGame("", user);
		
		return ResponseEntity.ok().body(convertNewGame(gameBean));
	}
	
//	@RequestMapping(path = "/guess", method = RequestMethod.POST)
//	public ResponseEntity<GameBean> guess(@RequestParam String gameKey, @RequestParam String code) {
//		GameBean bean = instance.guess(gameKey, code);
//		
//		return ResponseEntity.ok().body(bean);
//	}
	
	@RequestMapping(path = "/guess", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> guess(@RequestParam String gameKey, @RequestParam String code) {
		GameBean bean = instance.guess(gameKey, code);
		
		return ResponseEntity.ok().body(convertGuess(bean));
	}
	
	private Map<String, Object> convertNewGame(GameBean gameBean) {
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
		map.put("colors", gameBean.getColors());
		map.put("colorLength", gameBean.getColorLength());
		map.put("gameKey", gameBean.getGameKey());
		map.put("numGuesses", gameBean.getNumGuesses());
		map.put("pastResults", gameBean.getPastResults());
		map.put("solved", gameBean.isSolved());
		return map;
	}
	
	private Map<String, Object> convertGuess(GameBean gameBean) {
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
		map.put("colors", gameBean.getColors());
		map.put("colorLength", gameBean.getColorLength());
		map.put("gameKey", gameBean.getGameKey());
		map.put("numGuesses", gameBean.getNumGuesses());
		map.put("pastResults", gameBean.getPastResults());
		map.put("solved", gameBean.isSolved());
		map.put("result", gameBean.getResult());
		if (!gameBean.isValid()) {
			map.put("message", "GAME HAS EXPIRED!!!");
		} else if (gameBean.isSolved()) {
			map.put("result", gameBean.getUser() + " win");
			map.put("timeTaken", gameBean.getTimeTaken());
		}
		
		return map;
	}
}
