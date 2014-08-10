package ru.wrom.darts.statistic.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ru.wrom.darts.statistic.persist.entity.Dart;
import ru.wrom.darts.statistic.ui.model.DartUI;
import ru.wrom.darts.statistic.util.SpringBeans;

public class DartListController {
	@FXML
	public HBox dartDetailButtons;
	public HBox dartEditButtons;

	@FXML
	private TableView<DartUI> dartTable;

	@FXML
	private TableColumn<DartUI, String> nameColumn;

	@FXML
	private TextField nameTextField;

	@FXML
	private Button saveButton;

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button editButton;

	@FXML
	private Button cancelButton;


	private DartUI editedDart;

	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		dartTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			editedDart = newValue;
			updateDartDetailForm();
			updateButtons();
		});

		updateDartDetailForm();
		updateButtons();
		changeFormMode(false);
		updateTable(null);
	}

	private void onSelect(DartUI dartUI) {
		editedDart = dartUI;
	}

	private void updateDartDetailForm() {
		nameTextField.setText(editedDart != null ? editedDart.getName() : "");
	}

	private void updateButtons() {
		editButton.setDisable(editedDart == null);
		deleteButton.setDisable(editedDart == null);
	}


	private void updateTable(Integer selectedDartId) {
		Iterable<Dart> darts = SpringBeans.getDartCrudRepository().findAll();
		ObservableList<DartUI> dartList = FXCollections.observableArrayList();
		int index = 0;
		Integer selectedRow = null;
		for (Dart dart : darts) {
			if (dart.getId().equals(selectedDartId)) {
				selectedRow = index;
			}
			dartList.add(new DartUI(dart));
			index++;
		}

		dartTable.setItems(dartList);
		if (selectedRow == null) {
			dartTable.getSelectionModel().clearSelection();
			onSelect(null);
		} else {
			dartTable.getSelectionModel().select(selectedRow);
		}
	}

	@FXML
	private void save() {
		editedDart.setName(nameTextField.getText());
		Dart dart = SpringBeans.getDartCrudRepository().save(editedDart.getPersistObject());
		updateTable(dart.getId());
		changeFormMode(false);
	}

	@FXML
	private void add() {
		changeFormMode(true);
		dartTable.getSelectionModel().clearSelection();
		editedDart = new DartUI();
		updateDartDetailForm();
	}

	private void changeFormMode(boolean isEditMode) {
		dartEditButtons.setVisible(isEditMode);
		dartDetailButtons.setVisible(!isEditMode);
	}


	@FXML
	private void delete() {
		SpringBeans.getDartCrudRepository().delete(editedDart.getPersistObject());
		updateTable(null);
	}

	@FXML
	private void edit() {

	}

	@FXML
	private void cancel() {
		changeFormMode(false);
	}

}
