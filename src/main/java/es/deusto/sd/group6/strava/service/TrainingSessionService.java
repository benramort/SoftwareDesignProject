package es.deusto.sd.group6.strava.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import es.deusto.sd.group6.strava.entity.Sport;
import es.deusto.sd.group6.strava.entity.TrainingSession;
import es.deusto.sd.group6.strava.entity.User;

@Service
public class TrainingSessionService {
	//private User user;
	private UserService userService; //para token
	private List<TrainingSession> lTrainingSession = new ArrayList<TrainingSession>(); //ejemplos
	
	public TrainingSessionService(UserService userService) {
		this.userService = userService;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date1 = sdf.parse("01-01-2000");
			TrainingSession trainingSession = new TrainingSession("ts1", Sport.CYCLING, date1, 0.7f, 11.2f);
			lTrainingSession.add(trainingSession);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void createTrainingSession(long token, String title, Sport sport, Date startDate, float distance, float duration) {
		User user = userService.getUser(token);
		if(user != null) {
			user.createTrainingSession(title, sport, startDate, distance, duration);
		}
	}
	
	public List<TrainingSession> viewRecentTrainingSessions(long token){
		User user = userService.getUser(token);
		if(user != null) {
			List<TrainingSession> sessions = user.getTrainingSessions();
			
			List<TrainingSession> recentSessions = new ArrayList<>();
			

			if(sessions.size() <= 5) {
				recentSessions.addAll(sessions);
				
			}else{
				for (int i=0; i < 5; i++) {
					recentSessions.add(sessions.get(i));
				}
			}
			//return lTrainingSession; //ejemplo
			return recentSessions;
			
		}else {
			throw new RuntimeException("User not found");
		}

	}
	
	
	public List<TrainingSession> viewTrainingSessionsByDate(long token, Date startDate, Date endDate){
		User user = userService.getUser(token);
		if (user != null) {
			List<TrainingSession> sessions = user.getTrainingSessions();	
			List<TrainingSession> filteredSessions = new ArrayList<>();

			for(TrainingSession session : sessions) {
				Date sessionStartDate = session.getStartDate();

				if(sessionStartDate.after(startDate) && sessionStartDate.before(endDate)) {
					filteredSessions.add(session);
				}
			}
			return filteredSessions;
			
		}else {
			throw new RuntimeException("User not found");
		}
	}

}
