package ru.wrom.darts.statistic.ui.controller;

import ru.wrom.darts.statistic.entrypoint.DartsConstants;
import ru.wrom.darts.statistic.persist.entity.Game;
import ru.wrom.darts.statistic.persist.entity.Player;
import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.ui.controller.gamemanager.ScoreEnteringType;

import java.util.Collections;
import java.util.List;

public interface IGameManager {

	List<List<String>> getScoreButtonValues();

	List<String> getScoreButtonLabels();

	String getGameLabel();

	String getAttemptTip(PlayerGame playerGame, String dart1Score, String dart2Score);

	default boolean isEndOfGame(Game game) {
		return game.getPlayerGames().get(game.getPlayerGames().size() - 1).getDartCount() == DartsConstants.TRAINING_ATTEMPT_COUNT * 3;
	}

	default Player getWinner(Game game) {
		return null;
	}

	default List<ScoreElement> validateAttempt(PlayerGame playerGame, PlayerGameAttempt attempt) {
		return Collections.emptyList();
	}

	default boolean isLegalAttempt(PlayerGame currentPlayerGame, PlayerGameAttempt attempt) {
		return true;
	}

	default ScoreEnteringType getScoreEnteringType(PlayerGame playerGame) {
		return ScoreEnteringType.BUTTON;
	}


	default int getAttemptDartCount(PlayerGame currentPlayerGame, PlayerGameAttempt attempt) {
		return 3;
	}
}
