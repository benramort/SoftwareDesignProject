package es.deusto.sd.group6.strava.service;

import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;


import es.deusto.sd.group6.strava.entity.Challenge;
import es.deusto.sd.group6.strava.entity.Sport;
import es.deusto.sd.group6.strava.entity.User;

@Service
public class ChallengeService {

	private List<Challenge> challenges = new ArrayList<>();
	private UserService userService;

	public ChallengeService(UserService userService) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		this.userService = userService;
		try {
			Date startDate1 = sdf.parse("01-05-2023");
			Date endDate1 = sdf.parse("30-05-2023");
			Challenge challenge1 = new Challenge("Spring Marathon", startDate1, endDate1, true, 42.195f, Sport.RUNNING, null);

			Date startDate2 = sdf.parse("15-06-2023");
			Date endDate2 = sdf.parse("15-07-2023");
			Challenge challenge2 = new Challenge("Cycling Tour", startDate2, endDate2, true, 100.0f, Sport.CYCLING, null);

			challenges.add(challenge1);
			challenges.add(challenge2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Challenge createChallenge(String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport, long token) {
		User user = userService.getUser(token);
		Challenge challenge = new Challenge(name, startDate, endDate, isDistance, goal, sport, user);
		challenges.add(challenge);
		return challenge;
	}

	public List<Challenge> getActiveChallenges() {
		Date currentDate = new Date();
		List<Challenge> activeChallenges = new ArrayList<>();
		for(Challenge challenge : challenges) {

			if(challenge.getStartDate() != null && challenge.getEndDate() != null && challenge.getEndDate().after(currentDate) && challenge.getStartDate().before(currentDate)) {
				activeChallenges.add(challenge);
			}
		}

		return activeChallenges;
	}

	public boolean acceptChallenge(String challengeName, long token) {
		User user = userService.getUser(token);
		List<Challenge> activeChallenges = getActiveChallenges();
		for (Challenge challenge : activeChallenges) {
			if (challenge.getName().equalsIgnoreCase(challengeName)) {
				user.addAcceptedChallenge(challenge);
				return true;
			}
		}
		return false;
		/*
		List<TrainingSession> trainingSessions = user.getTrainingSessions();
		for (Challenge challenge : activeChallenges) {
			if (challenge.getName().equalsIgnoreCase(challengeName)) {
				Date currentDate = new Date();
				AcceptedChallenge acceptedChallenge = new AcceptedChallenge(challenge, user);
				for (TrainingSession trainingSession : trainingSessions) {
					if (trainingSession.getStartDate().after(challenge.getStartDate()) && trainingSession.getStartDate().before(challenge.getEndDate())) {
						acceptedChallenge.addChallenge(trainingSession);
					}
				}
				user.addAcceptedChallenge(acceptedChallenge);
				return true;

			} else {
				return false;
			}*/
	}
	public List<Challenge> getAcceptedChallenges(User user) {
		List<Challenge> challenges = user.getAcceptedChallenges();

		if (challenges == null) {
			throw new RuntimeException("No challenges found");
		}

		return challenges;
	}
	public Map<Challenge, Float> getAcceptedChallengesProgress(User user) {
		Map<Challenge, Float> challengeProgress = user.getAcceptedChallengesProgress();
		if (challengeProgress == null) {
			throw new RuntimeException("No challenges found");
		}

		return challengeProgress;
	}
}


