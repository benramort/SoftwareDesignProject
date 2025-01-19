package es.deusto.sd.group6.strava.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import es.deusto.sd.group6.strava.dto.ChallengeDTO;
import es.deusto.sd.group6.strava.dto.ProgressDTO;
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
public class Challenge {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(nullable = false)
	private boolean isDistance;
	
	@Column(nullable = false)
	private float goal;
	
	@Column(nullable = false)
	private Sport sport;
	
	@ManyToOne
    @JoinColumn(name = "user_email", nullable = false)
	private User creator;

	@ManyToMany(mappedBy = "acceptedChallenges")
	private List<User> acceptedUsers;
	
	public Challenge() {}
	
	public Challenge(long id, String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport, User creator) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isDistance = isDistance;
		this.goal = goal;
		this.sport = sport;
		this.creator = creator;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
        return id;
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
		return Objects.hash(id, creator, endDate, goal, isDistance, name, sport, startDate);
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
		return id == other.id;
	}
	
	public ChallengeDTO toDTO() 
	{
		ChallengeDTO challengeDTO = new ChallengeDTO(this.id, this.getName(),this.getStartDate(),this.getEndDate(),this.isDistance(),this.getGoal(),this.getSport());
		return challengeDTO;
	}
	public ProgressDTO toProgressDTO(float progress) 
	{
		ProgressDTO progressDTO = new ProgressDTO(this.id, this.getName(),this.getStartDate(),this.getEndDate(),this.isDistance(),this.getGoal(),this.getSport(), progress);
		return progressDTO;
	}
	

}
