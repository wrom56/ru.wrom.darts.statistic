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
import ru.wrom.darts.statistic.persist.repository.crud.CheckoutCrudRepository;
import ru.wrom.darts.statistic.persist.repository.crud.PlayerCrudRepository;
import ru.wrom.darts.statistic.ui.controller.GameController;
import ru.wrom.darts.statistic.util.SpringBeans;

import java.io.IOException;
import java.util.*;

public class DartsStatisticApplication extends Application {

	public static final Logger logger = LoggerFactory.getLogger(DartsStatisticApplication.class);

	public static final AnnotationConfigApplicationContext SPRING_CTX = new AnnotationConfigApplicationContext();

	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("locale.dictionary");

	public static DartsStatisticApplication instance = null;

	private Stage primaryStage;
	private BorderPane rootLayout;
	private GameController currentGameController;
	private Map<Integer, Checkout> checkoutMap;

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
		//openTrainingBullForm(GameType.BULL);
		openTrainingBullForm(GameType.GAME_501);
	}

	public void initRootLayout() {
		try {
			rootLayout = FXMLLoader.load(DartsStatisticApplication.class.getResource("/form/root.fxml"));
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


			Iterator<Player> playersIterator = players.iterator();

			List<PlayerGame> playerGames = new ArrayList<>();
			PlayerGame playerGame = new PlayerGame();
			playerGame.setGame(game);
			playerGame.setPlayer(playersIterator.next());
			playerGame.setOrderNumber(1);

			playerGames.add(playerGame);


			playerGame = new PlayerGame();
			playerGame.setGame(game);
			playerGame.setPlayer(playersIterator.next());
			playerGame.setOrderNumber(2);

			//		playerGames.add(playerGame);

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

			player = new Player();
			player.setName("Player 2");
			SPRING_CTX.getBean(PlayerCrudRepository.class).save(player);

			fillCheckoutTable();
		}
	}

	private static void fillCheckoutTable() {
		List<Checkout> checkouts = new ArrayList<>();
		checkouts.add(new Checkout(170, "T20", "T20", "50"));
		checkouts.add(new Checkout(167, "T20", "T19", "50"));
		checkouts.add(new Checkout(164, "T19", "T19", "50"));
		checkouts.add(new Checkout(161, "T20", "T17", "50"));
		checkouts.add(new Checkout(158, "T20", "T20", "D19"));
		checkouts.add(new Checkout(157, "T19", "T20", "D20"));
		checkouts.add(new Checkout(156, "T20", "T20", "D18"));
		checkouts.add(new Checkout(155, "T20", "T19", "D19"));
		checkouts.add(new Checkout(154, "T20", "T18", "D20"));
		checkouts.add(new Checkout(153, "T20", "T19", "D18"));
		checkouts.add(new Checkout(152, "T20", "T20", "D16"));
		checkouts.add(new Checkout(151, "T20", "T17", "D20"));
		checkouts.add(new Checkout(150, "T20", "T18", "D18"));

		checkouts.add(new Checkout(149, "T20", "T19", "D16"));
		checkouts.add(new Checkout(148, "T20", "T20", "D14"));
		checkouts.add(new Checkout(147, "T20", "T17", "D18"));
		checkouts.add(new Checkout(146, "T20", "T18", "D16"));
		checkouts.add(new Checkout(145, "T20", "T15", "D20"));
		checkouts.add(new Checkout(144, "T20", "T20", "D12"));
		checkouts.add(new Checkout(143, "T20", "T17", "D16"));
		checkouts.add(new Checkout(142, "T20", "T14", "D20"));
		checkouts.add(new Checkout(141, "T20", "T15", "D18"));
		checkouts.add(new Checkout(140, "T20", "T16", "D16"));
		checkouts.add(new Checkout(139, "T20", "T13", "D20"));
		checkouts.add(new Checkout(138, "T20", "T16", "D15"));
		checkouts.add(new Checkout(137, "T18", "T17", "D16"));
		checkouts.add(new Checkout(136, "T20", "T20", "D8"));


		checkouts.add(new Checkout(135, "T20", "T13", "D18"));
		checkouts.add(new Checkout(134, "T20", "T14", "D16"));
		checkouts.add(new Checkout(133, "T20", "T19", "D8"));
		checkouts.add(new Checkout(132, "T20", "T16", "D12"));
		checkouts.add(new Checkout(131, "T20", "T13", "D16"));
		checkouts.add(new Checkout(130, "T20", "T18", "D8"));
		checkouts.add(new Checkout(129, "T19", "T16", "D12"));
		checkouts.add(new Checkout(128, "T20", "T18", "D4"));
		checkouts.add(new Checkout(127, "T20", "T17", "D8"));
		checkouts.add(new Checkout(126, "T19", "T19", "50"));
		checkouts.add(new Checkout(125, "T20", "T19", "D4"));
		checkouts.add(new Checkout(124, "T20", "T16", "D8"));
		checkouts.add(new Checkout(123, "T20", "T13", "D12"));
		checkouts.add(new Checkout(122, "T18", "18", "50"));
		checkouts.add(new Checkout(121, "T19", "14", "50"));
		checkouts.add(new Checkout(120, "T20", "20", "D20"));

		checkouts.add(new Checkout(119, "T20", "19", "D20"));
		checkouts.add(new Checkout(118, "T20", "18", "D20"));
		checkouts.add(new Checkout(117, "T20", "17", "D20"));
		checkouts.add(new Checkout(116, "T20", "16", "D20"));
		checkouts.add(new Checkout(115, "T20", "15", "D20"));
		checkouts.add(new Checkout(114, "T20", "14", "D20"));
		checkouts.add(new Checkout(113, "T20", "13", "D20"));
		checkouts.add(new Checkout(112, "T20", "12", "D20"));
		checkouts.add(new Checkout(111, "T20", "19", "D16"));
		checkouts.add(new Checkout(110, "T20", "10", "D20"));

		checkouts.add(new Checkout(109, "T19", "12", "D20"));
		checkouts.add(new Checkout(108, "T20", "16", "D16"));
		checkouts.add(new Checkout(107, "T19", "10", "D20"));
		checkouts.add(new Checkout(106, "T20", "10", "D18"));
		checkouts.add(new Checkout(105, "T20", "13", "D16"));
		checkouts.add(new Checkout(104, "T20", "12", "D16"));
		checkouts.add(new Checkout(103, "T19", "10", "D18"));
		checkouts.add(new Checkout(102, "T20", "10", "D16"));
		checkouts.add(new Checkout(101, "T17", "10", "D20"));
		checkouts.add(new Checkout(100, "T20", "D20"));

		checkouts.add(new Checkout(99, "T19", "10", "D16"));
		checkouts.add(new Checkout(98, "T20", "D19"));
		checkouts.add(new Checkout(97, "T19", "D20"));
		checkouts.add(new Checkout(96, "T20", "D18"));
		checkouts.add(new Checkout(95, "T19", "D19"));
		checkouts.add(new Checkout(94, "T18", "D20"));
		checkouts.add(new Checkout(93, "T19", "D18"));
		checkouts.add(new Checkout(92, "T20", "D16"));
		checkouts.add(new Checkout(91, "T17", "D20"));
		checkouts.add(new Checkout(90, "T18", "D18"));

		checkouts.add(new Checkout(89, "T19", "D16"));
		checkouts.add(new Checkout(88, "T16", "D20"));
		checkouts.add(new Checkout(87, "T17", "D18"));
		checkouts.add(new Checkout(86, "T18", "D16"));
		checkouts.add(new Checkout(85, "T15", "D20"));
		checkouts.add(new Checkout(84, "T16", "D18"));
		checkouts.add(new Checkout(83, "T17", "D16"));
		checkouts.add(new Checkout(82, "T14", "D20"));
		checkouts.add(new Checkout(81, "T15", "D18"));
		checkouts.add(new Checkout(80, "T16", "D16"));

		checkouts.add(new Checkout(79, "T13", "D20"));
		checkouts.add(new Checkout(78, "T18", "D12"));
		checkouts.add(new Checkout(77, "T15", "D16"));
		checkouts.add(new Checkout(76, "T20", "D8"));
		checkouts.add(new Checkout(75, "T13", "D18"));
		checkouts.add(new Checkout(74, "T14", "D16"));
		checkouts.add(new Checkout(73, "T19", "D8"));
		checkouts.add(new Checkout(72, "T16", "D12"));
		checkouts.add(new Checkout(71, "T13", "D16"));
		checkouts.add(new Checkout(70, "T18", "D8"));

		checkouts.add(new Checkout(69, "19", "50"));
		checkouts.add(new Checkout(68, "T20", "D4"));
		checkouts.add(new Checkout(67, "T17", "D8"));
		checkouts.add(new Checkout(66, "T10", "D18"));
		checkouts.add(new Checkout(65, "T19", "D4"));
		checkouts.add(new Checkout(64, "T16", "D8"));
		checkouts.add(new Checkout(63, "T13", "D12"));
		checkouts.add(new Checkout(62, "T10", "D16"));
		checkouts.add(new Checkout(61, "T15", "D8"));
		checkouts.add(new Checkout(60, "20", "D20"));


		checkouts.add(new Checkout(59, "19", "D20"));
		checkouts.add(new Checkout(58, "18", "D20"));
		checkouts.add(new Checkout(57, "17", "D20"));
		checkouts.add(new Checkout(56, "16", "D20"));
		checkouts.add(new Checkout(55, "15", "D20"));
		checkouts.add(new Checkout(54, "14", "D20"));
		checkouts.add(new Checkout(53, "13", "D20"));
		checkouts.add(new Checkout(52, "12", "D20"));
		checkouts.add(new Checkout(51, "19", "D16"));
		checkouts.add(new Checkout(50, "10", "D20"));


		checkouts.add(new Checkout(49, "17", "D16"));
		checkouts.add(new Checkout(48, "16", "D16"));
		checkouts.add(new Checkout(47, "15", "D16"));
		checkouts.add(new Checkout(46, "6", "D20"));
		checkouts.add(new Checkout(45, "13", "D16"));
		checkouts.add(new Checkout(44, "12", "D16"));
		checkouts.add(new Checkout(43, "3", "D20"));
		checkouts.add(new Checkout(42, "10", "D16"));
		checkouts.add(new Checkout(41, "9", "D16"));

		checkouts.add(new Checkout(40, "D20"));

		checkouts.add(new Checkout(39, "7", "D16"));
		checkouts.add(new Checkout(38, "D19"));
		checkouts.add(new Checkout(37, "5", "D16"));
		checkouts.add(new Checkout(36, "D18"));
		checkouts.add(new Checkout(35, "3", "D16"));
		checkouts.add(new Checkout(34, "D17"));
		checkouts.add(new Checkout(33, "1", "D16"));
		checkouts.add(new Checkout(32, "D16"));
		checkouts.add(new Checkout(31, "15", "D8"));

		checkouts.add(new Checkout(30, "D15"));
		checkouts.add(new Checkout(29, "13", "D8"));
		checkouts.add(new Checkout(28, "D14"));
		checkouts.add(new Checkout(27, "11", "D8"));
		checkouts.add(new Checkout(26, "D13"));
		checkouts.add(new Checkout(25, "9", "D8"));
		checkouts.add(new Checkout(24, "D12"));
		checkouts.add(new Checkout(23, "7", "D8"));
		checkouts.add(new Checkout(22, "D11"));
		checkouts.add(new Checkout(21, "5", "D8"));

		checkouts.add(new Checkout(20, "D10"));
		checkouts.add(new Checkout(19, "3", "D8"));
		checkouts.add(new Checkout(18, "D9"));
		checkouts.add(new Checkout(17, "1", "D8"));
		checkouts.add(new Checkout(16, "D8"));
		checkouts.add(new Checkout(15, "7", "D4"));
		checkouts.add(new Checkout(14, "D7"));
		checkouts.add(new Checkout(13, "5", "D4"));
		checkouts.add(new Checkout(12, "D6"));
		checkouts.add(new Checkout(11, "3", "D4"));

		checkouts.add(new Checkout(10, "D5"));
		checkouts.add(new Checkout(9, "1", "D4"));
		checkouts.add(new Checkout(8, "D4"));
		checkouts.add(new Checkout(7, "3", "D2"));
		checkouts.add(new Checkout(6, "D3"));
		checkouts.add(new Checkout(5, "1", "D2"));
		checkouts.add(new Checkout(4, "D2"));
		checkouts.add(new Checkout(3, "1", "D1"));
		checkouts.add(new Checkout(2, "D1"));

		SPRING_CTX.getBean(CheckoutCrudRepository.class).save(checkouts);
	}

	public Map<Integer, Checkout> getCheckoutMap() {
		if (checkoutMap == null) {
			checkoutMap = new HashMap<>();
			SPRING_CTX.getBean(CheckoutCrudRepository.class).findAll().forEach(checkout -> checkoutMap.put(checkout.getFinishScore(), checkout));
		}
		return checkoutMap;
	}
}
