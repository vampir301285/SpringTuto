package com.redcrystal.example.controller;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
/**
 * ViewSope for managed bean
 * 
 * @author mngo
 * 
 */
public class ViewScope implements Scope {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object get(String name, ObjectFactory objectFactory) {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        if (viewMap.containsKey(name)) {
            return viewMap.get(name);
        } else {
            Object object = objectFactory.getObject();
            viewMap.put(name, object);
            return object;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getConversationId() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerDestructionCallback(String arg0, Runnable arg1) {
        // Not supported
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object remove(String name) {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object resolveContextualObject(String arg0) {
        return null;
    }

}
