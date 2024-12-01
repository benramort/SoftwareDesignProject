package es.deusto.sd.group6.strava.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.group6.strava.dao.UserRepository;
import es.deusto.sd.group6.strava.entity.AccountType;
import es.deusto.sd.group6.strava.entity.User;
import es.deusto.sd.group6.strava.external.ILoginServiceGateway;
import es.deusto.sd.group6.strava.external.GatewayFactory;

@Service
public class UserService {
	
	private Map<Long, User> activeUsers;
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		activeUsers = new HashMap<>();
		this.userRepository = userRepository;
	}
	
	public void createUser(String email, AccountType type, String password, String name, String surname, Date birthdate, float weight, float height,
			float maxHeartRate, float restHeartRate) {
		
		ILoginServiceGateway loginService = GatewayFactory.getInstance().getGateway(type);
		System.out.println("type" + type);
		if (!loginService.validateUser(email, password)) {
			throw new RuntimeException("Invalid credentials");
		}
		
		User newUser = new User(email, type, name, surname, birthdate);
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
		userRepository.findByEmail(email).ifPresent(u -> {throw new RuntimeException("User already exists");});
		userRepository.save(newUser);
	}
	
	public long logIn(String email, String password) { //Una persona puede logearse varias veces
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		ILoginServiceGateway loginService = GatewayFactory.getInstance().getGateway(user.getAccountType());
		if (!loginService.validateUser(email, password)) {
			throw new RuntimeException("Invalid credentials");
		}
		
		long token = System.currentTimeMillis();
		activeUsers.put(token, user);
		return token;
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
