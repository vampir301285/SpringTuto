package com.redcrystal.example.model;

import java.io.Serializable;

public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8962890620315721093L;

	/** The event id */
	private int eventId;

	/** The event name */
	private String name;

	/** event time on */
	private String todayOn;

	/** event time on */
	private String todayPlusOneOn;

	/** event time on */
	private String todayPlusTwoOn;

	/** event time on */
	private String todayPlusThreeOn;

	/** event time on */
	private String todayPlusFourOn;

	/** event time on */
	private String todayPlusFiveOn;

	/** event time on */
	private String todayPlusSixOn;

	/** event time on */
	private String otherDates;

	/** The event place */
	private String place;

	/** The flavor */
	private String flavor;

	public Event(String name, String todayOn, String todayPlusOneOn, String todayPlusTwoOn, String todayPlusThreeOn, String todayPlusFourOn,
			String todayPlusFiveOn, String todayPlusSixOn, String otherDates, String place, String flavor) {
		super();
		this.name = name;
		this.todayOn = todayOn;
		this.todayPlusOneOn = todayPlusOneOn;
		this.todayPlusTwoOn = todayPlusTwoOn;
		this.todayPlusThreeOn = todayPlusThreeOn;
		this.todayPlusFourOn = todayPlusFourOn;
		this.todayPlusFiveOn = todayPlusFiveOn;
		this.todayPlusSixOn = todayPlusSixOn;
		this.otherDates = otherDates;
		this.place = place;
		this.setFlavor(flavor);
	}

	public Event() {

	}
	/**
	 * @return the eventId
	 */
	public int getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the todayOn
	 */
	public String getTodayOn() {
		return todayOn;
	}

	/**
	 * @param todayOn
	 *            the todayOn to set
	 */
	public void setTodayOn(String todayOn) {
		this.todayOn = todayOn;
	}

	/**
	 * @return the todayPlusOneOn
	 */
	public String getTodayPlusOneOn() {
		return todayPlusOneOn;
	}

	/**
	 * @param todayPlusOneOn
	 *            the todayPlusOneOn to set
	 */
	public void setTodayPlusOneOn(String todayPlusOneOn) {
		this.todayPlusOneOn = todayPlusOneOn;
	}

	/**
	 * @return the todayPlusTwoOn
	 */
	public String getTodayPlusTwoOn() {
		return todayPlusTwoOn;
	}

	/**
	 * @param todayPlusTwoOn
	 *            the todayPlusTwoOn to set
	 */
	public void setTodayPlusTwoOn(String todayPlusTwoOn) {
		this.todayPlusTwoOn = todayPlusTwoOn;
	}

	/**
	 * @return the todayPlusThreeOn
	 */
	public String getTodayPlusThreeOn() {
		return todayPlusThreeOn;
	}

	/**
	 * @param todayPlusThreeOn
	 *            the todayPlusThreeOn to set
	 */
	public void setTodayPlusThreeOn(String todayPlusThreeOn) {
		this.todayPlusThreeOn = todayPlusThreeOn;
	}

	/**
	 * @return the todayPlusFourOn
	 */
	public String getTodayPlusFourOn() {
		return todayPlusFourOn;
	}

	/**
	 * @param todayPlusFourOn
	 *            the todayPlusFourOn to set
	 */
	public void setTodayPlusFourOn(String todayPlusFourOn) {
		this.todayPlusFourOn = todayPlusFourOn;
	}

	/**
	 * @return the todayPlusFiveOn
	 */
	public String getTodayPlusFiveOn() {
		return todayPlusFiveOn;
	}

	/**
	 * @param todayPlusFiveOn
	 *            the todayPlusFiveOn to set
	 */
	public void setTodayPlusFiveOn(String todayPlusFiveOn) {
		this.todayPlusFiveOn = todayPlusFiveOn;
	}

	/**
	 * @return the todayPlusSixOn
	 */
	public String getTodayPlusSixOn() {
		return todayPlusSixOn;
	}

	/**
	 * @param todayPlusSixOn
	 *            the todayPlusSixOn to set
	 */
	public void setTodayPlusSixOn(String todayPlusSixOn) {
		this.todayPlusSixOn = todayPlusSixOn;
	}

	/**
	 * @return the otherDates
	 */
	public String getOtherDates() {
		return otherDates;
	}

	/**
	 * @param otherDates
	 *            the otherDates to set
	 */
	public void setOtherDates(String otherDates) {
		this.otherDates = otherDates;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the flavor
	 */
	public String getFlavor() {
		return flavor;
	}

	/**
	 * @param flavor the flavor to set
	 */
	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}
}
