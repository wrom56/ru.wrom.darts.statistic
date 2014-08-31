package ru.wrom.darts.statistic.ui.controller.gamemanager;


import java.util.Arrays;
import java.util.List;

import ru.wrom.darts.statistic.ui.controller.IGameManager;

public class BullGameManager implements IGameManager {
	@Override
	public List<List<String>> getScoreButtonValues() {
		return Arrays.asList(Arrays.asList("0"), Arrays.asList("25"), Arrays.asList("50"), null);
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return Arrays.asList("0", "25", "50", "");
	}
}
