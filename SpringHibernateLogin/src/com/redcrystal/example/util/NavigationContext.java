package com.redcrystal.example.util;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * The navigation context
 * 
 * @author mngo
 * 
 */
@ManagedBean
@Controller
@Scope(value = "session")
public class NavigationContext implements Serializable {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 3779040680290451138L;

	/**
	 * returns css class for tab menu
	 * 
	 * @param page
	 *            the page name
	 * @return "active" or "default" class
	 */
	public String getSubmenuStyle(String page) {
		if (getViewId().equalsIgnoreCase(page)) {
			return "active";
		}
		return "default";
	}

	/**
	 * @return returns xhtml page name
	 */
	public String getViewId() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String viewId = fc.getViewRoot().getViewId();
		String selectedComponent;
		if (viewId != null) {
			selectedComponent = viewId.substring(viewId.lastIndexOf("/") + 1, viewId.lastIndexOf("."));
		} else {
			selectedComponent = null;
		}
		return selectedComponent;
	}
}
