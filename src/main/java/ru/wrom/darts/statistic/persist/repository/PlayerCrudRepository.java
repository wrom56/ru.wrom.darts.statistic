package ru.wrom.darts.statistic.persist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.wrom.darts.statistic.persist.entity.Player;


@Transactional
public interface PlayerCrudRepository extends CrudRepository<Player, Integer> {
}
