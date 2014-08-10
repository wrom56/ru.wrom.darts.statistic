package ru.wrom.darts.statistic.util;


import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;
import ru.wrom.darts.statistic.persist.repository.ApplicationInfoCrudRepository;
import ru.wrom.darts.statistic.persist.repository.DartCrudRepository;

public class SpringBeans {
	public static DartCrudRepository getDartCrudRepository() {
		return DartsStatisticApplication.SPRING_CTX.getBean(DartCrudRepository.class);
	}

	public static ApplicationInfoCrudRepository getApplicationInfoCrudRepository() {
		return DartsStatisticApplication.SPRING_CTX.getBean(ApplicationInfoCrudRepository.class);
	}
}
