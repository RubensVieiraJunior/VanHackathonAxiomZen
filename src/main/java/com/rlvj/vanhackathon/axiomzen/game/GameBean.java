package com.rlvj.vanhackathon.axiomzen.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.RandomStringUtils;

public class GameBean {

	private long date;
	
	private String user;
	
	private String gameKey;
	
	private int numGuesses;
	
	private boolean solved;
	
	private String[] colors = new String[] {Color.R.toString(), Color.B.toString(), Color.G.toString(), Color.Y.toString(), Color.O.toString(), Color.P.toString(), Color.C.toString(), Color.M.toString()};
	
	private int colorLength = 8;
	
	private boolean duplicate = true;
	
	private List<GuessBean> pastResults = new ArrayList<GuessBean>();
	
	private String[] secretCode = new String[colorLength];
	
	private GuessBean result;
	
	private long timeTaken;
	
	public GameBean(String user) {
		this.date = System.currentTimeMillis();
		this.gameKey = RandomStringUtils.randomAlphanumeric(20);
		this.user = user;
		
		generateSecret();
	}
	
	private void generateSecret() {
		List<String> list = new ArrayList<String>(Arrays.asList(colors));
		Random random = new Random();
		for (int x = 0; x < secretCode.length; x++) {
			int pos = random.nextInt(list.size());
			if (!duplicate) {
				secretCode[x] = list.remove(pos);
			} else {
				secretCode[x] = list.get(pos);
			}
		}
	}
	
	protected void checkGuess(String guess) {
		result = new GuessBean(guess);
		int near = result.getNear();
		int exact = result.getExact();
		
		Stack<Character> stackNear = new Stack<Character>();
		Map<Character, Long> mapSecret = Stream.of(secretCode).collect(Collectors.groupingBy((String t) -> t.charAt(0), Collectors.counting()));

		for (int x = 0; x < secretCode.length; x++) {
			Character charGuess = guess.charAt(x);
			if (secretCode[x].charAt(0) == charGuess.charValue()) {
				exact++;
				Long quantity = mapSecret.get(charGuess);
				mapSecret.put(charGuess, --quantity);
			} else {
				stackNear.addElement(charGuess);
			}
		}
		
		while ( !stackNear.isEmpty() ) {
			Character charNear = stackNear.pop();
			Long quantity = mapSecret.get(charNear);
			if (quantity != null && quantity.intValue() > 0) {
				near++;
				mapSecret.put(charNear, --quantity);
			}
		}
		
		solved = (exact == colorLength);
		result.setNear(near);
		result.setExact(exact);
		numGuesses++;
		pastResults.add(result);
		timeTaken = (System.currentTimeMillis() - this.date);
	}
	
	public boolean isValid() {
		return TimeUnit.MINUTES.convert(timeTaken, TimeUnit.MILLISECONDS) < 5;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public int getNumGuesses() {
		return numGuesses;
	}

	public void setNumGuesses(int numGuesses) {
		this.numGuesses = numGuesses;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public int getColorLength() {
		return colorLength;
	}

	public void setColorLength(int colorLength) {
		this.colorLength = colorLength;
	}

	public List<GuessBean> getPastResults() {
		return pastResults;
	}

	public void setPastResults(List<GuessBean> pastResults) {
		this.pastResults = pastResults;
	}

	public GuessBean getResult() {
		return result;
	}

	public void setResult(GuessBean result) {
		this.result = result;
	}

	public String getTimeTaken() {
		if (solved) {
			return String.format("Minutes: %d", TimeUnit.MINUTES.convert(timeTaken, TimeUnit.MILLISECONDS));
		} else {
			return "";
		}
	}

}
