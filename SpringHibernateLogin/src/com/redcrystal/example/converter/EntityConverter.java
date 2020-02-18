package com.redcrystal.example.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.redcrystal.example.dao.AbstractDao;
import com.redcrystal.example.entities.AccessGroup;
import com.redcrystal.example.entities.Activity;
import com.redcrystal.example.entities.User;

@Component
@Scope(value = "session")
public class EntityConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6102682948483267462L;

	/** The Constant ENUM_TYPE. */
	private static final String CLASS = "EntityConverter.class";

	/** The dao. */
	@Autowired
	private AbstractDao abstractDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		component.getAttributes().put(CLASS, value.getClass());
		if (value instanceof AccessGroup) {// AccessGroup
			return String.valueOf(((AccessGroup) value).getGroupId());
		} else if (value instanceof User) {// User
			return String.valueOf(((User) value).getUserId());
		} else if (value instanceof Activity) {// Activity
			return String.valueOf(((Activity) value).getActivityId());
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		int id = 0;
		try {
			id = Integer.valueOf(value);
		} catch (NumberFormatException ex) {
			return null;
		}
		Class<?> objectClass = (Class<?>) component.getAttributes().get(CLASS);
		return abstractDao.find(objectClass, id);
	}
}
