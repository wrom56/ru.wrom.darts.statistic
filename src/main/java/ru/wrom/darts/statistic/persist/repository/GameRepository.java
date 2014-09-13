package ru.wrom.darts.statistic.persist.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wrom.darts.statistic.persist.entity.Dart;
import ru.wrom.darts.statistic.persist.entity.GameType;
import ru.wrom.darts.statistic.persist.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Transactional
@Repository
public class GameRepository {

	@PersistenceContext
	EntityManager em;

	public int getMaxScore(GameType gameType, Dart dart, Player player, Integer dayCount) {
		return getInt(buildQuery("max(pg.totalScore)", gameType, dart, player, dayCount).getSingleResult());
	}

	public double getAvgScore(GameType gameType, Dart dart, Player player, Integer dayCount) {
		return getDouble(buildQuery("avg (cast(pg.totalScore as float))", gameType, dart, player, dayCount).getSingleResult());
	}

	public int getMinDartCount(GameType gameType, Dart dart, Player player, Integer dayCount) {
		return getInt(buildQuery("min (pg.dartCount)", gameType, dart, player, dayCount).getSingleResult());
	}

	public double getAvgDartCount(GameType gameType, Dart dart, Player player, Integer dayCount) {
		return getDouble(buildQuery("avg (cast(pg.dartCount as float))", gameType, dart, player, dayCount).getSingleResult());
	}

	private Query buildQuery(String select, GameType gameType, Dart dart, Player player, Integer dayCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(select).append(" from PlayerGame pg where pg.game.gameType = :gameType and pg.isFinished = :isFinished");
		if (dart != null) {
			sb.append("and pg.dart = :dart");
		}
		Query query = em.createQuery(sb.toString());
		query.setParameter("gameType", gameType);
		query.setParameter("isFinished", true);
		if (dart != null) {
			query.setParameter("dart", dart);
		}
		return query;
	}

	private int getInt(Object queryResult) {
		return queryResult != null ? ((Number) queryResult).intValue() : 0;
	}

	private double getDouble(Object queryResult) {
		return queryResult != null ? ((Number) queryResult).doubleValue() : 0;
	}


}
