package com.redcrystal.example.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redcrystal.example.dao.UserDao;
import com.redcrystal.example.entities.AccessGroup;
import com.redcrystal.example.entities.User;
import com.redcrystal.example.util.Utilities;

/**
 * 
 * @author mngo
 * 
 */
@ManagedBean
@Controller
@Scope(value = "view")
public class ManageUserController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -549186074216040216L;

	/** Logger object */
	private static final Logger LOGGER = Logger.getLogger(ManageUserController.class);

	/** UserDao is injected. */
	@Autowired
	private UserDao userDao;

	/** The session property is injected. */
	@Autowired
	private SessionProperty sessionProperty;

	/** The new user */
	private User newUser;

	/** the list of users from db */
	private List<User> userList;

	/** The filtered user list */
	private List<User> filteredUsers;

	/** The list of access group from db */
	private List<AccessGroup> accessGroupList;

	/**
	 * @return the accessGroupList
	 */
	public List<AccessGroup> getAccessGroupList() {
		return accessGroupList;
	}

	/**
	 * @param accessGroupList
	 *            the accessGroupList to set
	 */
	public void setAccessGroupList(List<AccessGroup> accessGroupList) {
		this.accessGroupList = accessGroupList;
	}

	@PostConstruct
	public void init() {
		newUser = new User();
		userList = userDao.findAll(User.class, "username", "asc");
		accessGroupList = userDao.findAll(AccessGroup.class, "name", "asc");
	}

	/**
	 * @return the userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * @return the newUser
	 */
	public User getNewUser() {
		return newUser;
	}

	/**
	 * @param newUser
	 *            the newUser to set
	 */
	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	/**
	 * auto-complete db groups when editing teams
	 * 
	 * @param query
	 *            the current input entered by user
	 * @return list of db group
	 */
	public List<AccessGroup> completeGroups(String query) {
		List<AccessGroup> groups = new ArrayList<AccessGroup>();
		/* in case of empty or null query */
		if (query == null || query.trim().isEmpty()) {
			return accessGroupList;
		}
		query = query.trim().toLowerCase();
		for (AccessGroup group : accessGroupList) {
			if (group.getName().toLowerCase().contains(query)) {
				groups.add(group);
			}
		}
		return groups;
	}

	public String addUser() {
		LOGGER.info("Add user");
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		newUser.setHashedPassword(Utilities.sha256(newUser.getHashedPassword()));
		newUser.setRecActive(true);
		newUser.setRecUpdatedBy(sessionProperty.getRemoteUser());
		if (userDao.findUserByUsername(newUser.getUsername()) != null) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Action failed", "The user with username '" + newUser.getUsername() + "' already exist.");
			RequestContext.getCurrentInstance().showMessageInDialog(msg);
			return "";
		}
		int id = userDao.saveUser(newUser);
		if (id > 0) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Action successful", "The new user with id " + id + " is added.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			context.execute("PF('addUserDlgWidget').hide()");
			userList = userDao.findAll(User.class, "username", "asc");
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Action failed", "Cannot save the user.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return null;
	}

	/**
	 * @return the filteredUsers
	 */
	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	/**
	 * @param filteredUsers
	 *            the filteredUsers to set
	 */
	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}
}
