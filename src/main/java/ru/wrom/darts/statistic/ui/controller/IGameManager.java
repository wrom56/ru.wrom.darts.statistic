package ru.wrom.darts.statistic.ui.controller;

import java.util.List;

public interface IGameManager {

	List<List<String>> getScoreButtonValues();

	List<String> getScoreButtonLabels();

	default int getStartScore() {
		return 0;
	}

	;
}
