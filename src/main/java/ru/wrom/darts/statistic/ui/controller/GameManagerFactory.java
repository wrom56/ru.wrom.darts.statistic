package ru.wrom.darts.statistic.ui.controller;

import ru.wrom.darts.statistic.persist.entity.GameType;
import ru.wrom.darts.statistic.ui.controller.gameprocessor.BullGameManager;
import ru.wrom.darts.statistic.ui.controller.gameprocessor.Sector60GameManager;

public class GameManagerFactory {

	public static IGameManager buildGameManager(GameType gameType) {
		switch (gameType) {
			case BULL:
				return new BullGameManager();
			case SECTOR_ATTEMPT:
				return new Sector60GameManager();
		}
		throw new IllegalArgumentException();
	}


}
