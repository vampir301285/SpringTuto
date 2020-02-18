/**
 * EnumConverter.java
 * 
 */
package com.redcrystal.example.converter;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.context.annotation.Scope;

/**
 * The Class EnumFilterConverter.
 * 
 * @author mngo
 */
@org.springframework.stereotype.Component
@Scope(value = "session")
public class EnumConverter implements Converter, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2970871764161730808L;

    /** The Constant ENUM_TYPE. */
    private static final String ENUM_TYPE = "EnumFilterConverter.enumType";

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Enum) {
            component.getAttributes().put(ENUM_TYPE, value.getClass());
            return ((Enum<?>) value).name();
        } else {
            throw new ConverterException(new FacesMessage("Value is not an enum: " + value.getClass()));
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Class<Enum> enumType = (Class<Enum>) component.getAttributes().get(ENUM_TYPE);
        try {
            return Enum.valueOf(enumType, value);
        } catch (IllegalArgumentException e) {
            throw new ConverterException(new FacesMessage("Value is not an enum of type: " + enumType));
        }
    }

}
