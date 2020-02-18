package com.redcrystal.example.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;

import com.redcrystal.example.dao.UserDao;
import com.redcrystal.example.entities.AccessGroup;
import com.redcrystal.example.entities.User;

/**
 * 
 * @author mngo
 * 
 */
@ManagedBean
@Controller
@Scope(value = "request")
public class LoginController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5103628689900262372L;

	/** Logger object */
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);

	/** the user dao is injected */
	@Autowired
	private UserDao userDao;

	/** The session property */
	@Autowired
	private SessionProperty sessionProperty;

	/** The user name. */
	private String username = "";

	/** The password. */
	private String password = "";

	/**
	 * Getter for login user name.
	 * 
	 * @return the login user name.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Setter for the login user name.
	 * 
	 * @param username
	 *            The login user name.
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Getter for login password.
	 * 
	 * @return the login password.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Setter for login password.
	 * 
	 * @param password
	 *            The login password.
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	public void login() throws ServletException, IOException {
		LOGGER.info("Login security check.");
		// Check security using spring security feature
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		// Get the current remote user and set it in user controller.
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			LOGGER.debug("Authentication successful.");
			String remoteUser = SecurityContextHolder.getContext().getAuthentication().getName();
			LOGGER.info(remoteUser + " Logged");
			User user = userDao.findUserByUsername(remoteUser);
			sessionProperty.setRemoteUser(user);
			for (AccessGroup group : user.getAccessGroups()) {
				if (group.getName().equalsIgnoreCase("admin")) {
					sessionProperty.setAdmin(true);
					break;
				}
			}
		} else {
			LOGGER.debug("Authentication failed.");
		}
	}

	/**
	 * Handle login error.
	 */
	public void handleErrorMessage() {
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (e instanceof AuthenticationException) {
			LOGGER.error(e.getMessage());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication failed", "Invalid username or password."));
			RequestContext context = RequestContext.getCurrentInstance();
			context.update("growl");
		}
	}

	/**
	 * to sign out. It should be used by p:commandButton
	 * 
	 * @return null string
	 */
	public void signout() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		String url = context.getRequestContextPath() + "/j_spring_security_logout?faces-redirect=true";
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
