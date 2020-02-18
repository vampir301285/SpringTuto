package com.redcrystal.example.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author mngo
 * 
 */
@ManagedBean
@Controller
@Scope(value = "view")
public class ImagesView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9021937442871952963L;
	
	private List<String> images;
	
	@PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            images.add("nature" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }

}
