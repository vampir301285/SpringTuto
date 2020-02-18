package com.redcrystal.example.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redcrystal.example.model.Event;

@ManagedBean
@Controller
@Scope(value = "view")
public class EventsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7705652921585037509L;

	private List<Event> events;

	@PostConstruct
	public void init() {
		events = new ArrayList<Event>();
		for (int i = 0; i < 50; i++) {
			Event e = new Event();
			e.setName("20 Jahre HISS");
			e.setTodayOn("20:00");
			e.setTodayPlusFourOn("19:00");
			e.setOtherDates("Sat 18.04.2015 20:00");
			e.setPlace("Jazzhaus");
			events.add(e);

			e = new Event();
			e.setName("39. traditioneller Freiburger Brettlemarkt November 2015");
			e.setOtherDates("Sat 21.11.2015 14:00");
			e.setPlace("Messe Freiburg");
			events.add(e);

			events.add(new Event("3 Türken & ein Baby", null, null, null, "14:35", "14:35", null, null, null, "Cinemaxx", null));
			events.add(new Event("300 Worte Deutsch", "21:00", "21:00", "21:00", "21:00", null, "21:00", "21:00", null, "Harmonie Kino", null));
			events.add(new Event("300 Worte Deutsch", null, null, "21:00", "21:00", null, null, "21:00", null, "Harmonie Kino", "OMU"));
			events.add(new Event("Baymax - Riesiges Robowabohu", null, "12:20<br/>14:35", "21:00", "21:00", null, null, "21:00", null, "Cinemaxx",
					"3D"));
			events.add(new Event("Birdman oder Die unverhoffte Macht der Ahnungslosigkeit", "16:20<br/>18:50<br/>21:15", "16:20<br/>18:50<br/>21:15",
					"16:20<br/>18:50<br/>21:15", "16:20<br/>18:50<br/>21:15", "16:20<br/>18:50<br/>21:15", "16:20<br/>18:50<br/>21:15", "16:20",
					null, "Friedrichsbau", null));
			events.add(new Event("Fifty Shades of Grey", null, "23:00", null, null, "20:15", null, "21:00", null, "Cinemaxx", "OV"));
			events.add(new Event("Anderswo", "19:30", "14:45<br/>19:30", "14:45<br/>19:30", "14:45<br/>19:30", "19:30", "19:30", "19:30", null,
					"Friedrichsbau", null));
		}
	}
	/**
	 * @return the events
	 */
	public List<Event> getEvents() {
		return events;
	}

}
