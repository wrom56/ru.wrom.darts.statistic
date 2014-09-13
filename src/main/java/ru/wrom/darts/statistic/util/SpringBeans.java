package ru.wrom.darts.statistic.util;


import org.springframework.beans.BeansException;
import ru.wrom.darts.statistic.entrypoint.DartsStatisticApplication;
import ru.wrom.darts.statistic.persist.repository.crud.ApplicationInfoCrudRepository;
import ru.wrom.darts.statistic.persist.repository.crud.DartCrudRepository;

public class SpringBeans {

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return DartsStatisticApplication.SPRING_CTX.getBean(requiredType);
	}

	public static DartCrudRepository getDartCrudRepository() {
		return DartsStatisticApplication.SPRING_CTX.getBean(DartCrudRepository.class);
	}

	public static ApplicationInfoCrudRepository getApplicationInfoCrudRepository() {
		return DartsStatisticApplication.SPRING_CTX.getBean(ApplicationInfoCrudRepository.class);
	}

}
