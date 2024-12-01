package es.deusto.sd.group6.strava.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.group6.strava.dto.LoginDTO;
import es.deusto.sd.group6.strava.dto.UserDTO;
import es.deusto.sd.group6.strava.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Operations to create users, log in and out")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("")
	@Operation(
			summary = "Create user",
			description = "Create a new user",
			responses= {
                @ApiResponse(responseCode = "201", description = "User created"),
                @ApiResponse(responseCode = "409", description = "Email already in use")
            }
	)
	public ResponseEntity<Void> createUser(
			@Parameter(name = "User data", description = "Data to create a new user. Required: email, password, name, surname and birthdate. Optional: weight, height, maxHearthRate and restHearthRate", required = true)
			@RequestBody UserDTO user
			) {
		try {
			userService.createUser(user.getEmail(), user.getAccountType(), user.getPassword(), user.getName(), user.getSurname(), user.getBirthdate(),
					user.getWeight(), user.getHeight(), user.getMaxHeartRate(), user.getRestHeartRate());
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/login")
	@Operation(
			summary = "Log in",
			description = "Logs in a user. Returns a session token to use in other requests", 
			responses = {
					@ApiResponse(responseCode = "200", description = "User logged in"),
					@ApiResponse(responseCode = "404", description = "User not found for the given email")
			}
	)
	public ResponseEntity<Long> createUser(
			@Parameter(name="Login data", description="Email and password to log in", required=true)
			@RequestBody LoginDTO loginData) {
		try {
			long token = userService.logIn(loginData.getEmail(), loginData.getPassword());
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (RuntimeException e) {
			if (e.getMessage().equals("Invalid credentials")) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			if (e.getMessage().equals("User not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Log out",
			description = "Logs out a user",
			responses = {
					@ApiResponse(responseCode = "204", description = "User logged out"),
					@ApiResponse(responseCode = "404", description = "Active session not found for the given token") 
					}
	)
	@PostMapping("/logout")
	public ResponseEntity<Void> deleteUser(
			@Parameter(name="token", description="Session token", required=true, example="123456789")
			@RequestParam("token") long token
			) {
		try {
			userService.LogOut(token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
