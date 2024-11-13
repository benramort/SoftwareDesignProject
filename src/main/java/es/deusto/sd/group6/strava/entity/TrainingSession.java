package es.deusto.sd.group6.strava.entity;

import java.util.Date;
import java.util.Objects;

import es.deusto.sd.group6.strava.dto.TrainingSessionDTO;

public class TrainingSession implements Comparable<TrainingSession> {
	
	private String title;
	private Sport sport;
	private Date startDate;
	private float distance;
	private float duration;
	
	public TrainingSession() {}
	
	public TrainingSession(String title, Sport sport, Date startDate, float distance,
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
	
	
	//TODO no por fecha, ordenar en el database
	@Override
	public int hashCode() {
		return Objects.hash(startDate, sport, title);
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
		return Objects.equals(startDate, other.startDate);
	}
	
	public TrainingSessionDTO toDTO() {
		return new TrainingSessionDTO(this.getTitle(),this.getSport(),this.getStartDate(),this.getDistance(),this.getDuration());
	}

	@Override
	public int compareTo(TrainingSession o) {
		return o.startDate.compareTo(this.getStartDate());
	}

}
