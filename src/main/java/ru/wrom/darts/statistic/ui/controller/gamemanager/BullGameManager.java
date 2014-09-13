package ru.wrom.darts.statistic.ui.controller.gamemanager;


import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.ui.controller.IGameManager;
import ru.wrom.darts.statistic.ui.controller.ScoreElement;
import ru.wrom.darts.statistic.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BullGameManager implements IGameManager {
	@Override
	public List<List<String>> getScoreButtonValues() {
		return Arrays.asList(Arrays.asList("0"), Arrays.asList("25"), Arrays.asList("50"), null);
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return Arrays.asList("0", "25", "50", "");
	}

	@Override
	public String getGameLabel() {
		return "BULL";
	}

	@Override
	public String getAttemptTip(PlayerGame playerGame, String dart1Score, String dart2Score) {
		return "150 = BULL + BULL + BULL";
	}

	@Override
	public List<ScoreElement> validateAttempt(PlayerGame playerGame, PlayerGameAttempt attempt) {
		List<ScoreElement> result = new ArrayList<>();
		if (isIncorrectDartScore(attempt.getDart1Score())) {
			result.add(ScoreElement.DART1);
		}
		if (isIncorrectDartScore(attempt.getDart2Score())) {
			result.add(ScoreElement.DART2);
		}
		if (isIncorrectDartScore(attempt.getDart3Score())) {
			result.add(ScoreElement.DART3);
		}

		if (attempt.getTotalScore() % 25 != 0 || attempt.getTotalScore() > 150) {
			result.add(ScoreElement.TOTAL);
		}
		return result;
	}

	private boolean isIncorrectDartScore(String dartScore) {
		return Utils.getScoreAsInt(dartScore) % 25 != 0;
	}
}
