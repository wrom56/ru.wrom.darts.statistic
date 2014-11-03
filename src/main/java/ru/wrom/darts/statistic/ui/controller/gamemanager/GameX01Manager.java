package ru.wrom.darts.statistic.ui.controller.gamemanager;

import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;
import ru.wrom.darts.statistic.persist.entity.*;
import ru.wrom.darts.statistic.ui.controller.GameInfo;
import ru.wrom.darts.statistic.ui.controller.IGameManager;
import ru.wrom.darts.statistic.ui.controller.ScoreButton;
import ru.wrom.darts.statistic.util.Utils;

import java.util.Arrays;
import java.util.List;

public class GameX01Manager implements IGameManager {

	private GameType gameType;

	public GameX01Manager(GameType gameType) {
		this.gameType = gameType;
	}

	@Override
	public List<ScoreButton> getScoreButtons(GameInfo gameInfo) {
		return Arrays.asList(new ScoreButton("0"), new ScoreButton("25"), new ScoreButton("50"));
	}

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
		return String.valueOf(gameType.getStartScore());
	}

	@Override
	public String getAttemptTip(GameInfo gameInfo) {
		int currentScore = gameType.getStartScore() - gameInfo.getGame().getTotalScore() - Utils.getScoreAsInt(gameInfo.getCurrentAttempt().getDart1Score()) - Utils.getScoreAsInt(gameInfo.getCurrentAttempt().getDart2Score());

		Checkout checkout = DartsStatisticApplication.getInstance().getCheckoutMap().get(currentScore);

		if (checkout != null) {
			return Utils.getScoreLabel(checkout.getDart1Score(), checkout.getDart2Score(), checkout.getDart3Score(), checkout.getFinishScore());
		} else {
			return "-";
		}
	}

	@Override
	public boolean isLegalAttempt(PlayerGame playerGame, PlayerGameAttempt attempt) {
		if (gameType.getStartScore() - (playerGame.getTotalScore() + attempt.getTotalScore()) > 1) {
			return true;
		}

		if (gameType.getStartScore() - (playerGame.getTotalScore() + attempt.getTotalScore()) == 0) {
			if (Utils.isDoubleSectorScore(attempt.getLastDartScore())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isEndOfGame(Game game) {
		for (PlayerGame playerGame : game.getPlayerGames()) {
			if (playerGame.getTotalScore() == gameType.getStartScore()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ScoreEnteringType getScoreEnteringType(PlayerGame playerGame) {
		if (gameType.getStartScore() - playerGame.getTotalScore() <= 170) {
			return ScoreEnteringType.DART;
		} else {
			return ScoreEnteringType.TOTAL;
		}
	}

	@Override
	public int getAttemptDartCount(PlayerGame playerGame, PlayerGameAttempt attempt) {
		if (attempt.isLegalAttempt()) {
			if (playerGame.getTotalScore() + attempt.getTotalScore() == gameType.getStartScore()) {
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
