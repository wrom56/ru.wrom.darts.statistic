package ru.wrom.darts.statistic.ui.controller.gamemanager;

import ru.wrom.darts.statistic.ui.controller.IGameManager;

public abstract class AbstractTrainingGameManager implements IGameManager {
	@Override
	public int getStartScore() {
		return 0;
	}
}
