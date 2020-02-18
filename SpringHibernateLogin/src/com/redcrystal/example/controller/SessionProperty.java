package com.redcrystal.example.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redcrystal.example.entities.User;

/**
 * to save http session related objects
 * 
 * @author mngo
 * 
 */
@ManagedBean
@Controller
@Scope(value = "session")
public class SessionProperty implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8986345826943060441L;

	/** The remote user */
    private User remoteUser;

    /** The indicator of the remote user is an administrator. */
    private boolean admin = false;

    /**
     * Gets the admin.
     * 
     * @return admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets the admin.
     * 
     * @param admin
     *            True, the user is admin. False, otherwise
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

	/**
	 * @return the remoteUser
	 */
	public User getRemoteUser() {
		return remoteUser;
	}

	/**
	 * @param remoteUser the remoteUser to set
	 */
	public void setRemoteUser(User remoteUser) {
		this.remoteUser = remoteUser;
	}

}
