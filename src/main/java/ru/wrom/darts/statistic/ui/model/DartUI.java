package ru.wrom.darts.statistic.ui.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.wrom.darts.statistic.persist.entity.Dart;

public class DartUI {

	private IntegerProperty id = new SimpleIntegerProperty(UIConstant.NULL_INT_VALUE);
	private StringProperty name = new SimpleStringProperty();

	public DartUI(Dart dart) {
		setId(dart.getId());
		setName(dart.getLabel());
	}

	public DartUI() {

	}

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Dart getPersistObject() {
		Dart dart = new Dart();
		dart.setId(getId() != UIConstant.NULL_INT_VALUE ? getId() : null);
		dart.setLabel(getName());
		return dart;
	}
}
