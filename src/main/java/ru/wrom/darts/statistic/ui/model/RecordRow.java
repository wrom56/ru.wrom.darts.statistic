package ru.wrom.darts.statistic.ui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecordRow {
	private StringProperty recordType = new SimpleStringProperty();
	private StringProperty absoluteValue = new SimpleStringProperty();
	private StringProperty sameDartValue = new SimpleStringProperty();

	public RecordRow(String recordType, String absoluteValue, String sameDartValue) {
		setRecordType(recordType);
		setAbsoluteValue(absoluteValue);
		setSameDartValue(sameDartValue);
	}

	public String getRecordType() {
		return recordType.get();
	}

	public StringProperty recordTypeProperty() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType.set(recordType);
	}

	public String getAbsoluteValue() {
		return absoluteValue.get();
	}

	public StringProperty absoluteValueProperty() {
		return absoluteValue;
	}

	public void setAbsoluteValue(String absoluteValue) {
		this.absoluteValue.set(absoluteValue);
	}

	public String getSameDartValue() {
		return sameDartValue.get();
	}

	public StringProperty sameDartValueProperty() {
		return sameDartValue;
	}

	public void setSameDartValue(String sameDartValue) {
		this.sameDartValue.set(sameDartValue);
	}
}
