package ru.wrom.darts.statistic.ui.controller.gamemanager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.wrom.darts.statistic.persist.entity.Game;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.ui.controller.GameInfo;
import ru.wrom.darts.statistic.ui.controller.IGameManager;
import ru.wrom.darts.statistic.ui.controller.ScoreButton;
import ru.wrom.darts.statistic.ui.controller.ScoreElement;
import ru.wrom.darts.statistic.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BigRoundGameManager implements IGameManager {

	public static final Logger logger = LoggerFactory.getLogger(BigRoundGameManager.class);

	@Override
	public List<ScoreButton> getScoreButtons(GameInfo gameInfo) {
		int attemptNumber = gameInfo.getGame().getAttempts().size() + 1;
		if (attemptNumber <= 20) {
			return Arrays.asList(new ScoreButton("0"), new ScoreButton(String.valueOf(attemptNumber)), new ScoreButton("D" + attemptNumber), new ScoreButton("T" + attemptNumber));
		} else if (attemptNumber == 21) {
			return Arrays.asList(new ScoreButton("0"), new ScoreButton("25"), new ScoreButton("50"));
		} else {
			return Collections.emptyList();
		}

	}

	@Override
	public String getAttemptTip(GameInfo gameInfo) {
		int attemptNumber = gameInfo.getGame().getAttempts().size() + 1;
		if (attemptNumber <= 20) {
			return Utils.getAttemptTipScoreLabel("T" + attemptNumber, "T" + attemptNumber, "T" + attemptNumber);
		} else if (attemptNumber == 21) {
			return Utils.getAttemptTipScoreLabel("50", "50", "50");
		} else {
			return "";
		}
	}

	@Override
	public boolean isEndOfGame(Game game) {
		return game.getPlayerAttemptCount() == 21;
	}

	@Override
	public List<ScoreElement> validateAttempt(GameInfo gameInfo) {
		PlayerGameAttempt attempt = gameInfo.getCurrentAttempt();
		int attemptNumber = gameInfo.getGame().getAttempts().size() + 1;
		List<ScoreElement> result = new ArrayList<>();
		if (!isCorrectDartScore(attempt.getDart1Score(), attemptNumber)) {
			result.add(ScoreElement.DART1);
		}
		if (!isCorrectDartScore(attempt.getDart2Score(), attemptNumber)) {
			result.add(ScoreElement.DART2);
		}
		if (!isCorrectDartScore(attempt.getDart3Score(), attemptNumber)) {
			result.add(ScoreElement.DART3);
		}

		if (result.isEmpty()) {
			if (attemptNumber < 21) {
				if (attempt.getTotalScore() % attemptNumber != 0 || attempt.getTotalScore() > attemptNumber * 9) {
					result.add(ScoreElement.TOTAL);
				}
			} else {
				if (attempt.getTotalScore() % 25 != 0 || attempt.getTotalScore() > 150) {
					result.add(ScoreElement.TOTAL);
				}
			}
		}
		return result;
	}

	/*
			@Override
			public List<ScoreElement> validateAttempt(GameInfo gameInfo) {
				List<ScoreElement> result = new ArrayList<>();
				if (isCorrectDartScore(attempt.getDart1Score())) {
					result.add(ScoreElement.DART1);
				}
				if (isCorrectDartScore(attempt.getDart2Score())) {
					result.add(ScoreElement.DART2);
				}
				if (isCorrectDartScore(attempt.getDart3Score())) {
					result.add(ScoreElement.DART3);
				}

				if (attempt.getTotalScore() % 25 != 0 || attempt.getTotalScore() > 150) {
					result.add(ScoreElement.TOTAL);
				}
				return result;
			}
		*/
	@Override
	public List<List<String>> getScoreButtonValues() {
		return null;
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return null;
	}

	@Override
	public String getGameLabel() {
		return "Big round";
	}


	private boolean isCorrectDartScore(String dartScore, Integer attemptNumber) {
		if (dartScore == null) {
			return true;
		}

		if (attemptNumber < 21) {
			return Arrays.asList("0", "" + attemptNumber, "D" + attemptNumber, "T" + attemptNumber).contains(dartScore);
		} else {
			return Arrays.asList("0", "" + attemptNumber, "25", "50").contains(dartScore);
		}
	}
}
