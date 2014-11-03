package ru.wrom.darts.statistic.ui.controller;

import ru.wrom.darts.statistic.entrypoint.DartsConstants;
import ru.wrom.darts.statistic.persist.entity.Game;
import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.ui.controller.gamemanager.ScoreEnteringType;

import java.util.Collections;
import java.util.List;

public interface IGameManager {

	default List<ScoreButton> getScoreButtons(GameInfo gameInfo) {
		return Collections.emptyList();
	}

	default List<ScoreElement> validateAttempt(GameInfo gameInfo) {
		return Collections.emptyList();
	}

	String getGameLabel();

	String getAttemptTip(GameInfo gameInfo);

	default boolean isEndOfGame(Game game) {
		return game.getPlayerGames().get(game.getPlayerGames().size() - 1).getDartCount() == DartsConstants.TRAINING_ATTEMPT_COUNT * 3;
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
