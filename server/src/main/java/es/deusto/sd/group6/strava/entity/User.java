package es.deusto.sd.group6.strava.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private AccountType accountType;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthdate;
	
	@Column(nullable = false)
	private float weight;
	
	@Column(nullable = false)
	private float height;
	
	@Column(nullable = false)
	private float maxHeartRate;
	
	@Column(nullable = false)
	private float restHeartRate;
	
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "challenge_user",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "challenge_id")
    )
	private List<Challenge> acceptedChallenges;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<TrainingSession> lTrainingSessions;

	public User() {}

	public User(String email, AccountType accountType, String name, String surname, Date birthdate, float weight, float height,
			float maxHeartRate, float restHeartRate) {
		this.email = email;
		this.accountType = accountType;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.weight = weight;
		this.height = height;
		this.maxHeartRate = maxHeartRate;
		this.restHeartRate = restHeartRate;
		this.acceptedChallenges = new ArrayList<>();
		this.lTrainingSessions = new ArrayList<>();
	}

	public User(String email, AccountType accountType, String name, String surname, Date birthdate) {
		this.email = email;
		this.accountType = accountType;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.weight = -1;
		this.height = -1;
		this.maxHeartRate = -1;
		this.restHeartRate = -1;
		this.acceptedChallenges = new ArrayList<>();
		this.lTrainingSessions = new ArrayList<>();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getMaxHeartRate() {
		return maxHeartRate;
	}

	public void setMaxHeartRate(float maxHeartRate) {
		this.maxHeartRate = maxHeartRate;
	}

	public float getRestHeartRate() {
		return restHeartRate;
	}

	public void setRestHeartRate(float restHeartRate) {
		this.restHeartRate = restHeartRate;
	}

	public List<Challenge> getAcceptedChallenges() {
		return acceptedChallenges;
	}

	public void addAcceptedChallenge(Challenge challenge) {
		this.acceptedChallenges.add(challenge);
	}

	public List<TrainingSession> getTrainingSessions() {
		return lTrainingSessions;
	}

	public void addTrainingSession(TrainingSession trainingSession) {
		this.lTrainingSessions.add(trainingSession);
		lTrainingSessions.sort(null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}


	public void createTrainingSession(long id, String title, Sport sport, Date startDate, float distance, float duration){
		TrainingSession trainingSession = new TrainingSession(id,title, sport, startDate, distance, duration);
		addTrainingSession(trainingSession);
	}
	public List<Challenge> getActiveChallenges(){
		Date currentDate = new Date();
		List<Challenge> activeChallenges = new ArrayList<>();
		for(Challenge challenge : acceptedChallenges) {
			if(challenge.getStartDate() != null && challenge.getEndDate() != null && challenge.getEndDate().after(currentDate) && challenge.getStartDate().before(currentDate)) {
				activeChallenges.add(challenge);
			}
		}

		return activeChallenges;
	}
	
	public Map<Challenge, Float> getAcceptedChallengesProgress(){
		List<Challenge> activeChallenges = getActiveChallenges();
        Map<Challenge, Float> progress = new HashMap<>();
        for (Challenge challenge : activeChallenges) {
            float completion = 0;
            for (TrainingSession trainingSession : lTrainingSessions) {
                if (trainingSession.getStartDate().after(challenge.getStartDate()) && trainingSession.getStartDate().before(challenge.getEndDate())) {
                    if(trainingSession.getSport() == challenge.getSport()) {
						if (challenge.isDistance()) {
							completion += trainingSession.getDistance();
						} else {
							completion += trainingSession.getDuration();
						}
                    }
                }
            }
			if (completion > challenge.getGoal()) {
				completion = challenge.getGoal();
			}
            progress.put(challenge, completion / challenge.getGoal());
        }
        return progress;
	}

}
