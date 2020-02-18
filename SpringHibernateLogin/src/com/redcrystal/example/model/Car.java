package com.redcrystal.example.model;

import java.io.Serializable;

public class Car implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7971547852258168026L;

	public String model;
	public int year;
	public String brand;
	public String color;
	public int price;
	public String id;

	public Car(String model, int year, String manufacturer, String color) {
		this.model = model;
		this.year = year;
		this.brand = manufacturer;
		this.color = color;
	}

	public Car(String model, int year, String manufacturer, String color, int price) {
		this.model = model;
		this.year = year;
		this.brand = manufacturer;
		this.color = color;
		this.price = price;
	}

	public Car(String id, String brand, int year, String color, int price, boolean soldState) {
		this.id = id;
		this.brand = brand;
		this.year = year;
		this.color = color;
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof Car))
			return false;

		Car compare = (Car) obj;

		return compare.model.equals(this.model);
	}

	@Override
	public int hashCode() {
		int hash = 1;

		return hash * 31 + model.hashCode();
	}

	@Override
	public String toString() {
		return "Car{" + "model=" + model + ", year=" + year + ", brand=" + brand + ", color=" + color + ", price=" + price + '}';
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
}
