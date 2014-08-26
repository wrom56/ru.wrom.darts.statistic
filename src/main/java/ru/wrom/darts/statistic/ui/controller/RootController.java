package ru.wrom.darts.statistic.ui.controller;

import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;

/**
 * Created by wrom on 18.08.2014.
 */
public class RootController {

	public void openTrainingBullForm() {
		DartsStatisticApplication.getInstance().openTrainingBullForm();
	}

	public void openDartListForm() {
		DartsStatisticApplication.getInstance().openDartListForm();
	}

	/*
	public void showBirthdayStatistics() {
    try {
        // Load the fxml file and create a new stage for the popup.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Birthday Statistics");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the persons into the controller.
        BirthdayStatisticsController controller = loader.getController();
        controller.setPersonData(personData);

        dialogStage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}
	 */
}
