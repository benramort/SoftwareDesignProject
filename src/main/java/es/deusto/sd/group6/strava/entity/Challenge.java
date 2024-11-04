package es.deusto.sd.group6.strava.entity;

import java.util.Date;
import java.util.Objects;

import es.deusto.sd.group6.strava.dto.ChallengeDTO;

public class Challenge {
	
	private String name;
	private Date startDate;
	private Date endDate;
	private boolean isDistance;
	private float goal;
	private Sport sport;
	private User creator;
	
	public Challenge() {}
	
	public Challenge(String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport, User creator) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isDistance = isDistance;
		this.goal = goal;
		this.sport = sport;
		this.creator = creator;
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

	public float getDistance() {
		if (isDistance) {
			return goal;
		} else {
			return -1;
		}
	}
	
	public float getTime() {
		if (!isDistance) {
			return goal;
		} else {
			return -1;
		}
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
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creator, endDate, goal, isDistance, name, sport, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Challenge other = (Challenge) obj;
		return Objects.equals(creator, other.creator) && Objects.equals(endDate, other.endDate)
				&& Float.floatToIntBits(goal) == Float.floatToIntBits(other.goal) && isDistance == other.isDistance
				&& Objects.equals(name, other.name) && sport == other.sport
				&& Objects.equals(startDate, other.startDate);
	}
	
	public ChallengeDTO toDTO() 
	{
		ChallengeDTO challengeDTO = new ChallengeDTO(this.getName(),this.getStartDate(),this.getEndDate(),this.isDistance(),this.getGoal(),this.getSport());
		return challengeDTO;
	}
	
	

}
