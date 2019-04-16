package fr.cgaiton611.cdb.dao;

import static org.junit.Assert.assertNotEquals;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.cdb.config.HibernateConfigTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigTest.class})
public class DataSourceTest {

	@Autowired
	DataSource ds;

	@Test
	public void creation() {
		assertNotEquals(ds, null);

	}

}