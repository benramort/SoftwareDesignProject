package es.deusto.sd.group6.strava.service;

import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import es.deusto.sd.group6.strava.dto.ChallengeDTO;
import es.deusto.sd.group6.strava.entity.AcceptedChallenge;
import es.deusto.sd.group6.strava.entity.Challenge;
import es.deusto.sd.group6.strava.entity.Sport;
import es.deusto.sd.group6.strava.entity.TrainingSession;
import es.deusto.sd.group6.strava.entity.User;

@Service
public class ChallengeService {

	private List<Challenge> challenges = new ArrayList<>();

    public ChallengeService() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

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
	
	public Challenge createChallenge(String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport, User creator) {
		Challenge challenge = new Challenge(name, startDate, endDate, isDistance, goal, sport, creator);
		challenges.add(challenge);
		return challenge;
	}

	public List<Challenge> getActiveChallenges() {
		Date currentDate = new Date();
		List<Challenge> activeChallenges = new ArrayList<>();
		for(Challenge challenge : challenges) {
			if(challenge.getEndDate() != null && challenge.getEndDate().after(currentDate)) {
				activeChallenges.add(challenge);
				System.out.println(challenge);
			}
		}

		return activeChallenges;
	}

	public boolean acceptChallenge(String challengeName, List<TrainingSession> trainingSessions, User user) {
	    for (Challenge challenge : challenges) {
	        if (challenge.getName().equalsIgnoreCase(challengeName)) {
	            Date currentDate = new Date();
	            if (challenge.getEndDate() != null && challenge.getEndDate().after(currentDate)) {
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
	            }
	        }
	    }
	    return false;
	}



}
