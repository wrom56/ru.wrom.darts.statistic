package ru.wrom.darts.statistic.ui.controller;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.wrom.darts.statistic.entrypoint.DartsConstants;
import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;
import ru.wrom.darts.statistic.persist.entity.Game;
import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.persist.repository.GameRepository;
import ru.wrom.darts.statistic.persist.repository.crud.GameCrudRepository;
import ru.wrom.darts.statistic.ui.model.AttemptRow;
import ru.wrom.darts.statistic.ui.model.RecordRow;
import ru.wrom.darts.statistic.ui.model.ScoreRow;
import ru.wrom.darts.statistic.util.SpringBeans;
import ru.wrom.darts.statistic.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {


	public static final String INCORRECT_SCORE_STYLE = "incorrect-score";
	public Label attemptTipLabel;

	private Game game;
	private IGameManager gameManager;
	private Integer currentDartNumber;
	private PlayerGame currentPlayerGame;
	private boolean isGameOver;

	public TableView<ScoreRow> scoreTable;
	public TableColumn<ScoreRow, String> scoreTableHeaderColumn;
	public TableColumn<ScoreRow, String> scoreTablePlayerColumn;
	public TableColumn<ScoreRow, Integer> scoreTableDartCountColumn;
	public TableColumn<ScoreRow, Integer> scoreTableScoreColumn;

	public TableView<AttemptRow> attemptsTable;
	public TableColumn<AttemptRow, String> attemptsTableHeaderColumn;
	public TableColumn<AttemptRow, Integer> attemptsTableNumberColumn;

	public TableView<RecordRow> recordTable;
	public TableColumn<RecordRow, String> recordTableRecordLabelColumn;
	public TableColumn<RecordRow, String> recordTableBestColumn;
	public TableColumn<RecordRow, String> recordTableAvgColumn;

	public Button score0Button;
	public Button score1Button;
	public Button score2Button;
	public Button score3Button;
	public Button enterScoreButton;
	public TextField dart1ScoreTextField;
	public TextField dart2ScoreTextField;
	public TextField dart3ScoreTextField;
	public TextField totalScoreTextField;

	ChangeListener<String> scoreChangeListener = null;
	ChangeListener<String> totalScoreChangeListener = null;

	public void init(Game game) {

		scoreTable.setSelectionModel(null);
		scoreTablePlayerColumn.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());
		scoreTableScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
		scoreTableDartCountColumn.setCellValueFactory(cellData -> cellData.getValue().dartCountProperty().asObject());

		attemptsTableNumberColumn.setCellValueFactory(cellData -> cellData.getValue().attemptNumberProperty().asObject());

		for (int i = 0; i < game.getPlayerGames().size(); i++) {
			TableColumn<AttemptRow, String> column = new TableColumn<>();
			column.setText(game.getPlayerGames().get(i).getPlayer().getName());
			final int index = i;
			column.setCellValueFactory(cellData -> cellData.getValue().getAttempts().get(index));
			attemptsTableHeaderColumn.getColumns().add(column);
		}

		recordTableRecordLabelColumn.setCellValueFactory(cellData -> cellData.getValue().recordLabelProperty());
		recordTableBestColumn.setCellValueFactory(cellData -> cellData.getValue().bestValueProperty());
		recordTableAvgColumn.setCellValueFactory(cellData -> cellData.getValue().avgValueProperty());


		ObservableList<RecordRow> recordRowList = FXCollections.observableArrayList();

		GameRepository gameRepository = SpringBeans.getBean(GameRepository.class);
		recordRowList.add(new RecordRow("Absolute score record", Utils.getScoreRecordMessage(gameRepository.getMaxScore(game.getGameType(), null, null, null)), Utils.getAvgScoreRecordMessage(gameRepository.getAvgScore(game.getGameType(), null, null, null))));
		recordRowList.add(new RecordRow("Absolute dart count record", Utils.getDartCountRecordMessage(gameRepository.getMinDartCount(game.getGameType(), null, null, null), game.getGameType().getStartScore()), Utils.getAvgDartCountRecordMessage(gameRepository.getAvgDartCount(game.getGameType(), null, null, null), game.getGameType().getStartScore())));


		recordTable.setItems(recordRowList);

		ObservableMap<KeyCombination, Runnable> accelerators = score1Button.getScene().getAccelerators();
		accelerators.put(new KeyCodeCombination(KeyCode.NUMPAD0), score0Button::fire);
		accelerators.put(new KeyCodeCombination(KeyCode.NUMPAD1), score1Button::fire);
		accelerators.put(new KeyCodeCombination(KeyCode.NUMPAD2), score2Button::fire);
		accelerators.put(new KeyCodeCombination(KeyCode.NUMPAD3), score3Button::fire);

		accelerators.put(new KeyCodeCombination(KeyCode.ENTER), this::onPressEnter);

		dart1ScoreTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				currentDartNumber = 1;
			} else {
				refreshAttemptTip();
			}
		});

		dart2ScoreTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				currentDartNumber = 2;
			} else {
				refreshAttemptTip();
			}
		});

		dart3ScoreTextField.focusedProperty().addListener((observable, oldValue, newValue) -> currentDartNumber = 3);

		isGameOver = false;
		this.game = game;
		this.game.setStartDate(new Date());
		this.gameManager = GameManagerFactory.buildGameManager(game.getGameType());

		scoreTableHeaderColumn.setText(gameManager.getGameLabel());


		ObservableList<ScoreRow> playerScoreList = FXCollections.observableArrayList();
		playerScoreList.addAll(game.getPlayerGames().stream().map(ScoreRow::new).collect(Collectors.toList()));
		scoreTable.setItems(playerScoreList);

		currentPlayerGame = game.getPlayerGames().get(0);

		startNextAttempt();

		scoreChangeListener = (observable, oldValue, newValue) -> {
			String modifiedValue = null;


			if (newValue.length() >= 1) {
				if ("-вВd".contains(String.valueOf(newValue.charAt(0)))) {
					StringBuilder sb = new StringBuilder();
					sb.append(DartsConstants.DOUBLE_SECTOR);
					for (int i = 1; i < newValue.length(); i++) {
						sb.append(newValue.charAt(i));
					}
					modifiedValue = sb.toString();
				} else if ("+еЕt".contains(String.valueOf(newValue.charAt(0)))) {
					StringBuilder sb = new StringBuilder();
					sb.append(DartsConstants.TRIPLE_SECTOR);
					for (int i = 1; i < newValue.length(); i++) {
						sb.append(newValue.charAt(i));
					}
					modifiedValue = sb.toString();
				}
			}
			if (modifiedValue == null && newValue.length() > 1) {
				for (int i = 1; i < newValue.length(); i++) {
					if (("-вВd" + DartsConstants.DOUBLE_SECTOR).contains(String.valueOf(newValue.charAt(i)))) {
						StringBuilder sb = new StringBuilder();
						sb.append(DartsConstants.DOUBLE_SECTOR);
						for (int j = 0; j < newValue.length(); j++) {
							if (j != i && newValue.charAt(j) != DartsConstants.TRIPLE_SECTOR) {
								sb.append(newValue.charAt(j));
							}
						}
						modifiedValue = sb.toString();
						break;
					}
					if (("+еЕt" + DartsConstants.TRIPLE_SECTOR).contains(String.valueOf(newValue.charAt(i)))) {
						StringBuilder sb = new StringBuilder();
						sb.append(DartsConstants.TRIPLE_SECTOR);
						for (int j = 0; j < newValue.length(); j++) {
							if (j != i && newValue.charAt(j) != DartsConstants.DOUBLE_SECTOR) {
								sb.append(newValue.charAt(j));
							}
						}
						modifiedValue = sb.toString();
						break;
					}
				}
			}


			if ((modifiedValue == null || !Utils.validDartEditValue(modifiedValue)) && !Utils.validDartEditValue(newValue)) {
				modifiedValue = oldValue;
			}

			if (modifiedValue != null) {
				observable.removeListener(scoreChangeListener);
				TextField textField = ((TextField) ((StringProperty) observable).getBean());
				textField.clear();
				textField.appendText(modifiedValue);
				observable.addListener(scoreChangeListener);
			}

			refreshScoreTextFields();

		};
		dart1ScoreTextField.textProperty().addListener(scoreChangeListener);
		dart2ScoreTextField.textProperty().addListener(scoreChangeListener);
		dart3ScoreTextField.textProperty().addListener(scoreChangeListener);


		totalScoreChangeListener = (observable, oldValue, newValue) -> {
			if (!Utils.validTotalEditValue(newValue)) {
				observable.removeListener(totalScoreChangeListener);
				TextField textField = ((TextField) ((StringProperty) observable).getBean());
				textField.clear();
				textField.appendText(oldValue);
				observable.addListener(totalScoreChangeListener);
			}
			refreshScoreTextFields();
		};
		totalScoreTextField.textProperty().addListener(totalScoreChangeListener);
		refreshForm();
	}


	private void onPressEnter() {
		if (dart1ScoreTextField.isFocused()) {
			dart2ScoreTextField.requestFocus();
		} else if (dart2ScoreTextField.isFocused()) {
			dart3ScoreTextField.requestFocus();
		} else {
			submitCurrentAttemptScore();
		}
		refreshForm();
	}

	private void startNextAttempt() {
		dart1ScoreTextField.setText("");
		dart2ScoreTextField.setText("");
		dart3ScoreTextField.setText("");
		totalScoreTextField.setText("");
		currentDartNumber = 1;

		switch (gameManager.getScoreEnteringType(currentPlayerGame)) {
			case BUTTON:
				enterScoreButton.requestFocus();
				break;
			case DART:
				dart1ScoreTextField.requestFocus();
				break;
			case TOTAL:
				totalScoreTextField.requestFocus();
				break;
		}
	}

	private void refreshScoreTextFields() {
		totalScoreTextField.setDisable(isGameOver || dart1ScoreTextField.getText().length() + dart2ScoreTextField.getText().length() + dart3ScoreTextField.getText().length() > 0);
		dart1ScoreTextField.setDisable(isGameOver || totalScoreTextField.getText().length() > 0);
		dart2ScoreTextField.setDisable(isGameOver || totalScoreTextField.getText().length() > 0);
		dart3ScoreTextField.setDisable(isGameOver || totalScoreTextField.getText().length() > 0);
	}

	private int getInt(Integer integer) {
		return integer != null ? integer : 0;
	}

	public void refreshForm() {

		List<ScoreButton> scoreButtons = gameManager.getScoreButtons(buildGameInfo());
		score0Button.setText(scoreButtons.size() > 0 ? scoreButtons.get(0).getLabel() : "");
		score1Button.setText(scoreButtons.size() > 1 ? scoreButtons.get(1).getLabel() : "");
		score2Button.setText(scoreButtons.size() > 2 ? scoreButtons.get(2).getLabel() : "");
		score3Button.setText(scoreButtons.size() > 3 ? scoreButtons.get(3).getLabel() : "");


		enterScoreButton.setDisable(isGameOver);
		score0Button.setDisable(isGameOver);
		score1Button.setDisable(isGameOver);
		score2Button.setDisable(isGameOver);
		score3Button.setDisable(isGameOver);

		for (ScoreRow scoreRow : scoreTable.getItems()) {
			scoreRow.setDartCount(getInt(scoreRow.getPlayerGame().getDartCount()));
			int currentScore = getInt(scoreRow.getPlayerGame().getTotalScore());
			if (game.getGameType().getStartScore() > 0) {
				scoreRow.setScore(game.getGameType().getStartScore() - currentScore);
			} else {
				scoreRow.setScore(currentScore);
			}
		}


		ObservableList<AttemptRow> attemptRowList = FXCollections.observableArrayList();

		for (int i = game.getPlayerAttemptCount(); i > 0; i--) {
			List<PlayerGameAttempt> attempts = new ArrayList<>();
			final int attemptNumber = i;
			game.getPlayerGames().stream().forEach(playerGame -> {
				if (playerGame.getAttempts().size() >= attemptNumber) {
					attempts.add(playerGame.getAttempts().get(attemptNumber - 1));
				} else {
					attempts.add(null);
				}
			});
			attemptRowList.add(new AttemptRow(attemptNumber, attempts));
		}
		attemptsTable.setItems(attemptRowList);
		/*
		for (StatRow statRow : statTable.getItems()) {
			if (statRow.getPlayerGame().getAttempts().size() > 0) {
				List<PlayerGameAttempt> attempts = statRow.getPlayerGame().getAttempts();
				statRow.setLastScore(Utils.getAttemptScore(attempts.get(attempts.size() - 1)));
				statRow.setMaxScore(Utils.getAttemptScore(attempts.stream().max((o1, o2) -> o1.getTotalScore().compareTo(o2.getTotalScore())).get()));
				if (statRow.getPlayerGame().getDartCount() > 0) {
					statRow.setAvgScore(getInt(statRow.getPlayerGame().getTotalScore()) / (statRow.getPlayerGame().getDartCount() / 3));
				} else {
					statRow.setAvgScore(0);
				}
			} else {
				statRow.setLastScore("");
				statRow.setMaxScore("");
				statRow.setAvgScore(0);
			}
		}
		*/
		refreshScoreTextFields();
		refreshAttemptTip();
	}

	public void onClickScore0Button() {
		onClickScoreButton(getScoreButton(0));
	}

	public void onClickScore1Button() {
		onClickScoreButton(getScoreButton(1));
	}

	public void onClickScore2Button() {
		onClickScoreButton(getScoreButton(2));
	}

	public void onClickScore3Button() {
		onClickScoreButton(getScoreButton(3));
	}

	private void onClickScoreButton(ScoreButton scoreButton) {
		if (scoreButton == null) {
			return;
		}
		for (String score : scoreButton.getValues()) {
			setDartScore(currentDartNumber, score);
			if (currentDartNumber == 3) {
				submitCurrentAttemptScore();
				break;
			} else {
				currentDartNumber++;
			}
		}
		refreshForm();
	}

	private ScoreButton getScoreButton(Integer index) {
		List<ScoreButton> scoreButtons = gameManager.getScoreButtons(buildGameInfo());
		return scoreButtons.size() > index ? scoreButtons.get(index) : null;
	}

	private void setDartScore(Integer dartNumber, String score) {
		switch (dartNumber) {
			case 1:
				dart1ScoreTextField.setText(score);
				break;
			case 2:
				dart2ScoreTextField.setText(score);
				break;
			case 3:
				dart3ScoreTextField.setText(score);
				break;
		}
	}

	private void submitCurrentAttemptScore() {

		dart1ScoreTextField.getStyleClass().remove(INCORRECT_SCORE_STYLE);
		dart2ScoreTextField.getStyleClass().remove(INCORRECT_SCORE_STYLE);
		dart3ScoreTextField.getStyleClass().remove(INCORRECT_SCORE_STYLE);
		totalScoreTextField.getStyleClass().remove(INCORRECT_SCORE_STYLE);


		List<ScoreElement> validateResult = new ArrayList<>();

		if (!Utils.isValidDartScore(dart1ScoreTextField.getText())) {
			validateResult.add(ScoreElement.DART1);
		}
		if (!Utils.isValidDartScore(dart2ScoreTextField.getText())) {
			validateResult.add(ScoreElement.DART2);
		}
		if (!Utils.isValidDartScore(dart3ScoreTextField.getText())) {
			validateResult.add(ScoreElement.DART3);
		}

		PlayerGameAttempt attempt = null;
		if (validateResult.isEmpty()) {
			attempt = buildCurrentAttempt();
			if (attempt == null) {
				return;
			}
			validateResult = gameManager.validateAttempt(new GameInfo(currentPlayerGame, attempt));
		}


		if (validateResult.isEmpty()) {
			attempt.setLegalAttempt(gameManager.isLegalAttempt(currentPlayerGame, attempt));
			attempt.setDartCount(gameManager.getAttemptDartCount(currentPlayerGame, attempt));
			if (attempt.getDart1Score() != null) {
				if (attempt.getDart2Score() == null && attempt.getDartCount() >= 2) {
					attempt.setDart2Score("0");
				}
				if (attempt.getDart3Score() == null && attempt.getDartCount() == 3) {
					attempt.setDart3Score("0");
				}
			}
			currentPlayerGame.addAttempt(attempt);
			refreshForm();

			if (gameManager.isEndOfGame(game)) {
				currentPlayerGame.setFinished(true);
				isGameOver = true;
				Stage dialog = new Stage();
				Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
				dialog.setScene(scene);

				dialog.initOwner(DartsStatisticApplication.getInstance().getPrimaryStage());
				dialog.show();
				game.setEndDate(new Date());
				SpringBeans.getBean(GameCrudRepository.class).save(game);

			} else {
				nextPlayer();
				startNextAttempt();
				refreshForm();
			}
			refreshForm();

		} else {
			validateResult.stream().forEach(scoreElement -> {

				switch (scoreElement) {
					case DART1:
						dart1ScoreTextField.getStyleClass().add(INCORRECT_SCORE_STYLE);
						break;
					case DART2:
						dart2ScoreTextField.getStyleClass().add(INCORRECT_SCORE_STYLE);
						break;
					case DART3:
						dart3ScoreTextField.getStyleClass().add(INCORRECT_SCORE_STYLE);
						break;
					case TOTAL:
						totalScoreTextField.getStyleClass().add(INCORRECT_SCORE_STYLE);
						break;
				}
			});
		}




/*
		if (gameManager.isEndOfGame(game)) {
			refreshForm();

			Stage dialog = new Stage();

			//		AnchorPane pane = new AnchorPane();
			//	pane.setMaxHeight(100);
			//	pane.setMaxWidth(100);

			Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
			//scene.se

			dialog.setScene(scene);


			dialog.initOwner(DartsStatisticApplication.getInstance().getPrimaryStage());

			dialog.show();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			dialog.hide();
*/
		//pane.

/*					Stage dialog = new Stage();
				//	dialog.initStyle(StageStyle.UTILITY);
					Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
					dialog.setScene(scene);

					//DartsStatisticApplication.getInstance().getPrimaryStage().setScene(scene);

					//scene.setRoot(DartsStatisticApplication.getInstance().getPrimaryStage());




					scene.setRoot(DartsStatisticApplication.getInstance().getPrimaryStage().getScene());

					score0Button.getScene().setCe
*/
		//DartsStatisticApplication.getInstance().getPrimaryStage().


					/*
					Dialogs dialogs = Dialogs.create()
							.owner(DartsStatisticApplication.getInstance().getPrimaryStage())
							.title("End of game")
							.masthead("Look, an Information Dialog")
							.message("I have a great message for you!");
							//.showInformation();
*/

	}

	private void refreshAttemptTip() {
		attemptTipLabel.setText(gameManager.getAttemptTip(buildGameInfo()));
	}

	private GameInfo buildGameInfo() {
		return new GameInfo(currentPlayerGame, Utils.getDartScore(dart1ScoreTextField.getText()), Utils.getDartScore(dart2ScoreTextField.getText()));
	}

	private void nextPlayer() {
		if (currentPlayerGame.getOrderNumber() == game.getPlayerGames().size()) {
			currentPlayerGame = game.getPlayerGames().get(0);
		} else {
			currentPlayerGame = game.getPlayerGames().get(currentPlayerGame.getOrderNumber());
		}
	}

	private PlayerGame getPreviousPlayerGame() {
		if (currentPlayerGame.getOrderNumber() == 1) {
			return game.getPlayerGames().get(game.getPlayerGames().size() - 1);
		} else {
			return game.getPlayerGames().get(currentPlayerGame.getOrderNumber() - 2);
		}
	}

	public void onClickSubmitScoreButton() {
		submitCurrentAttemptScore();
	}

	private PlayerGameAttempt buildCurrentAttempt() {
		PlayerGameAttempt attempt = new PlayerGameAttempt();
		if (!Utils.isEmpty(totalScoreTextField.getText())) {
			attempt.setTotalScore(Integer.valueOf(totalScoreTextField.getText()));
		} else {
			if (!Utils.isEmpty(dart3ScoreTextField.getText())) {
				attempt.setDart3Score(dart3ScoreTextField.getText());
			}
			if (!Utils.isEmpty(dart2ScoreTextField.getText())) {
				attempt.setDart2Score(dart2ScoreTextField.getText());
			}
			if (!Utils.isEmpty(dart1ScoreTextField.getText())) {
				attempt.setDart1Score(dart1ScoreTextField.getText());
			}

			if (attempt.getDart1Score() == null && attempt.getDart2Score() == null && attempt.getDart3Score() == null) {
				attempt.setDart1Score("0");
				attempt.setDart2Score("0");
				attempt.setDart3Score("0");
			} else {
				if (attempt.getDart3Score() != null && attempt.getDart2Score() == null) {
					attempt.setDart2Score("0");
				}
				if ((attempt.getDart3Score() != null || attempt.getDart2Score() != null) && attempt.getDart1Score() == null) {
					attempt.setDart1Score("0");
				}
			}

			attempt.setTotalScore(Utils.getScoreAsInt(attempt.getDart1Score()) + Utils.getScoreAsInt(attempt.getDart2Score()) + Utils.getScoreAsInt(attempt.getDart3Score()));
		}
		attempt.setAttemptDate(new Date());
		return attempt;
	}

	public void cancelLastAttempt() {
		PlayerGame playerGame = getPreviousPlayerGame();
		playerGame.removeLastAttempt();
		currentPlayerGame = playerGame;
		isGameOver = false;
		startNextAttempt();
		refreshForm();
	}

}
