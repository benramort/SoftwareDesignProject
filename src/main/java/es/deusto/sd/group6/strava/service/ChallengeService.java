package es.deusto.sd.group6.strava.service;

import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;


import es.deusto.sd.group6.strava.entity.Challenge;
import es.deusto.sd.group6.strava.entity.Sport;
import es.deusto.sd.group6.strava.entity.User;

@Service
public class ChallengeService {

	private AtomicLong idGenerator = new AtomicLong(0);
	
	private Map<Long, Challenge> challenges = new HashMap<>();
	private UserService userService;

	public ChallengeService(UserService userService) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		this.userService = userService;
		try {
			Date startDate1 = sdf.parse("01-05-2023");
			Date endDate1 = sdf.parse("30-05-2023");
			Challenge challenge1 = new Challenge(idGenerator.incrementAndGet(), "Spring Marathon", startDate1, endDate1, true, 42.195f, Sport.RUNNING, null);

			Date startDate2 = sdf.parse("15-06-2023");
			Date endDate2 = sdf.parse("15-07-2023");
			Challenge challenge2 = new Challenge(idGenerator.incrementAndGet(), "Cycling Tour", startDate2, endDate2, true, 100.0f, Sport.CYCLING, null);

			challenges.put(challenge1.getId(), challenge1);
			challenges.put(challenge2.getId(), challenge2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Challenge createChallenge(String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport, long token) {
		User user = userService.getUser(token);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		Challenge challenge = new Challenge(idGenerator.incrementAndGet(), name, startDate, endDate, isDistance, goal, sport, user);
		System.out.println("Creating challenge with id " + challenge.getId() + " and name " + challenge.getName());
		challenges.put(challenge.getId(), challenge);
		return challenge;
	}
	
	public Challenge createChallenge(String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport, User user) {
		Challenge challenge = new Challenge(idGenerator.incrementAndGet(), name, startDate, endDate, isDistance, goal, sport, user);
		challenges.put(challenge.getId(), challenge);
		return challenge;
	}

	public List<Challenge> getActiveChallenges() {
		Date currentDate = new Date();
		List<Challenge> activeChallenges = new ArrayList<>();
		for(Challenge challenge : challenges.values()) {

			if(challenge.getStartDate() != null && challenge.getEndDate() != null && challenge.getEndDate().after(currentDate) && challenge.getStartDate().before(currentDate)) {
				activeChallenges.add(challenge);
			}
		}

		return activeChallenges;
	}

	public boolean acceptChallenge(long id, long token) {
		User user = userService.getUser(token);
		List<Challenge> activeChallenges = getActiveChallenges();
		for (Challenge challenge : activeChallenges) {
			if (challenge.getId() == id) {
				user.addAcceptedChallenge(challenge);
				return true;
			}
		}
		return false;
	}
	
	public boolean acceptChallenge(long id, User user) {
		List<Challenge> activeChallenges = getActiveChallenges();
		for (Challenge challenge : activeChallenges) {
			if ((challenge.getId() == id)) {
				user.addAcceptedChallenge(challenge);
				return true;
			}
		}
		return false;
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


