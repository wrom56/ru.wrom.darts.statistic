package ru.wrom.darts.statistic.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;
import ru.wrom.darts.statistic.persist.entity.Game;
import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.persist.repository.GameCrudRepository;
import ru.wrom.darts.statistic.ui.model.ScoreRow;
import ru.wrom.darts.statistic.ui.model.StatRow;
import ru.wrom.darts.statistic.util.SpringBeans;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameController {

	private Game game;
	private IGameManager gameManager;
	private Integer currentDartNumber;
	private PlayerGameAttempt currentAttempt;
	private PlayerGame currentPlayerGame;

	public TableView<ScoreRow> scoreTable;
	public TableColumn<ScoreRow, String> scoreTablePlayerColumn;
	public TableColumn<ScoreRow, Integer> scoreTableDartCountColumn;
	public TableColumn<ScoreRow, Integer> scoreTableScoreColumn;

	public TableView<StatRow> statTable;
	public TableColumn<StatRow, Integer> statTableLastScoreColumn;
	public TableColumn<StatRow, Integer> statTableAvgScoreColum;
	public TableColumn<StatRow, Integer> statTableMaxScoreColumn;

	public Button score0Button;
	public Button score1Button;
	public Button score2Button;
	public Button score3Button;
	public TextField dart1ScoreTextField;
	public TextField dart2ScoreTextField;
	public TextField dart3ScoreTextField;
	public TextField attemptScoreTextField;

	@FXML
	private void initialize() {
		scoreTablePlayerColumn.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());
		scoreTableScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
		scoreTableDartCountColumn.setCellValueFactory(cellData -> cellData.getValue().dartCountProperty().asObject());

		statTableLastScoreColumn.setCellValueFactory(cellData -> cellData.getValue().lastScoreProperty().asObject());
		statTableAvgScoreColum.setCellValueFactory(cellData -> cellData.getValue().avgScoreProperty().asObject());
		statTableMaxScoreColumn.setCellValueFactory(cellData -> cellData.getValue().maxScoreProperty().asObject());
	}

	public void init(Game game) {
		ObservableMap<KeyCombination, Runnable> accelerators = score1Button.getScene().getAccelerators();
		accelerators.put(new KeyCodeCombination(KeyCode.NUMPAD0), () -> score0Button.fire());
		accelerators.put(new KeyCodeCombination(KeyCode.NUMPAD1), () -> score1Button.fire());
		accelerators.put(new KeyCodeCombination(KeyCode.NUMPAD2), () -> score2Button.fire());
		accelerators.put(new KeyCodeCombination(KeyCode.NUMPAD3), () -> score3Button.fire());

		this.game = game;
		this.gameManager = GameManagerFactory.buildGameManager(game.getGameType());
		score0Button.setText(gameManager.getScoreButtonLabels().get(0));
		score1Button.setText(gameManager.getScoreButtonLabels().get(1));
		score2Button.setText(gameManager.getScoreButtonLabels().get(2));
		score3Button.setText(gameManager.getScoreButtonLabels().get(3));

		ObservableList<ScoreRow> playerScoreList = FXCollections.observableArrayList();
		playerScoreList.addAll(game.getPlayerGames().stream().map(playerGame -> new ScoreRow(playerGame)).collect(Collectors.toList()));
		scoreTable.setItems(playerScoreList);


		ObservableList<StatRow> statList = FXCollections.observableArrayList();
		statList.addAll(game.getPlayerGames().stream().map(playerGame -> new StatRow(playerGame)).collect(Collectors.toList()));
		statTable.setItems(statList);

		nextAttempt();
		refreshForm();
	}

	private void nextAttempt() {

		if (currentAttempt != null) {
			if (currentAttempt.getTotalScore() == null) {
				currentAttempt.setTotalScore(getInt(currentAttempt.getDart1Score()) + getInt(currentAttempt.getDart2Score()) + getInt(currentAttempt.getDart3Score()));
				currentAttempt.setUsedDarts(3);
			}
			currentPlayerGame.addAttempt(currentAttempt);
		}

		if (currentPlayerGame == null || currentPlayerGame.getOrderNumber() == game.getPlayerGames().size()) {
			currentPlayerGame = game.getPlayerGames().get(0);
		} else {
			currentPlayerGame = game.getPlayerGames().get(currentPlayerGame.getOrderNumber());
		}

		currentAttempt = new PlayerGameAttempt();
		currentDartNumber = 1;
	}


	private int getInt(Integer integer) {
		return integer != null ? integer : 0;
	}


	private void refreshForm() {
		dart1ScoreTextField.setText(Objects.toString(currentAttempt.getDart1Score(), ""));
		dart2ScoreTextField.setText(Objects.toString(currentAttempt.getDart2Score(), ""));
		dart3ScoreTextField.setText(Objects.toString(currentAttempt.getDart3Score(), ""));
		attemptScoreTextField.setText(Objects.toString(currentAttempt.getTotalScore(), ""));


		for (ScoreRow scoreRow : scoreTable.getItems()) {
			scoreRow.setDartCount(getInt(scoreRow.getPlayerGame().getDartCount()));
			List<PlayerGameAttempt> attempts = scoreRow.getPlayerGame().getAttempts();
			if (attempts.size() > 0) {
				scoreRow.setLastAttemptScore(attempts.get(attempts.size() - 1).getTotalScore());
			}
			scoreRow.setScore(getInt(scoreRow.getPlayerGame().getTotalScore()));

			if (isCurrentPlayerGame(scoreRow.getPlayerGame())) {
				scoreRow.setDartCount(scoreRow.getDartCount() + currentDartNumber - 1);
				scoreRow.setScore(scoreRow.getScore() + currentAttempt.getDartsScoreSum());
			}
		}

		statTable.getItems().stream().filter(statRow -> statRow.getPlayerGame().getAttempts().size() > 0).forEach(statRow -> {
					List<PlayerGameAttempt> attempts = statRow.getPlayerGame().getAttempts();
					statRow.setLastScore(attempts.get(attempts.size() - 1).getTotalScore());
					statRow.setMaxScore(attempts.stream().max((o1, o2) -> o1.getTotalScore().compareTo(o2.getTotalScore())).get().getTotalScore());
					if (statRow.getPlayerGame().getDartCount() > 0) {
						statRow.setAvgScore(getInt(statRow.getPlayerGame().getTotalScore()) / (statRow.getPlayerGame().getDartCount() / 3));
					}
				}
		);
	}

	private boolean isCurrentPlayerGame(PlayerGame playerGame) {
		return playerGame.getOrderNumber().equals(currentPlayerGame.getOrderNumber());
	}

	public void onClickScore0Button() {
		setDartScore(gameManager.getScoreButtonValues().get(0));
	}

	public void onClickScore1Button() {
		setDartScore(gameManager.getScoreButtonValues().get(1));
	}

	public void onClickScore2Button() {
		setDartScore(gameManager.getScoreButtonValues().get(2));
	}

	public void onClickScore3Button() {
		setDartScore(gameManager.getScoreButtonValues().get(3));
	}


	private void setDartScore(Integer score) {

		switch (currentDartNumber) {
			case 1:
				currentAttempt.setDart1Score(score);
				break;
			case 2:
				currentAttempt.setDart2Score(score);
				break;
			case 3:
				currentAttempt.setDart3Score(score);
				break;
		}

		if (currentDartNumber == 3) {
			nextAttempt();
			checkEndGame();
		} else {
			currentDartNumber++;
		}
		refreshForm();
	}

	private void checkEndGame() {
		if (game.getPlayerGames().get(0).getAttempts().size() == DartsStatisticApplication.TRAINING_ATTEMPT_COUNT) {
			SpringBeans.getBean(GameCrudRepository.class).save(game);
			currentAttempt = new PlayerGameAttempt();
		}

	}


}
