package ru.wrom.darts.statistic.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;
import ru.wrom.darts.statistic.persist.entity.Game;
import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.ui.model.ScoreRow;
import ru.wrom.darts.statistic.ui.model.StatRow;
import ru.wrom.darts.statistic.util.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class GameController {


	private Game game;
	private IGameManager gameManager;
	private Integer currentDartNumber;
	private PlayerGame currentPlayerGame;

	public TableView<ScoreRow> scoreTable;
	public TableColumn<ScoreRow, String> scoreTablePlayerColumn;
	public TableColumn<ScoreRow, Integer> scoreTableDartCountColumn;
	public TableColumn<ScoreRow, Integer> scoreTableScoreColumn;

	public TableView<StatRow> statTable;
	public TableColumn<StatRow, String> statTableLastScoreColumn;
	public TableColumn<StatRow, Integer> statTableAvgScoreColum;
	public TableColumn<StatRow, String> statTableMaxScoreColumn;

	public Button score0Button;
	public Button score1Button;
	public Button score2Button;
	public Button score3Button;
	public Button enterScoreButton;
	public TextField dart1ScoreTextField;
	public TextField dart2ScoreTextField;
	public TextField dart3ScoreTextField;
	public TextField attemptScoreTextField;

	@FXML
	private void initialize() {
		scoreTablePlayerColumn.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());
		scoreTableScoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
		scoreTableDartCountColumn.setCellValueFactory(cellData -> cellData.getValue().dartCountProperty().asObject());

		statTableLastScoreColumn.setCellValueFactory(cellData -> cellData.getValue().lastScoreProperty());
		statTableAvgScoreColum.setCellValueFactory(cellData -> cellData.getValue().avgScoreProperty().asObject());
		statTableMaxScoreColumn.setCellValueFactory(cellData -> cellData.getValue().maxScoreProperty());
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

		currentPlayerGame = game.getPlayerGames().get(0);

		startNextAttempt();

		addKeyTypedEvent(dart1ScoreTextField);
		addKeyTypedEvent(dart2ScoreTextField);
		addKeyTypedEvent(dart3ScoreTextField);

		addKeyReleasedEvent(dart1ScoreTextField, dart2ScoreTextField);
		addKeyReleasedEvent(dart2ScoreTextField, dart3ScoreTextField);

		dart3ScoreTextField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
			if (Utils.isDartMaxValue(dart3ScoreTextField.getText())) {
				submitCurrentAttemptScore();
			}
		});
		refreshForm();
	}

	private void addKeyTypedEvent(TextField textField) {
		textField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
					String newCharacter = keyEvent.getCharacter();
					if ("вВd".contains(newCharacter)) {
						newCharacter = "D";
					} else if ("еЕt".contains(newCharacter)) {
						newCharacter = "T";
					}
					keyEvent.consume();

					if (Utils.validDartEditValue(textField.getText() + newCharacter)) {
						textField.appendText(newCharacter);
					} else if (Utils.validDartEditValue(newCharacter)) {
						textField.setText("");
						textField.appendText(newCharacter);
					}
				}
		);
	}

	private void addKeyReleasedEvent(TextField currentTextField, TextField nextTextField) {
		currentTextField.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
			if (Utils.isDartMaxValue(currentTextField.getText())) {
				nextTextField.requestFocus();
			}
		});
	}

	private void startNextAttempt() {
		dart1ScoreTextField.setText("");
		dart2ScoreTextField.setText("");
		dart3ScoreTextField.setText("");
		attemptScoreTextField.setText("");
		currentDartNumber = 1;
		enterScoreButton.requestFocus();

		//	dart1ScoreTextField.requestFocus();

/*
				if (currentAttempt != null) {
			if (currentAttempt.getTotalScore() == null) {
				currentAttempt.setTotalScore(Utils.getScoreAsInt(currentAttempt.getDart1Score()) + Utils.getScoreAsInt(currentAttempt.getDart2Score()) + Utils.getScoreAsInt(currentAttempt.getDart3Score()));
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

		*/
	}

	private int getInt(Integer integer) {
		return integer != null ? integer : 0;
	}

	private void refreshForm() {

		for (ScoreRow scoreRow : scoreTable.getItems()) {
			scoreRow.setDartCount(getInt(scoreRow.getPlayerGame().getDartCount()));
			List<PlayerGameAttempt> attempts = scoreRow.getPlayerGame().getAttempts();
			if (attempts.size() > 0) {
				scoreRow.setLastAttemptScore(attempts.get(attempts.size() - 1).getTotalScore());
			} else {
				scoreRow.setLastAttemptScore(0);
			}
			scoreRow.setScore(getInt(scoreRow.getPlayerGame().getTotalScore()));

			if (isCurrentPlayerGame(scoreRow.getPlayerGame())) {
				scoreRow.setDartCount(scoreRow.getDartCount() + currentDartNumber - 1);
				scoreRow.setScore(scoreRow.getScore() + getCurentAttemptScore());
			}
		}

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
	}

	private boolean isCurrentPlayerGame(PlayerGame playerGame) {
		return playerGame.getOrderNumber() == currentPlayerGame.getOrderNumber();
	}

	public void onClickScore0Button() {
		onClickScoreButton(gameManager.getScoreButtonValues().get(0));
	}

	public void onClickScore1Button() {
		onClickScoreButton(gameManager.getScoreButtonValues().get(1));
	}

	public void onClickScore2Button() {
		onClickScoreButton(gameManager.getScoreButtonValues().get(2));
	}

	public void onClickScore3Button() {
		onClickScoreButton(gameManager.getScoreButtonValues().get(3));
	}

	private void onClickScoreButton(List<String> scoreButtonValues) {
		if (scoreButtonValues == null) {
			return;
		}
		for (String score : scoreButtonValues) {
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

	private void checkEndGame() {
	/*	if (game.getPlayerGames().get(0).getAttempts().size() == DartsStatisticApplication.TRAINING_ATTEMPT_COUNT) {
			SpringBeans.getBean(GameCrudRepository.class).save(game);
			currentAttempt = new PlayerGameAttempt();
		}
*/
	}

	private void submitCurrentAttemptScore() {

		PlayerGameAttempt attempt = buildCurrentAttempt();
		if (attempt == null) {
			return;
		}

		currentPlayerGame.addAttempt(attempt);
		currentPlayerGame.setDartCount(currentPlayerGame.getDartCount() + 3);

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


		} else {
			nextPlayer();
			startNextAttempt();
			refreshForm();
		}
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

	public void onClickSubmitScoreButton(ActionEvent actionEvent) {
		submitCurrentAttemptScore();
	}

	public int getCurentAttemptScore() {
		PlayerGameAttempt attempt = buildCurrentAttempt();
		return attempt != null ? attempt.getTotalScore() : 0;
	}

	private PlayerGameAttempt buildCurrentAttempt() {
		PlayerGameAttempt attempt = new PlayerGameAttempt();
		if (!Utils.isEmpty(attemptScoreTextField.getText())) {
			attempt.setTotalScore(Integer.valueOf(attemptScoreTextField.getText()));
		} else {
					/*
						if (Utils.isEmpty(dart1ScoreTextField.getText()) && Utils.isEmpty(dart2ScoreTextField.getText()) && Utils.isEmpty(dart3ScoreTextField.getText())) {
                return attempt;
            }
*/
			attempt.setDart1Score(Utils.isEmpty(dart1ScoreTextField.getText()) ? "0" : dart1ScoreTextField.getText());
			attempt.setDart2Score(Utils.isEmpty(dart2ScoreTextField.getText()) ? "0" : dart2ScoreTextField.getText());
			attempt.setDart3Score(Utils.isEmpty(dart3ScoreTextField.getText()) ? "0" : dart3ScoreTextField.getText());

			attempt.setTotalScore(Utils.getScoreAsInt(dart1ScoreTextField.getText()) + Utils.getScoreAsInt(dart2ScoreTextField.getText()) + Utils.getScoreAsInt(dart3ScoreTextField.getText()));
		}
		return attempt;
	}

	public void cancelLastAttempt() {
		PlayerGame playerGame = getPreviousPlayerGame();
		if (playerGame.getAttempts().size() > 0) {
			playerGame.getAttempts().remove(playerGame.getAttempts().size() - 1);
			playerGame.setDartCount(playerGame.getAttempts().size() * 3);
		}
		currentPlayerGame = playerGame;
		startNextAttempt();
		refreshForm();
	}
}
