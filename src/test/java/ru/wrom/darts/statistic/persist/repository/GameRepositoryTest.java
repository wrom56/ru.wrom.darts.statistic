package ru.wrom.darts.statistic.persist.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.wrom.darts.statistic.entrypoint.SpringConfig;
import ru.wrom.darts.statistic.persist.entity.GameType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
public class GameRepositoryTest {

	@Autowired
	GameRepository gameRepository;

	@Test
	public void testName() throws Exception {
/*
		Query query = em.createQuery("select avg(pg.totalScore) from PlayerGame pg where pg.game.gameType = :gameType");
		query.setParameter("gameType", GameType.BULL);
		System.out.println("!!!!!!!!!!!!!");
		System.out.println(query.getSingleResult());
		System.out.println("!!!!!!!!!!!!!");
	*/
		System.out.println(gameRepository.getMaxScore(GameType.BULL, null));
	}
}