package es.deusto.sd.group6.strava.dto;

import java.util.Date;

import es.deusto.sd.group6.strava.entity.Sport;

public class ProgressDTO {
	private long id;
	private String name;
	private Date startDate;
	private Date endDate;
	private boolean isDistance;
	private float goal;
	private Sport sport;
	private float progress;

	public ProgressDTO() {}

	public ProgressDTO(long id, String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport,
			float progress) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isDistance = isDistance;
		this.goal = goal;
		this.sport = sport;
		this.progress = progress;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isDistance() {
		return isDistance;
	}

	public void setDistance(boolean isDistance) {
		this.isDistance = isDistance;
	}

	public float getGoal() {
		return goal;
	}

	public void setGoal(float goal) {
		this.goal = goal;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}
}
