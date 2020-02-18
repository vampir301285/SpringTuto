package com.redcrystal.example.dao;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redcrystal.example.controller.ManageUserController;
import com.redcrystal.example.controller.SessionProperty;
import com.redcrystal.example.entities.User;
import com.redcrystal.example.util.Utilities;

/**
 * not done yet
 * 
 * @InjectMocks – Instantiates testing object instance and tries to inject
 *              fields annotated with @Mock or @Spy into private fields of
 *              testing object
 * @Mock – Creates mock instance of the field it annotates
 * @Spy – Creates spy for instance of annotated field
 * 
 * @author mngo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:WebContent/WEB-INF/applicationContext.xml"})
public class ManageUserControllerTest {

	/** Logger object */
	private static final Logger LOGGER = Logger.getLogger(ManageUserControllerTest.class);

	@Mock
	private UserDao userDao;

	@Mock
	private SessionProperty sessionProperty;
	
	@Mock
	private FacesContext facesContext;

	@InjectMocks
	private ManageUserController manageUserController = new ManageUserController();

	@Before
	public void initMocks() {
		LOGGER.debug("initMocks");
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addUserTest() {
		User user = new User();
		user.setUsername("mockito");
		user.setHashedPassword(Utilities.sha256("123"));
		manageUserController.setNewUser(user);

		// stub
		sessionProperty.setRemoteUser(userDao.findUserByUsername("mngo"));
		Mockito.when(userDao.saveUser(user)).thenReturn(15);

		// execute command
		String outcome = manageUserController.addUser();
		Assert.assertEquals("", outcome);

		// verify outcome. In this case no outcome of the method
		// e.g. String result = controller.loginClicked();
		// assertEquals("failed", result);
		// Assert.assertTrue(true);

	}
}
