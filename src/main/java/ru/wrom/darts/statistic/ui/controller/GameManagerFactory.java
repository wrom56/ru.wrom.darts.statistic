package ru.wrom.darts.statistic.ui.controller;

import ru.wrom.darts.statistic.persist.entity.GameType;
import ru.wrom.darts.statistic.ui.controller.gamemanager.BigRoundGameManager;
import ru.wrom.darts.statistic.ui.controller.gamemanager.BullGameManager;
import ru.wrom.darts.statistic.ui.controller.gamemanager.GameX01Manager;
import ru.wrom.darts.statistic.ui.controller.gamemanager.Sector60GameManager;

public class GameManagerFactory {

	public static IGameManager buildGameManager(GameType gameType) {
		switch (gameType) {
			case BULL:
				return new BullGameManager();
			case SECTOR_ATTEMPT:
				return new Sector60GameManager();
			case GAME_501:
				return new GameX01Manager(GameType.GAME_501);
			case ALL_DOUBLE:
				return new Sector60GameManager();
			case BIG_ROUND:
				return new BigRoundGameManager();
		}
		throw new IllegalArgumentException();
	}


}
