package es.deusto.sd.group6.strava.service;

import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import es.deusto.sd.group6.strava.dao.ChallengeRepository;
import es.deusto.sd.group6.strava.dao.UserRepository;
import es.deusto.sd.group6.strava.entity.Challenge;
import es.deusto.sd.group6.strava.entity.Sport;
import es.deusto.sd.group6.strava.entity.User;

@Service
public class ChallengeService {

	private AtomicLong idGenerator = new AtomicLong(0);
	private ChallengeRepository challengeRepository;
	private UserRepository userRepository;
	private UserService userService;

	public ChallengeService(UserService userService, ChallengeRepository challengeRepository, UserRepository userRepository) {
		this.userService = userService;
		this.challengeRepository = challengeRepository;
		this.userRepository = userRepository;
	}

	public Challenge createChallenge(String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport, long token) {
		User user = userService.getUser(token);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		Challenge challenge = new Challenge(idGenerator.incrementAndGet(), name, startDate, endDate, isDistance, goal, sport, user);
		challengeRepository.save(challenge);
		return challenge;
	}
	
	public Challenge createChallenge(String name, Date startDate, Date endDate, boolean isDistance, float goal, Sport sport, User user) {
		Challenge challenge = new Challenge(idGenerator.incrementAndGet(), name, startDate, endDate, isDistance, goal, sport, user);
		challengeRepository.save(challenge);
		return challenge;
	}

	public List<Challenge> getActiveChallenges(Date filterDate, Sport filterSport) {
		Date currentDate = new Date();

        if (filterDate == null && (filterSport == null || filterSport == Sport.ANY)) {
            return challengeRepository.findByStartDateBeforeAndEndDateAfter(currentDate, currentDate);
        } else if (filterDate == null) {
            return challengeRepository.findByStartDateBeforeAndEndDateAfterAndSport(currentDate, currentDate, filterSport);
        } else if (filterSport == null || filterSport == Sport.ANY) {
            return challengeRepository.findByStartDateBeforeAndEndDateAfter(filterDate, filterDate);
        } else {
            return challengeRepository.findByStartDateBeforeAndEndDateAfterAndSport(filterDate, filterDate, filterSport);
        }
	}
	/*public List<Challenge> getActiveChallenges() {
		Date currentDate = new Date();
		List<Challenge> activeChallenges = new ArrayList<>();
		for(Challenge challenge : challenges.values()) {
			if(challenge.getStartDate() != null && challenge.getEndDate() != null && challenge.getEndDate().after(currentDate) && challenge.getStartDate().before(currentDate)) {
				activeChallenges.add(challenge);
			}
		}

		return activeChallenges;
	}*/

	public boolean acceptChallenge(long id, long token) {
		User user = userService.getUser(token);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Challenge challenge = challengeRepository.findById(id).orElse(null);
        if (challenge != null && challenge.getStartDate().before(new Date()) && challenge.getEndDate().after(new Date())) {
            user.addAcceptedChallenge(challenge);
            userRepository.save(user);
            return true;
        }
        return false;
	}
	
	public boolean acceptChallenge(long id, User user) {

        Challenge challenge = challengeRepository.findById(id).orElse(null);
        if (challenge != null && challenge.getStartDate().before(new Date()) && challenge.getEndDate().after(new Date())) {
            user.addAcceptedChallenge(challenge);
            userRepository.save(user);
            return true;
        }
        return false;
	}
	
	public List<Challenge> getAcceptedChallenges(long token) {
		User user = userService.getUser(token);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		List<Challenge> challenges = user.getAcceptedChallenges();
		
		if (challenges == null) {
			throw new RuntimeException("No challenges found");
		}

		return challenges;
	}
	public Map<Challenge, Float> getAcceptedChallengesProgress(long token) {
		User user = userService.getUser(token);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		Map<Challenge, Float> challengeProgress = user.getAcceptedChallengesProgress();
		if (challengeProgress == null) {
			throw new RuntimeException("No challenges found");
		}
		

		return challengeProgress;
	}
}


