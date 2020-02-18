package com.redcrystal.example.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the access_group database table.
 * 
 */
@Entity
@Table(name = "access_group")
@NamedQuery(name = "AccessGroup.findAll", query = "SELECT a FROM AccessGroup a")
public class AccessGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int groupId;

	@Column(nullable = false, length = 100)
	private String name;

	// bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "accessGroups")
	private List<User> users;

	public AccessGroup() {
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + groupId;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessGroup other = (AccessGroup) obj;
		if (groupId != other.groupId)
			return false;
		return true;
	}

}