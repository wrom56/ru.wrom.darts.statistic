package ru.wrom.darts.statistic.entrypoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.wrom.darts.statistic.persist.entity.*;
import ru.wrom.darts.statistic.persist.repository.PlayerCrudRepository;
import ru.wrom.darts.statistic.ui.controller.GameController;
import ru.wrom.darts.statistic.util.SpringBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DartsStatisticApplication extends Application {

	public static final Logger logger = LoggerFactory.getLogger(DartsStatisticApplication.class);

	public static final AnnotationConfigApplicationContext SPRING_CTX = new AnnotationConfigApplicationContext();

	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("locale.dictionary");

	public static DartsStatisticApplication instance = null;

	private Stage primaryStage;
	private BorderPane rootLayout;
	private GameController currentGameController;

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public static DartsStatisticApplication getInstance() {
		return instance;
	}

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
		instance = this;
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(RESOURCE_BUNDLE.getString("mainFormHeader"));
		primaryStage.setWidth(1280);
		primaryStage.setHeight(800);
		initRootLayout();
		//openDartListForm();
		openTrainingBullForm(GameType.BULL);
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

	public void openDartListForm() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(RESOURCE_BUNDLE);
			AnchorPane personOverview = fxmlLoader.load(DartsStatisticApplication.class.getResource("/form/dartList.fxml").openStream());
			rootLayout.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openTrainingBullForm(GameType gameType) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(RESOURCE_BUNDLE);
			AnchorPane personOverview = fxmlLoader.load(DartsStatisticApplication.class.getResource("/form/game.fxml").openStream());
			rootLayout.setCenter(personOverview);
			Game game = new Game();
			//	game.setGameType(GameType.SECTOR_ATTEMPT);
			game.setGameType(gameType);


			Iterable<Player> players = SpringBeans.getBean(PlayerCrudRepository.class).findAll();


			List<PlayerGame> playerGames = new ArrayList<>();
			PlayerGame playerGame = new PlayerGame();
			playerGame.setGame(game);
			playerGame.setPlayer(players.iterator().next());
			playerGame.setOrderNumber(1);

			playerGames.add(playerGame);
			game.setPlayerGames(playerGames);

			currentGameController = fxmlLoader.getController();
			currentGameController.init(game);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelLastAttemptInCurrentGame() {
		currentGameController.cancelLastAttempt();
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

			Player player = new Player();
			player.setName("Player 1");
			SPRING_CTX.getBean(PlayerCrudRepository.class).save(player);
		}
	}

}
