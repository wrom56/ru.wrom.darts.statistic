package ru.wrom.darts.statistic.util;

import java.text.MessageFormat;

import ru.wrom.darts.statistic.entrypoint.DartsConstants;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;

public class Utils {

	public static int getScoreAsInt(String scoreStr) {
		if (scoreStr == null || scoreStr.isEmpty()) {
			return 0;
		}

		if (scoreStr.charAt(0) == DartsConstants.TRIPLE_SECTOR) {
			return 3 * Integer.valueOf(scoreStr.substring(1));
		} else if (scoreStr.charAt(0) == DartsConstants.DOUBLE_SECTOR) {
			return 2 * Integer.valueOf(scoreStr.substring(1));
		} else {
			return Integer.valueOf(scoreStr);
		}
	}

	public static String getAttemptScore(PlayerGameAttempt attempt) {
		return MessageFormat.format("{3} ({0}; {1}; {2})", attempt.getDart1Score(), attempt.getDart2Score(), attempt.getDart3Score(), attempt.getTotalScore());
	}

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static boolean validDartEditValue(String dartValue) {

		try {

			if (dartValue.length() == 0 || dartValue.length() >= 4) {
				return false;
			}

			if (!"DT123456789".contains(String.valueOf(dartValue.charAt(0)))) {
				return false;
			} else if (dartValue.length() == 1) {
				return true;
			}

			if ("DT".contains(String.valueOf(dartValue.charAt(0)))) {
				if (!"123456789".contains(String.valueOf(dartValue.charAt(1)))) {
					return false;
				}
				if (dartValue.length() == 3) {
					if (Integer.valueOf(dartValue.substring(1)) > 20) {
						return false;
					}
				}
			} else {
				if (Integer.valueOf(dartValue) > 20) {
					return false;
				}
			}
		} catch (NumberFormatException exception) {
			return false;
		}
		return true;
	}

	public static boolean isDartMaxValue(String dartValue) {
		if (dartValue.length() == 0) {
			return false;
		}

		Integer intDartValue = 0;
		if ("DT".contains(String.valueOf(dartValue.charAt(0)))) {
			if (dartValue.length() >= 2) {
				intDartValue = Integer.valueOf(dartValue.substring(1));
			}
		} else {
			intDartValue = Integer.valueOf(dartValue);
		}
		return intDartValue * 10 > 20;
	}

}
