package com.redcrystal.example.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int userId;

	@Column(length = 45)
	private String firstName;

	@Column(length = 255)
	private String hashedPassword;

	@Column(length = 45)
	private String lastName;

	@Column(nullable = false)
	private boolean recActive;

	@Column(nullable = false, length = 45)
	private String username;

	// bi-directional many-to-one association to Activity
	@OneToMany(mappedBy = "user")
	private List<Activity> activities;

	// bi-directional many-to-many association to AccessGroup
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	@BatchSize(size = 10)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "user_access_group", joinColumns = {@JoinColumn(name = "UserId", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "GroupId", nullable = false)})
	private List<AccessGroup> accessGroups;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "RecUpdatedBy")
	private User recUpdatedBy;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "recUpdatedBy")
	private List<User> usersViaRecUpdatedBy;

	// bi-directional many-to-one association to UserHistory
	@OneToMany(mappedBy = "recCreatedBy")
	private List<UserHistory> userHistoriesViaRecCreated;

	// bi-directional many-to-one association to UserHistory
	@OneToMany(mappedBy = "reference")
	private List<UserHistory> userHistoriesViaReference;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean getRecActive() {
		return this.recActive;
	}

	public void setRecActive(boolean recActive) {
		this.recActive = recActive;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Activity> getActivities() {
		return this.activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public Activity addActivity(Activity activity) {
		getActivities().add(activity);
		activity.setUser(this);

		return activity;
	}

	public Activity removeActivity(Activity activity) {
		getActivities().remove(activity);
		activity.setUser(null);

		return activity;
	}

	public List<AccessGroup> getAccessGroups() {
		return this.accessGroups;
	}

	public void setAccessGroups(List<AccessGroup> accessGroups) {
		this.accessGroups = accessGroups;
	}

	public User getRecUpdatedBy() {
		return this.recUpdatedBy;
	}

	public void setRecUpdatedBy(User recUpdatedBy) {
		this.recUpdatedBy = recUpdatedBy;
	}

	public List<User> getUsersViaRecUpdatedBy() {
		return this.usersViaRecUpdatedBy;
	}

	public void setUsersViaRecUpdatedBy(List<User> usersViaRecUpdatedBy) {
		this.usersViaRecUpdatedBy = usersViaRecUpdatedBy;
	}

	public User addUsersViaRecUpdatedBy(User usersViaRecUpdatedBy) {
		getUsersViaRecUpdatedBy().add(usersViaRecUpdatedBy);
		usersViaRecUpdatedBy.setRecUpdatedBy(this);

		return usersViaRecUpdatedBy;
	}

	public User removeUsersViaRecUpdatedBy(User usersViaRecUpdatedBy) {
		getUsersViaRecUpdatedBy().remove(usersViaRecUpdatedBy);
		usersViaRecUpdatedBy.setRecUpdatedBy(null);

		return usersViaRecUpdatedBy;
	}

	public List<UserHistory> getUserHistoriesViaRecCreated() {
		return this.userHistoriesViaRecCreated;
	}

	public void setUserHistoriesViaRecCreated(List<UserHistory> userHistoriesViaRecCreated) {
		this.userHistoriesViaRecCreated = userHistoriesViaRecCreated;
	}

	public UserHistory addUserHistoriesViaRecCreated(UserHistory userHistoriesViaRecCreated) {
		getUserHistoriesViaRecCreated().add(userHistoriesViaRecCreated);
		userHistoriesViaRecCreated.setRecCreatedBy(this);

		return userHistoriesViaRecCreated;
	}

	public UserHistory removeUserHistoriesViaRecCreated(UserHistory userHistoriesViaRecCreated) {
		getUserHistoriesViaRecCreated().remove(userHistoriesViaRecCreated);
		userHistoriesViaRecCreated.setRecCreatedBy(null);

		return userHistoriesViaRecCreated;
	}

	public List<UserHistory> getUserHistoriesViaReference() {
		return this.userHistoriesViaReference;
	}

	public void setUserHistoriesViaReference(List<UserHistory> userHistoriesViaReference) {
		this.userHistoriesViaReference = userHistoriesViaReference;
	}

	public UserHistory addUserHistoriesViaReference(UserHistory userHistoriesViaReference) {
		getUserHistoriesViaReference().add(userHistoriesViaReference);
		userHistoriesViaReference.setReference(this);

		return userHistoriesViaReference;
	}

	public UserHistory removeUserHistoriesViaReference(UserHistory userHistoriesViaReference) {
		getUserHistoriesViaReference().remove(userHistoriesViaReference);
		userHistoriesViaReference.setReference(null);

		return userHistoriesViaReference;
	}
	

}