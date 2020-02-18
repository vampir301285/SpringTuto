package com.redcrystal.example.controller.demo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redcrystal.example.model.Car;
import com.redcrystal.example.service.CarService;

@ManagedBean
@Controller
@Scope(value = "view")
public class DataScrollerView implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 77514737087333895L;

	/** Logger object */
    private static final Logger LOGGER = Logger.getLogger(DataScrollerView.class);
    
	private List<Car> cars;

	@Autowired
	private CarService service;
	
	@PostConstruct
    public void init() {
		LOGGER.info("Initialize data");
        cars = service.createCars(100);
    }
 
    public List<Car> getCars() {
        return cars;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
}
