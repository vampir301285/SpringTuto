package com.redcrystal.example.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redcrystal.example.dao.UserDao;
import com.redcrystal.example.entities.AccessGroup;
import com.redcrystal.example.entities.User;
import com.redcrystal.example.util.Utilities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:WebContent/WEB-INF/applicationContext.xml"})
public class UserDaoTest {

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	@Test
	public void testSave() {
		String username = "mngo";
		User user = userDao.findUserByUsername(username);
		if (user == null) {
			user = new User();
			user.setUsername(username);
			user.setHashedPassword(Utilities.sha256("123"));
		}
		user.setRecActive(true);
		user.setFirstName("Minh Duc");
		user.setLastName("Ngo");
		AccessGroup group1 = userDao.findAccessGroupByName("USER");
		if (group1 == null) {
			group1 = new AccessGroup();
			group1.setName("USER");
			userDao.saveAccessGroup(group1);
		}
		AccessGroup group2 = userDao.findAccessGroupByName("ADMIN");
		if (group2 == null) {
			group2 = new AccessGroup();
			group2.setName("ADMIN");
			userDao.saveAccessGroup(group2);
		}

		List<AccessGroup> groups = new ArrayList<>();
		groups.add(group1);
		groups.add(group2);

		user.setAccessGroups(groups);
		userDao.saveUser(user);
		Assert.assertTrue(true);
	}

	@Test
	public void getUser() {
		User user = userDao.find(User.class, 1);
		Assert.assertNotNull(user);
		System.out.println(user.getUsername());
	}

	@Test
	public void testFindUser() {
		User user = userDao.findUserByUsername("mngo");
		Assert.assertNotNull(user);
		System.out.println("The user " + user.getUsername() + " is of following groups(" + user.getAccessGroups().size() + "): ");

		for (AccessGroup group : user.getAccessGroups()) {
			System.out.println(group.getName());
		}
		System.out.println(Utilities.sha256("minhduc85"));
	}
}
