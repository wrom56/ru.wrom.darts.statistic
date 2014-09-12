package ru.wrom.darts.statistic.ui.controller.gamemanager;

import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;
import ru.wrom.darts.statistic.persist.entity.Checkout;
import ru.wrom.darts.statistic.persist.entity.Game;
import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.ui.controller.IGameManager;
import ru.wrom.darts.statistic.util.Utils;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractGameX01Manager implements IGameManager {

	@Override
	public List<List<String>> getScoreButtonValues() {
		return Arrays.asList(null, null, null, null);
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return Arrays.asList("-", "-", "-", "-");
	}

	@Override
	public String getGameLabel() {
		return String.valueOf(getStartScore());
	}

	@Override
	public String getAttemptTip(PlayerGame playerGame, String dart1Score, String dart2Score) {
		int currentScore = getStartScore() - playerGame.getTotalScore() - Utils.getScoreAsInt(dart1Score) - Utils.getScoreAsInt(dart2Score);

		Checkout checkout = DartsStatisticApplication.getInstance().getCheckoutMap().get(currentScore);

		if (checkout != null) {
			return Utils.getScoreLabel(checkout.getDart1Score(), checkout.getDart2Score(), checkout.getDart3Score(), checkout.getFinishScore());
		} else {
			return "-";
		}
	}

	@Override
	public boolean isLegalAttempt(PlayerGame playerGame, PlayerGameAttempt attempt) {
		if (getStartScore() - (playerGame.getTotalScore() + attempt.getTotalScore()) > 1) {
			return true;
		}

		if (getStartScore() - (playerGame.getTotalScore() + attempt.getTotalScore()) == 0) {
			if (Utils.isDoubleSectorScore(attempt.getLastDartScore())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isEndOfGame(Game game) {
		for (PlayerGame playerGame : game.getPlayerGames()) {
			if (playerGame.getTotalScore() == getStartScore()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ScoreEnteringType getScoreEnteringType(PlayerGame playerGame) {
		if (getStartScore() - playerGame.getTotalScore() <= 170) {
			return ScoreEnteringType.DART;
		} else {
			return ScoreEnteringType.TOTAL;
		}
	}

	@Override
	public int getAttemptDartCount(PlayerGame playerGame, PlayerGameAttempt attempt) {
		if (attempt.isLegalAttempt()) {
			if (playerGame.getTotalScore() + attempt.getTotalScore() == getStartScore()) {
				if (Utils.getScoreAsInt(attempt.getDart3Score()) > 0) {
					return 3;
				} else if (Utils.getScoreAsInt(attempt.getDart2Score()) > 0) {
					return 2;
				} else {
					return 1;
				}
			}
		}
		return 3;
	}
}
