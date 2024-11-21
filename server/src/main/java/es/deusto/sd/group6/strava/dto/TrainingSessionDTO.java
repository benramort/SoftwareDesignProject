package es.deusto.sd.group6.strava.dto;

import java.util.Date;

import es.deusto.sd.group6.strava.entity.Sport;

public class TrainingSessionDTO {

	
	private String title;
	private Sport sport;
	private Date startDate;
	private float distance;
	private float duration;
	
	public TrainingSessionDTO() {}
	
	public TrainingSessionDTO(String title, Sport sport, Date startDate, float distance,
			float duration) {
		this.title = title;
		this.sport = sport;
		this.startDate = startDate;
		this.distance = distance;
		this.duration = duration;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}
}
