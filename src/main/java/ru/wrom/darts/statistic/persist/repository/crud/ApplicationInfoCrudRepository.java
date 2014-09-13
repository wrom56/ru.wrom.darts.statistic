package ru.wrom.darts.statistic.persist.repository.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.wrom.darts.statistic.persist.entity.ApplicationInfo;


@Transactional
public interface ApplicationInfoCrudRepository extends CrudRepository<ApplicationInfo, Integer> {
}
