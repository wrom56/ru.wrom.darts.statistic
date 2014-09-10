package ru.wrom.darts.statistic.ui.controller;

import ru.wrom.darts.statistic.entrypoint.DartsConstants;
import ru.wrom.darts.statistic.persist.entity.Game;

import java.util.List;

public interface IGameManager {

	List<List<String>> getScoreButtonValues();

	List<String> getScoreButtonLabels();

	default int getStartScore() {
		return 0;
	}

	default boolean isEndOfGame(Game game) {
		return game.getPlayerGames().get(game.getPlayerGames().size() - 1).getDartCount() == DartsConstants.TRAINING_ATTEMPT_COUNT * 3;
	}
}
