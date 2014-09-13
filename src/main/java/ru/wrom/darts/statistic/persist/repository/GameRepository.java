package ru.wrom.darts.statistic.persist.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wrom.darts.statistic.persist.entity.Dart;
import ru.wrom.darts.statistic.persist.entity.GameType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Transactional
@Repository
public class GameRepository {

	@PersistenceContext
	EntityManager em;

	public int getMaxScore(GameType gameType, Dart dart) {
		StringBuilder sb = new StringBuilder("select max(pg.totalScore) from PlayerGame pg where pg.game.gameType = :gameType");
		if (dart != null) {
			sb.append(" and pg.dart = :dart");
		}
		Query query = em.createQuery(sb.toString());
		query.setParameter("gameType", gameType);
		if (dart != null) {
			query.setParameter("dart", dart);
		}
		return (int) query.getSingleResult();
	}

	public int getAvgScore(GameType gameType, Dart dart) {
		StringBuilder sb = new StringBuilder("select avg(pg.totalScore) from PlayerGame pg where pg.game.gameType = :gameType");
		if (dart != null) {
			sb.append(" and pg.dart = :dart");
		}
		Query query = em.createQuery(sb.toString());
		query.setParameter("gameType", gameType);
		if (dart != null) {
			query.setParameter("dart", dart);
		}
		return (int) query.getSingleResult();
	}
}
