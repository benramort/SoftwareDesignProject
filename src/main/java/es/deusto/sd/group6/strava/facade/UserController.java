package es.deusto.sd.group6.strava.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.group6.strava.dto.LoginDTO;
import es.deusto.sd.group6.strava.dto.UserDTO;
import es.deusto.sd.group6.strava.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("")
	public ResponseEntity<Void> createUser(@RequestBody UserDTO user) {
		try {
			userService.createUser(user.getEmail(), user.getAccountType(), user.getPassword(), user.getName(), user.getSurname(), user.getBirthdate(),
					user.getWeight(), user.getHeight(), user.getMaxHeartRate(), user.getRestHeartRate());
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/activeUsers")
	public ResponseEntity<Long> createUser(@RequestBody LoginDTO loginData) {
		try {
			long token = userService.logIn(loginData.getEmail(), loginData.getPassword());
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/activeUsers")
	public ResponseEntity<Void> deleteUser(@RequestParam("token") long token) {
		try {
			userService.LogOut(token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
