package es.deusto.sd.group6.strava.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.group6.strava.dto.ChallengeDTO;
import es.deusto.sd.group6.strava.dto.ProgressDTO;
import es.deusto.sd.group6.strava.entity.Challenge;
import es.deusto.sd.group6.strava.entity.User;
import es.deusto.sd.group6.strava.service.ChallengeService;
import es.deusto.sd.group6.strava.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/challenges")
public class ChallengeController {
	private ChallengeService challengeService;
	private UserService userService;

	public ChallengeController(ChallengeService challengeService, UserService userService) {
		super();
		this.challengeService = challengeService;
		this.userService = userService;
	}

	@PostMapping("")
	public ResponseEntity<Void> createChallenge(@RequestParam("token") long token, @RequestBody ChallengeDTO challenge){
		try {
			challengeService.createChallenge(challenge.getName(), challenge.getStartDate(), challenge.getEndDate(), challenge.isDistance(), challenge.getGoal(), challenge.getSport(), token);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/active")
	public ResponseEntity<List<ChallengeDTO>> getActiveChallenges(){
		try {
			List<Challenge> activeChallenges = challengeService.getActiveChallenges();
			List<ChallengeDTO> activeChallengesDTO = new ArrayList<ChallengeDTO>();
			for(Challenge activeChallenge: activeChallenges) {
				activeChallengesDTO.add(activeChallenge.toDTO());
			}
			return new ResponseEntity<>(activeChallengesDTO,HttpStatus.OK);
		}catch(RuntimeException e){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/{challengeName}")
	public ResponseEntity<Void> acceptChallenge(@RequestParam("token") long token, @PathVariable("challengeName") String challengeName) {
		try {
			boolean isAccepted = challengeService.acceptChallenge(challengeName, token);
			if (isAccepted) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	@Operation(
			summary = "Get all accepted challenges",
			description = "Get all challenges that have been accepted by the user",
			responses = {
					@ApiResponse(responseCode = "200", description = "OK: List of accepted challenges retrieved successfully"),
					@ApiResponse(responseCode = "204", description = "No Content: No challeges found"),
					@ApiResponse(responseCode = "500", description = "Internal server error"),
					@ApiResponse(responseCode = "403", description = "Unauthorized: User not authenticated")
			}
			)
	@GetMapping("/accepted")
	public ResponseEntity<List<ChallengeDTO>> getAcceptedChallenges(
			@Parameter(name = "token", description = "Authorization token", required = true, example = "1727786726773")
			@RequestParam("token") long token) {

		try {
			User user = userService.getUser(token);

			if (user == null) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			List<Challenge> challenges = challengeService.getAcceptedChallenges(user);

			if (challenges.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			List<ChallengeDTO> dtos = new ArrayList<>();
			challenges.forEach(challenge -> dtos.add(challenge.toDTO()));

			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(
			summary = "Get accepted challenges progress", 
			description = "Get the progress of all accepted challenges", 
			responses = {
					@ApiResponse(responseCode = "200", description = "OK: List of accepted challenges progress retrieved successfully"),
					@ApiResponse(responseCode = "204", description = "No Content: No challeges found"),
					@ApiResponse(responseCode = "403", description = "Unauthorized: User not authenticated"),
					@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/progress")
	public ResponseEntity<List<ProgressDTO>> getAcceptedChallengesProgress(
			@Parameter(name = "token", description = "Authorization token", required = true, example = "1727786726773")
			@RequestParam("token") long token){
		try {
			User user = userService.getUser(token);

			if (user == null) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			Map<Challenge, Float> acceptedChallenges = challengeService.getAcceptedChallengesProgress(user);

			if (acceptedChallenges.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			List<ProgressDTO> dtos = new ArrayList<>();
			acceptedChallenges.forEach((challenge, progress) -> dtos.add(challenge.toProgressDTO(progress)));

			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
