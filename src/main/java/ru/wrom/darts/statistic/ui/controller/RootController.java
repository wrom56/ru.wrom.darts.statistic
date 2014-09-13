package ru.wrom.darts.statistic.ui.controller;

import javafx.event.ActionEvent;
import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;
import ru.wrom.darts.statistic.persist.entity.GameType;

public class RootController {

	public void openTrainingBullForm() {
		DartsStatisticApplication.getInstance().openTrainingBullForm(GameType.BULL);
	}

	public void openDartListForm() {
		DartsStatisticApplication.getInstance().openDartListForm();
	}

	public void openTraining60Form(ActionEvent actionEvent) {
		DartsStatisticApplication.getInstance().openTrainingBullForm(GameType.SECTOR_ATTEMPT);
	}

	public void cancelLastAttemptInCurrentGame(ActionEvent actionEvent) {
		DartsStatisticApplication.getInstance().cancelLastAttemptInCurrentGame();
	}

	public void openGame501Form(ActionEvent actionEvent) {
		DartsStatisticApplication.getInstance().openTrainingBullForm(GameType.GAME_501);
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
