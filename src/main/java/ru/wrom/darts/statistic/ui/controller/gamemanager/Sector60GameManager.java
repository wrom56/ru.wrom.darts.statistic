package ru.wrom.darts.statistic.ui.controller.gamemanager;

import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.ui.controller.IGameManager;

import java.util.Arrays;
import java.util.List;

public class Sector60GameManager implements IGameManager {
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
	public String getAttemptTip(PlayerGame playerGame, String dart1Score, String dart2Score) {
		return "180 = T20 + T20 + T20";
	}
}
