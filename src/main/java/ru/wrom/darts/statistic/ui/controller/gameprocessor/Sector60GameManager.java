package ru.wrom.darts.statistic.ui.controller.gameprocessor;


import ru.wrom.darts.statistic.ui.controller.IGameManager;

import java.util.Arrays;
import java.util.List;

public class Sector60GameManager implements IGameManager {
	@Override
	public List<Integer> getScoreButtonValues() {
		return Arrays.asList(0, 20, 40, 60);
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return Arrays.asList("0", "20", "D20", "T20");
	}
}
