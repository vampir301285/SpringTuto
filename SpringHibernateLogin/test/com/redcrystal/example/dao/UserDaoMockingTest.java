package com.redcrystal.example.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redcrystal.example.entities.User;

/**
 * Working perfectly. Using mockito with Spring
 * 
 * @author mngo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:WebContent/WEB-INF/applicationContext.xml"})
public class UserDaoMockingTest {

	@Autowired
	private UserDao userDao;

	private SessionFactory sessionFactory;

	private Session session;

	private Criteria criteria;

	@Before
	public void setup() {
		sessionFactory = mock(SessionFactory.class);
		session = mock(Session.class);
		criteria = mock(Criteria.class);
		userDao.setSessionFactory(sessionFactory);
	}

	/**
	 * to test the method {@link UserDao#saveUser(User)F}
	 */
	@Test
	public void saveUserTest() {
		User user = mock(User.class);
		user.setUsername("mngo");

		// when
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.save(user)).thenReturn(user);
		when(user.getUserId()).thenReturn(10);

		// outcome
		int outcome = userDao.saveUser(user);

		// conclude
		verify(user).getUserId();
		Assert.assertEquals(10, outcome);
	}

	/**
	 * to test the method {@link UserDao#findUserByUsername(String)}
	 */
	@Test
	public void findUserByNameUserTest() {
		// Given
		String username = "mngo";
		User user = new User();
		user.setUsername(username);

		// When
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createCriteria(User.class)).thenReturn(criteria);
		SimpleExpression simpleExp = Restrictions.eq("username", username);
		/**
		 * captures input used during the call. This will be used later for
		 * verifying
		 */
		ArgumentCaptor<SimpleExpression> argument = ArgumentCaptor.forClass(SimpleExpression.class);
		Mockito.when(criteria.uniqueResult()).thenReturn(user);

		// Outcome
		User outcome = userDao.findUserByUsername(username);

		// Conclude
		Assert.assertEquals(user, outcome);
		/** verifies arguments */
		verify(criteria).add(argument.capture());
		verify(criteria).setMaxResults(1);
		Assert.assertEquals(simpleExp.toString(), argument.getAllValues().get(0).toString());
	}

}
