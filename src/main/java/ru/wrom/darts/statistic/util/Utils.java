package ru.wrom.darts.statistic.util;

import ru.wrom.darts.statistic.entrypoint.DartsConstants;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.ui.controller.ScoreElement;

import java.util.ArrayList;
import java.util.List;

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

	public static boolean isDoubleSectorScore(String score) {
		return !isEmpty(score) && ("D".contains(String.valueOf(score.charAt(0))));
	}

	public static String getAttemptScore(PlayerGameAttempt attempt) {
		if (attempt == null) {
			return "";
		}
		return getScoreLabel(attempt.getDart1Score(), attempt.getDart2Score(), attempt.getDart3Score(), attempt.getTotalScore());
	}

	public static String getScoreLabel(String dart1Score, String dart2Score, String dart3Score, Integer total) {
		StringBuilder sb = new StringBuilder();
		sb.append(total);
		if (dart1Score != null) {
			sb.append(" = ");
			sb.append(getDartScoreLabel(dart1Score));
			if (dart2Score != null) {
				sb.append(" + ");
				sb.append(getDartScoreLabel(dart2Score));
				if (dart3Score != null) {
					sb.append(" + ");
					sb.append(getDartScoreLabel(dart3Score));
				}
			}
		}
		return sb.toString();
	}

	public static String getDartScoreLabel(String dartScore) {
		if (dartScore != null && dartScore.equals("50")) {
			return "BULL";
		} else {
			return dartScore;
		}
	}


	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static boolean validDartEditValue(String dartValue) {
		try {
			if (dartValue.length() == 0) {
				return true;
			}

			if (dartValue.length() == 1 && dartValue.charAt(0) == '0') {
				return true;
			}

			if (dartValue.length() >= 4) {
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
				Integer intDartValue = Integer.valueOf(dartValue);
				if (intDartValue > 20 && intDartValue != 25 && intDartValue != 50) {
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

	public static boolean validTotalEditValue(String scoreValue) {
		if (scoreValue.length() == 0) {
			return true;
		}
		if (scoreValue.length() >= 4) {
			return false;
		}

		if (scoreValue.charAt(0) == '0' && scoreValue.length() > 1) {
			return false;
		}

		try {
			return Integer.parseInt(scoreValue) <= 180;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isValidDartScore(String dartScore) {
		try {
			getScoreAsInt(dartScore);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static List<ScoreElement> validateAttempt(PlayerGameAttempt attempt) {
		List<ScoreElement> result = new ArrayList<>();
		if (!isValidDartScore(attempt.getDart1Score())) {
			result.add(ScoreElement.DART1);
		}
		if (!isValidDartScore(attempt.getDart2Score())) {
			result.add(ScoreElement.DART2);
		}
		if (!isValidDartScore(attempt.getDart3Score())) {
			result.add(ScoreElement.DART3);
		}
		return result;
	}
}
