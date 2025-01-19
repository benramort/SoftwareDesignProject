package es.deusto.sd.group6.strava.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import es.deusto.sd.group6.strava.dao.TrainingSessionRepository;
import es.deusto.sd.group6.strava.dao.UserRepository;
import es.deusto.sd.group6.strava.entity.Sport;
import es.deusto.sd.group6.strava.entity.TrainingSession;
import es.deusto.sd.group6.strava.entity.User;

@Service
public class TrainingSessionService {
	//private User user;
	private AtomicLong idGenerator = new AtomicLong(0);
	private UserService userService; //para token
	private TrainingSessionRepository trainingSessionRepository;
	private UserRepository userRepository;
	
	public TrainingSessionService(UserService userService, TrainingSessionRepository trainingSessionRepository, UserRepository userRepository) {
		this.userService = userService;
		this.trainingSessionRepository = trainingSessionRepository;
		this.userRepository = userRepository;
	}

	
	public void createTrainingSession(long token,long id, String title, Sport sport, Date startDate, float distance, float duration) {
		User user = userService.getUser(token);
		if(user != null) {
			TrainingSession trainingSession = new TrainingSession(idGenerator.incrementAndGet(), title, sport, startDate, distance, duration);
			trainingSessionRepository.save(trainingSession);
			user.getTrainingSessions().add(trainingSession); 
			userRepository.save(user);
		} else {
			throw new RuntimeException("User not found");
		}
	}
	
	public void createTrainingSession(User user,long id, String title, Sport sport, Date startDate, float distance, float duration) {
		if(user != null) {
			TrainingSession trainingSession = new TrainingSession(id, title, sport, startDate, distance, duration);
			trainingSessionRepository.save(trainingSession);
			user.getTrainingSessions().add(trainingSession); 
			userRepository.save(user);
		} else {
			throw new RuntimeException("User not found");
		}
	}
	
	
	public List<TrainingSession> viewRecentTrainingSessions(long token){
		User user = userService.getUser(token);
	    if (user != null) {
	        List<TrainingSession> allSessions = trainingSessionRepository.findAllByOrderByStartDateDesc();
	        
	        List<TrainingSession> userSessions = new ArrayList<>();
	        for (TrainingSession session : allSessions) {
	            if (user.getTrainingSessions().contains(session)) {
	                userSessions.add(session);
	            }
	        }
	        
	        return userSessions.size() <= 5 ? userSessions : userSessions.subList(0, 5);
	    } else {
	        throw new RuntimeException("User not found");
	    }
		
	}
	
	public List<TrainingSession> viewRecentTrainingSessions(User user){
		List<TrainingSession> allSessions = trainingSessionRepository.findAllByOrderByStartDateDesc();
        
        List<TrainingSession> userSessions = new ArrayList<>();
        for (TrainingSession session : allSessions) {
            if (user.getTrainingSessions().contains(session)) {
                userSessions.add(session);
            }
        }
        
        return userSessions.size() <= 5 ? userSessions : userSessions.subList(0, 5);

	}
	
	
	public List<TrainingSession> viewTrainingSessionsByDate(long token, Date startDate, Date endDate){
		User user = userService.getUser(token);
	    if (user != null) {
	    	List<TrainingSession> allSessions = trainingSessionRepository.findAllByOrderByStartDateDesc();

	        List<TrainingSession> filteredSessions = new ArrayList<>();
	        for (TrainingSession session : allSessions) {
	            if (user.getTrainingSessions().contains(session)) {
	                Date sessionStartDate = session.getStartDate();
	                
	                if (sessionStartDate.after(startDate) && sessionStartDate.before(endDate)) {
	                    filteredSessions.add(session);
	                }
	            }
	        }

	        return filteredSessions;
	    } else {
	        throw new RuntimeException("User not found");
	    }
	}
	
	public List<TrainingSession> viewTrainingSessionsByDate(User user, Date startDate, Date endDate){
		List<TrainingSession> allSessions = trainingSessionRepository.findAllByOrderByStartDateDesc();

        List<TrainingSession> filteredSessions = new ArrayList<>();
        for (TrainingSession session : allSessions) {
            if (user.getTrainingSessions().contains(session)) {
                Date sessionStartDate = session.getStartDate();
                
                if (sessionStartDate.after(startDate) && sessionStartDate.before(endDate)) {
                    filteredSessions.add(session);
                }
            }
        }

        return filteredSessions;
	}

}
