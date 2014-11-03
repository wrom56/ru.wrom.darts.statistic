package ru.wrom.darts.statistic.ui.controller.gamemanager;

import ru.wrom.darts.statistic.ui.controller.GameInfo;
import ru.wrom.darts.statistic.ui.controller.IGameManager;
import ru.wrom.darts.statistic.ui.controller.ScoreButton;

import java.util.Arrays;
import java.util.List;

public class Sector60GameManager implements IGameManager {

	@Override
	public List<ScoreButton> getScoreButtons(GameInfo gameInfo) {
		return Arrays.asList(new ScoreButton("0"), new ScoreButton("25"), new ScoreButton("50"));
	}

	@Override
	public List<List<String>> getScoreButtonValues() {
		return Arrays.asList(Arrays.asList("0"), Arrays.asList("20"), Arrays.asList("D20"), Arrays.asList("T20"));
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return Arrays.asList("0", "20", "D20", "T20");
	}

	@Override
	public String getGameLabel() {
		return "Sector 60";
	}

	@Override
	public String getAttemptTip(GameInfo gameInfo) {
		return "180 = T20 + T20 + T20";
	}
}
