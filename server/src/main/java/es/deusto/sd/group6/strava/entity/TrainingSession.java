package es.deusto.sd.group6.strava.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import es.deusto.sd.group6.strava.dto.TrainingSessionDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class TrainingSession {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Sport sport;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
	
	@Column(nullable = false)
	private float distance;
	
	@Column(nullable = false)
	private float duration;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public TrainingSession() {}
	
	public TrainingSession(User user, String title, Sport sport, Date startDate, float distance,
			float duration) {
		this.title = title;
		this.sport = sport;
		this.startDate = startDate;
		this.distance = distance;
		this.duration = duration;
	}
	
	public TrainingSession(long id, String title, Sport sport, Date startDate, float distance,
			float duration) {
		this.id = id;
		this.title = title;
		this.sport = sport;
		this.startDate = startDate;
		this.distance = distance;
		this.duration = duration;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id,startDate, sport, title);
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
		return Objects.equals(id, other.id);
	}
	
	public TrainingSessionDTO toDTO() {
		return new TrainingSessionDTO(this.getId(), this.getTitle(),this.getSport(),this.getStartDate(),this.getDistance(),this.getDuration());
	}

}
