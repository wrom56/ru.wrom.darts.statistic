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
import ru.wrom.darts.statistic.persist.entity.Dart;
import ru.wrom.darts.statistic.persist.repository.crud.DartCrudRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class, loader = AnnotationConfigContextLoader.class)
public class DartCrudRepositoryTest {

	@Autowired
	DartCrudRepository dartCrudRepository;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testName() throws Exception {
		Dart dart = new Dart();
		dart.setLabel("Dart");
		dartCrudRepository.save(dart);
	}
}