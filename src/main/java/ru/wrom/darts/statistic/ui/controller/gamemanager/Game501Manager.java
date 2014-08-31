package ru.wrom.darts.statistic.ui.controller.gamemanager;

import java.util.Arrays;
import java.util.List;

import ru.wrom.darts.statistic.ui.controller.IGameManager;

public class Game501Manager implements IGameManager {
	@Override
	public List<List<String>> getScoreButtonValues() {
		return Arrays.asList(null, null, null, null);
	}

	@Override
	public List<String> getScoreButtonLabels() {
		return Arrays.asList("-", "-", "-", "-");
	}

	@Override
	public int getStartScore() {
		return 501;
	}

}
