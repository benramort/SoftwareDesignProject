package es.deusto.sd.group6.GoogleAuthorization.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.deusto.sd.group6.GoogleAuthorization.dao.UserRepository;
import es.deusto.sd.group6.GoogleAuthorization.entity.User;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	public boolean validateUser(String email, String password) {
		Optional<User> user = userRepository.findByEmail(email);
		user.orElseThrow(() -> new RuntimeException("User not found"));
		return user.get().getPassword().equals(password);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}
	

}
