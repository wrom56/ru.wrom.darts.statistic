package ru.wrom.darts.statistic.persist.entity;


public enum GameType {
	BULL, SECTOR_ATTEMPT, GAME_501(501), ALL_DOUBLE, BIG_ROUND;

	private int startScore;

	GameType() {
		this(0);
	}

	GameType(int startScore) {
		this.startScore = startScore;
	}

	public int getStartScore() {
		return startScore;
	}
}
