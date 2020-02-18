package com.redcrystal.example.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.ocpsoft.shade.org.apache.commons.beanutils.ConversionException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session")
public class LowerCaseConverter implements Converter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (!(modelValue instanceof String)) {
			throw new ConversionException("The object is not an instance of String type.");
		}
		return ((String) modelValue).toLowerCase();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null) {
			return null;
		}
		return submittedValue.toLowerCase();
	}

}
