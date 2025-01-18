package es.deusto.sd.group6.client.web;

import java.util.Date;
import java.util.List;

import es.deusto.sd.group6.client.data.Challenge;
import es.deusto.sd.group6.client.data.Sport;
import es.deusto.sd.group6.client.data.User;
import es.deusto.sd.group6.client.data.TrainingSession;

public interface IStravaServiceProxy {
	
	public void createUser(User user);
	public Long login(String email, String password);
	public void logout(long token);
	List<Challenge> getActiveChallenges(Sport filterSport, Date filterDate);
	void joinChallenge(long id, String token);
	void createTrainingSession(TrainingSession trainingSession, String token);

}
