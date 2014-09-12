package ru.wrom.darts.statistic.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;
import ru.wrom.darts.statistic.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class AttemptRow {
	private IntegerProperty attemptNumber = new SimpleIntegerProperty();
	private List<StringProperty> attempts = new ArrayList<>();

	public AttemptRow(Integer number, List<PlayerGameAttempt> playerGameAttempts) {
		setAttemptNumber(number);
		playerGameAttempts.stream().forEach(attempt -> {
			attempts.add(new SimpleStringProperty(Utils.getAttemptScore(attempt)));
		});
	}

	public int getAttemptNumber() {
		return attemptNumber.get();
	}

	public IntegerProperty attemptNumberProperty() {
		return attemptNumber;
	}

	public void setAttemptNumber(int attemptNumber) {
		this.attemptNumber.set(attemptNumber);
	}

	public List<StringProperty> getAttempts() {
		return attempts;
	}
}
