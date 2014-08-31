package ru.wrom.darts.statistic.ui.controller.gamemanager;

import java.util.Arrays;
import java.util.List;

import ru.wrom.darts.statistic.ui.controller.IGameManager;

public class Sector60GameManager implements IGameManager {
	@Override
	public List<List<String>> getScoreButtonValues() {
		return Arrays.asList(Arrays.asList("0"), Arrays.asList("20"), Arrays.asList("D20"), Arrays.asList("T20"));
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return Arrays.asList("0", "20", "D20", "T20");
	}
}
