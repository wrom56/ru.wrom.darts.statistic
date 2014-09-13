package ru.wrom.darts.statistic.ui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecordRow {
	private StringProperty recordLabel = new SimpleStringProperty();
	private StringProperty bestValue = new SimpleStringProperty();
	private StringProperty avgValue = new SimpleStringProperty();

	public RecordRow(String recordLabel, String bestValue, String avgValue) {
		setRecordLabel(recordLabel);
		setBestValue(bestValue);
		setAvgValue(avgValue);
	}

	public String getRecordLabel() {
		return recordLabel.get();
	}

	public StringProperty recordLabelProperty() {
		return recordLabel;
	}

	public void setRecordLabel(String recordLabel) {
		this.recordLabel.set(recordLabel);
	}

	public String getBestValue() {
		return bestValue.get();
	}

	public StringProperty bestValueProperty() {
		return bestValue;
	}

	public void setBestValue(String bestValue) {
		this.bestValue.set(bestValue);
	}

	public String getAvgValue() {
		return avgValue.get();
	}

	public StringProperty avgValueProperty() {
		return avgValue;
	}

	public void setAvgValue(String avgValue) {
		this.avgValue.set(avgValue);
	}
}
