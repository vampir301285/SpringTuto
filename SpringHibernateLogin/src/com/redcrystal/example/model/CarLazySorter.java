package com.redcrystal.example.model;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

public class CarLazySorter implements Comparator<Car> {

	/** The field to be sorted */
	private String sortField;

	/** The sort order */
	private SortOrder sortOrder;

	public CarLazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public int compare(Car car1, Car car2) {
		try {
			Object value1 = Car.class.getField(this.sortField).get(car1);
			Object value2 = Car.class.getField(this.sortField).get(car2);
			int value = ((Comparable) value1).compareTo(value2);
			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

}
