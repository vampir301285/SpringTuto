package com.redcrystal.example.ws.domain;

import java.io.Serializable;


/** The user to be used in web service scope */
public class WSUser implements Serializable {

	/**
	 * generated serial version id
	 */
	private static final long serialVersionUID = -3636605000652159384L;

	private int id = -1;

	private String username;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WSUser [id=" + id + ", username=" + username + "]";
	}

}
