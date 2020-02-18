package com.redcrystal.example.controller.demo;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redcrystal.example.model.Car;
import com.redcrystal.example.model.CarLazyDataModel;
import com.redcrystal.example.service.CarService;

@ManagedBean
@Controller
@Scope(value = "view")
public class DataTableLazyView implements Serializable {

	/**
	 * generated serial version id
	 */
	private static final long serialVersionUID = 2778295089180168954L;

	/** The CarService is injected */
	@Autowired
	private CarService service;

	private LazyDataModel<Car> lazyModel;

	private Car selectedCar;

	@PostConstruct
	public void init() {
		lazyModel = new CarLazyDataModel(service.createCars(200));
	}

	/**
	 * @return the lazyModel
	 */
	public LazyDataModel<Car> getLazyModel() {
		return lazyModel;
	}

	/**
	 * @param lazyModel
	 *            the lazyModel to set
	 */
	public void setLazyModel(LazyDataModel<Car> lazyModel) {
		this.lazyModel = lazyModel;
	}

	/**
	 * @return the selectedCar
	 */
	public Car getSelectedCar() {
		return selectedCar;
	}

	/**
	 * @param selectedCar
	 *            the selectedCar to set
	 */
	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getId());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
