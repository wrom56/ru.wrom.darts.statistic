package ru.wrom.darts.statistic.ui.controller.gamemanager;

import ru.wrom.darts.statistic.ui.controller.GameInfo;
import ru.wrom.darts.statistic.ui.controller.IGameManager;
import ru.wrom.darts.statistic.ui.controller.ScoreButton;
import ru.wrom.darts.statistic.util.Utils;

import java.util.List;

public class Sector60GameManager implements IGameManager {

	@Override
	public List<ScoreButton> getScoreButtons(GameInfo gameInfo) {
		return ScoreButton.buildScoreButtons("0", "20", "D20", "T20");
	}

	@Override
	public String getGameLabel() {
		return "Sector 60";
	}

	@Override
	public String getAttemptTip(GameInfo gameInfo) {
		return Utils.getAttemptTipScoreLabel("T20", "T20", "T20");
	}
}
