package es.deusto.sd.group6.strava.entity;

import java.util.Date;
import java.util.Objects;

public class TrainingSession {
	
	private String title;
	private Sport sport;
	private Date date;
	private String startTime; //Igual se puede quitar y utilizar el tiempo de startDate
	private float distance;
	private float duration;
	
	public TrainingSession() {}
	
	public TrainingSession(String title, Sport sport, Date startDate, String startTime, float distance,
			float duration) {
		this.title = title;
		this.sport = sport;
		this.date = startDate;
		this.startTime = startTime;
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
		return date;
	}

	public void setStartDate(Date startDate) {
		this.date = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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

	@Override
	public int hashCode() {
		return Objects.hash(date, sport, startTime, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainingSession other = (TrainingSession) obj;
		return Objects.equals(date, other.date) && sport == other.sport && Objects.equals(startTime, other.startTime)
				&& Objects.equals(title, other.title);
	}
	
	

}
