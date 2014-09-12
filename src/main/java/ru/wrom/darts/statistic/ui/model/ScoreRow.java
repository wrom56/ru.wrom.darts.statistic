package ru.wrom.darts.statistic.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.wrom.darts.statistic.persist.entity.PlayerGame;

public class ScoreRow {
	private PlayerGame playerGame;
	private StringProperty playerName = new SimpleStringProperty();
	private IntegerProperty dartCount = new SimpleIntegerProperty();
	private IntegerProperty score = new SimpleIntegerProperty();

	public ScoreRow(PlayerGame playerGame) {
		this.playerGame = playerGame;
		setPlayerName(playerGame.getPlayer().getName());
	}

	public String getPlayerName() {
		return playerName.get();
	}

	public StringProperty playerNameProperty() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName.set(playerName);
	}

	public int getScore() {
		return score.get();
	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	public void setScore(int score) {
		this.score.set(score);
	}

	public int getDartCount() {
		return dartCount.get();
	}

	public IntegerProperty dartCountProperty() {
		return dartCount;
	}

	public void setDartCount(int dartCount) {
		this.dartCount.set(dartCount);
	}

	public PlayerGame getPlayerGame() {
		return playerGame;
	}
}
