package ru.wrom.darts.statistic.persist.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.wrom.darts.statistic.entrypoint.SpringConfig;
import ru.wrom.darts.statistic.persist.entity.Game;
import ru.wrom.darts.statistic.persist.entity.GameType;
import ru.wrom.darts.statistic.persist.entity.PlayerGameAttempt;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
public class DartCrudRepositoryTest {

	@Autowired
	GameCrudRepository gameCrudRepository;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testName() throws Exception {
		Game game = new Game();
		game.setGameType(GameType.BULL);
		game.setStartDate(new Date());
		game = gameCrudRepository.save(game);


		PlayerGameAttempt attempt = new PlayerGameAttempt();
		attempt.setDart1Score(50);
		attempt.setDart2Score(25);
		attempt.setDart3Score(null);
		attempt.setTotalScore(75);
		attempt.setUsedDarts(3);
		attempt.setAttemptDate(new Date());
		//game.getAttempts().add(attempt);


		game = gameCrudRepository.save(game);


	}
}