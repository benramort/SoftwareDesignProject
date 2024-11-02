package es.deusto.sd.group6.strava.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AcceptedChallenge {
	
	private List<TrainingSession> trainingSessions;
	private User user;
	private Challenge challenge;
	private float completion;
	
	public AcceptedChallenge() {}
	
	public AcceptedChallenge(Challenge challenge, User user) {
		this.trainingSessions = new ArrayList<>();
		this.user = user;
		this.challenge = challenge;
        this.completion = 0;
	}

	public List<TrainingSession> getTrainingSessions() {
		return trainingSessions;
	}
	
	public User getUser() {
		return user;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public float getCompletion() {
		return completion;
	}
	
	public void addChallenge(TrainingSession trainingSession) {
		this.trainingSessions.add(trainingSession);
		//Actualizar completion
	}

	@Override
	public int hashCode() {
		return Objects.hash(challenge, completion, trainingSessions, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AcceptedChallenge))
			return false;
		AcceptedChallenge other = (AcceptedChallenge) obj;
		return Objects.equals(challenge, other.challenge)
				&& Float.floatToIntBits(completion) == Float.floatToIntBits(other.completion)
				&& Objects.equals(trainingSessions, other.trainingSessions) && Objects.equals(user, other.user);
	}
	
	
	
	
	

}
