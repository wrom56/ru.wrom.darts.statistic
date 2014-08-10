package ru.wrom.darts.statistic.entrypoint;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.wrom.darts.statistic.persist.entity.ApplicationInfo;
import ru.wrom.darts.statistic.persist.entity.Dart;
import ru.wrom.darts.statistic.util.SpringBeans;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

public class DartsStatisticApplication extends Application {

	public static final Logger logger = LoggerFactory.getLogger(DartsStatisticApplication.class);

	public static final AnnotationConfigApplicationContext SPRING_CTX = new AnnotationConfigApplicationContext();

	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("locale.dictionary");

	private Stage primaryStage;
	private BorderPane rootLayout;

	public static void main(String[] args) {
		try {
			SPRING_CTX.register(SpringConfig.class);
			SPRING_CTX.refresh();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		onFirstStart();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(RESOURCE_BUNDLE.getString("mainFormHeader"));
		initRootLayout();
		showDartList();
	}

	public void initRootLayout() {
		try {
			rootLayout = new FXMLLoader().load(DartsStatisticApplication.class.getResource("/form/root.fxml"));
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDartList() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(RESOURCE_BUNDLE);
			AnchorPane personOverview = fxmlLoader.load(DartsStatisticApplication.class.getResource("/form/dartList.fxml").openStream());
			rootLayout.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	private static void onFirstStart() {
		if (SpringBeans.getApplicationInfoCrudRepository().count() == 0) {
			ApplicationInfo appInfo = new ApplicationInfo();
			appInfo.setId(1);
			appInfo.setLastRunTime(new Date());
			SpringBeans.getApplicationInfoCrudRepository().save(appInfo);

			Dart dart = new Dart();
			dart.setLabel("Noname");
			SpringBeans.getDartCrudRepository().save(dart);
		}
	}

}
