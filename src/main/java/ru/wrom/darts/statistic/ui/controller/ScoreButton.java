package ru.wrom.darts.statistic.ui.controller;


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
