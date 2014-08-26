package ru.wrom.darts.statistic.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.wrom.darts.statistic.persist.entity.PlayerGame;

public class StatRow {
	private PlayerGame playerGame;
	private StringProperty playerName = new SimpleStringProperty();
	private IntegerProperty lastScore = new SimpleIntegerProperty();
	private IntegerProperty maxScore = new SimpleIntegerProperty();
	private IntegerProperty avgScore = new SimpleIntegerProperty();


	public StatRow(PlayerGame playerGame) {
		this.playerGame = playerGame;
		setPlayerName(playerGame.getPlayer().getName());
	}

	public void setPlayerGame(PlayerGame playerGame) {
		this.playerGame = playerGame;
	}

	public void setPlayerName(String playerName) {
		this.playerName.set(playerName);
	}

	public void setLastScore(int lastScore) {
		this.lastScore.set(lastScore);
	}

	public void setMaxScore(int maxScore) {
		this.maxScore.set(maxScore);
	}

	public void setAvgScore(int avgScore) {
		this.avgScore.set(avgScore);
	}

	public PlayerGame getPlayerGame() {
		return playerGame;
	}

	public String getPlayerName() {
		return playerName.get();
	}

	public StringProperty playerNameProperty() {
		return playerName;
	}

	public int getLastScore() {
		return lastScore.get();
	}

	public IntegerProperty lastScoreProperty() {
		return lastScore;
	}

	public int getMaxScore() {
		return maxScore.get();
	}

	public IntegerProperty maxScoreProperty() {
		return maxScore;
	}

	public int getAvgScore() {
		return avgScore.get();
	}

	public IntegerProperty avgScoreProperty() {
		return avgScore;
	}
}
