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

@Component
@Scope(value = "session")
public class AccessGroupConverter implements Converter, Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 6102682948483267462L;
	
	/** The dao. */
    @Autowired
    private AbstractDao abstractDao;
    
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		return abstractDao.find(AccessGroup.class, Integer.valueOf(id));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null) {
            return "";
        }
        return String.valueOf(((AccessGroup) value).getGroupId());
	}

}
