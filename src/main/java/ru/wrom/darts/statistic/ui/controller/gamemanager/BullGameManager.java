package ru.wrom.darts.statistic.ui.controller.gamemanager;


import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.ui.controller.GameInfo;
import ru.wrom.darts.statistic.ui.controller.IGameManager;
import ru.wrom.darts.statistic.ui.controller.ScoreButton;
import ru.wrom.darts.statistic.ui.controller.ScoreElement;
import ru.wrom.darts.statistic.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BullGameManager implements IGameManager {

	@Override
	public List<ScoreButton> getScoreButtons(GameInfo gameInfo) {
		return Arrays.asList(new ScoreButton("0"), new ScoreButton("25"), new ScoreButton("50"));
	}

	@Override
	public String getGameLabel() {
		return "BULL";
	}

	@Override
	public String getAttemptTip(GameInfo gameInfo) {
		return "150 = BULL + BULL + BULL";
	}

	@Override
	public List<ScoreElement> validateAttempt(GameInfo gameInfo) {
		PlayerGameAttempt attempt = gameInfo.getCurrentAttempt();
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
