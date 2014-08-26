package ru.wrom.darts.statistic.persist.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.wrom.darts.statistic.entrypoint.SpringConfig;
import ru.wrom.darts.statistic.persist.entity.Game;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)

public class GameCrudRepositoryTest {
	@Autowired
	GameCrudRepository gameCrudRepository;

	@Test
	public void save() {

		Game game = new Game();
		gameCrudRepository.save(game);

	}


}