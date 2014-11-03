package ru.wrom.darts.statistic.ui.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreButton {
	private String label;
	private List<String> values;

	public ScoreButton(String label, List<String> values) {
		this.label = label;
		this.values = values;
	}

	public ScoreButton(String label, String value) {
		this.label = label;
		this.values = Arrays.asList(value);
	}

	public ScoreButton(String value) {
		this.label = value;
		this.values = Arrays.asList(value);
	}

	public static List<ScoreButton> buildScoreButtons(String... values) {
		List<ScoreButton> scoreButtons = new ArrayList<>();
		for (String value : values) {
			scoreButtons.add(new ScoreButton(value));
		}
		return scoreButtons;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
}
