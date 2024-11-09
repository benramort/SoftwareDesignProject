package es.deusto.sd.group6.strava.service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import es.deusto.sd.group6.strava.entity.AccountType;
import es.deusto.sd.group6.strava.entity.User;

@Service
public class UserService {
	
	private Map<Long, User> activeUsers;
	private Map<String, User> users; //Temporal
	
	public UserService() {
		activeUsers = new HashMap<>();
		users = new TreeMap<>();
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = sdf.parse("01-01-2000");
			User newUser = new User("benat@benat.es", null, "Benat", "Ramirez", date);
			users.put(newUser.getEmail(), newUser);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createUser(String email, AccountType type, String password, String name, String surname, Date birthdate, float weight, float height,
			float maxHeartRate, float restHeartRate) {
		
		if (users.containsKey(email)) { //Temporal
			throw new RuntimeException("Email already in use");
		}
		
		//Falta validar contraseña con google
		
		User newUser = new User(email, null, name, surname, birthdate);
		if (weight >= 0) {
			newUser.setWeight(weight);
		}
		if (height >= 0) {
			newUser.setHeight(height);
		}
		if (maxHeartRate >= 0) {
			newUser.setMaxHeartRate(maxHeartRate);
		}
		if (restHeartRate >= 0) {
			newUser.setRestHeartRate(restHeartRate);
		}
		users.put(newUser.getEmail(), newUser);
	}
	
	public long logIn(String email, String password) { //Una persona puede logearse varias veces
		User user = users.get(email);
		if (user == null) {
			throw new RuntimeException("User not found");
		} else {
			//Falta validar contraseña con google
			long token = System.currentTimeMillis();
			activeUsers.put(token, user);
			return token;
		}
	}
	
	public void LogOut(long token) {
		User loggedOutUser = activeUsers.remove(token);
		if (loggedOutUser == null) {
			throw new RuntimeException("Token not found in active users list");
		}
	}
	
	public User getUser(long token) {
		User user = activeUsers.get(token);
		return user;
	}

}
