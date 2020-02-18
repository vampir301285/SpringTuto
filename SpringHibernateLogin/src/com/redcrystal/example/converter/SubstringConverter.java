/**
 * SubStringConverter.java
 * 
 */
package com.redcrystal.example.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Converts from substring
 * 
 * @author ryuen
 */
@Component
@Scope(value = "session")
public class SubstringConverter implements Converter, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5407373604585605839L;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String string = (String) value;
        int maxlength = Integer.valueOf((String) component.getAttributes().get("maxlength"));
        if (string.length() > maxlength) {
            return string.substring(0, maxlength) + "...";
        } else {
            return string;
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("getAsObject() is not supported in SubStringConverter");
    }
}