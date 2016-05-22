package com.rlvj.vanhackathon.axiomzen.game;

public class GuessBean {

	private String guess;
	
	private int exact = 0;
	
	private int near = 0;
	
	GuessBean (String guess) {
		this.guess = guess;
	}

	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}

	public int getExact() {
		return exact;
	}

	public void setExact(int exact) {
		this.exact = exact;
	}

	public int getNear() {
		return near;
	}

	public void setNear(int near) {
		this.near = near;
	}
	
}
