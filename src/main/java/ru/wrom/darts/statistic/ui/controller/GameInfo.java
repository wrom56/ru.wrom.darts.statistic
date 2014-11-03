package ru.wrom.darts.statistic.ui.controller;

import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;

public class GameInfo {

	private PlayerGame game;
	private PlayerGameAttempt currentAttempt;

	public GameInfo(PlayerGame game, String dart1Score, String dart2Score) {
		this.game = game;
		this.currentAttempt = new PlayerGameAttempt();
		this.currentAttempt.setDart1Score(dart1Score);
		this.currentAttempt.setDart2Score(dart2Score);
	}

	public GameInfo(PlayerGame game, PlayerGameAttempt currentAttempt) {
		this.game = game;
		this.currentAttempt = currentAttempt;
	}

	public PlayerGame getGame() {
		return game;
	}

	public void setGame(PlayerGame game) {
		this.game = game;
	}

	public PlayerGameAttempt getCurrentAttempt() {
		return currentAttempt;
	}

	public void setCurrentAttempt(PlayerGameAttempt currentAttempt) {
		this.currentAttempt = currentAttempt;
	}

}
