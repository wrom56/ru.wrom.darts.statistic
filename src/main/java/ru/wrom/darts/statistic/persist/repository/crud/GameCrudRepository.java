package ru.wrom.darts.statistic.persist.repository.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.wrom.darts.statistic.persist.entity.Game;


@Transactional
public interface GameCrudRepository extends CrudRepository<Game, Integer> {
}
