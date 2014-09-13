package ru.wrom.darts.statistic.ui.controller.gamemanager;

import ru.wrom.darts.statistic.persist.entity.PlayerGame;
import ru.wrom.darts.statistic.ui.controller.IGameManager;

import java.util.Arrays;
import java.util.List;

public class AllDoubleGameManager implements IGameManager {
	@Override
	public List<List<String>> getScoreButtonValues() {
		return Arrays.asList(Arrays.asList("0"), Arrays.asList("D1"), null, null);
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return Arrays.asList("0", "D1", "", "");
	}

	@Override
	public String getGameLabel() {
		return "All double";
	}

	@Override
	public String getAttemptTip(PlayerGame playerGame, String dart1Score, String dart2Score) {
		return "D1 + D2 + D3";
	}
}
