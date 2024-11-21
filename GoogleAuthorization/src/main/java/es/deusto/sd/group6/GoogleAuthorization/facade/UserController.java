package es.deusto.sd.group6.GoogleAuthorization.facade;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.group6.GoogleAuthorization.entity.User;
import es.deusto.sd.group6.GoogleAuthorization.service.UserService;

@RestController("")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/validation")
	public ResponseEntity<Boolean> validateUser(@RequestParam("email") String email, @RequestParam("password") String password) {
		try {
			return new ResponseEntity<>(userService.validateUser(email, password), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}	
	}
	
	@GetMapping("/user")
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
}
