package ru.wrom.darts.statistic.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import ru.wrom.darts.statistic.persist.entity.PlayerGame;

public class StatRow {
	private PlayerGame playerGame;
	private StringProperty playerName = new SimpleStringProperty();
	private StringProperty lastScore = new SimpleStringProperty();
	private StringProperty maxScore = new SimpleStringProperty();
	private IntegerProperty avgScore = new SimpleIntegerProperty();


	public StatRow(PlayerGame playerGame) {
		this.playerGame = playerGame;
		setPlayerName(playerGame.getPlayer().getName());
	}

	public String getLastScore() {
		return lastScore.get();
	}

	public StringProperty lastScoreProperty() {
		return lastScore;
	}

	public void setLastScore(String lastScore) {
		this.lastScore.set(lastScore);
	}

	public void setPlayerGame(PlayerGame playerGame) {
		this.playerGame = playerGame;
	}

	public void setPlayerName(String playerName) {
		this.playerName.set(playerName);
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

	public int getAvgScore() {
		return avgScore.get();
	}

	public IntegerProperty avgScoreProperty() {
		return avgScore;
	}

	public String getMaxScore() {
		return maxScore.get();
	}

	public StringProperty maxScoreProperty() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore.set(maxScore);
	}
}
