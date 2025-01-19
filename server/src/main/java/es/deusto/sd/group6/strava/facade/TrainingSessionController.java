package es.deusto.sd.group6.strava.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.group6.strava.dto.TrainingSessionDTO;
import es.deusto.sd.group6.strava.entity.TrainingSession;
import es.deusto.sd.group6.strava.service.TrainingSessionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/trainingSessions")
@Tag(name = "Training Session Controller", description = "Operations to create and manage training sessions")
public class TrainingSessionController {

	private TrainingSessionService trainingSessionService;

	public TrainingSessionController(TrainingSessionService trainingSessionService) {
		super();
		this.trainingSessionService = trainingSessionService;
	}

	@PostMapping("")
	@Operation(summary = "Create training session", description = "Create a new training session",
			responses = {
			@ApiResponse(responseCode = "201", description = "Training session created"),
			@ApiResponse(responseCode = "403", description = "User not found") 
			})
	public ResponseEntity<Void> createTrainingSession(
			@Parameter(name = "token", description = "User session token", required = true)
			@RequestParam("token") long token, 
			@Parameter(name = "Training session data", description = "Data to create a new training session. Required: title, sport, startDate, distance and duration", required = true)
			@RequestBody TrainingSessionDTO trainingSession
            ) {
		try {

			trainingSessionService.createTrainingSession(token, trainingSession.getId(),trainingSession.getTitle(), trainingSession.getSport(), trainingSession.getStartDate(), trainingSession.getDistance(), trainingSession.getDuration());
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println("holasoyelget ");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("")
	@Operation(summary = "View recent training sessions", description = "View the recent 5 most recent training sessions of an user", responses = {
			@ApiResponse(responseCode = "200", description = "List of recent training sessions"),
			@ApiResponse(responseCode = "403", description = "User not found") })
	public ResponseEntity<List<TrainingSessionDTO>> viewRecentTrainingSessions(
			@Parameter(name = "token", description = "User session token", required = true)
			@RequestParam("token") long token){
		try {
			List<TrainingSessionDTO> recentTrainingSessionsDTO = new ArrayList<TrainingSessionDTO>();
			List<TrainingSession> recentTrainingSessions = trainingSessionService.viewRecentTrainingSessions(token);

			for(TrainingSession session:recentTrainingSessions) {
				recentTrainingSessionsDTO.add(session.toDTO());	
			}
			
			return new ResponseEntity<>(recentTrainingSessionsDTO,HttpStatus.OK);
		}catch (RuntimeException e) {
			e.printStackTrace();
			if(e.getMessage().equals("User not found")) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/byDate")
	@Operation(summary = "View training sessions by date", description = "View the training sessions of an user between two dates", responses = {
			@ApiResponse(responseCode = "200", description = "List of training sessions between the dates"),
			@ApiResponse(responseCode = "403", description = "User not found") })
	public ResponseEntity<List<TrainingSessionDTO>> viewTrainingSessionsByDate(
			@Parameter(name = "token", description = "User session token", required = true)
			@RequestParam("token") long token,
			@Parameter(name = "startDate", description = "Start date to filter the training sessions", required = true)
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@Parameter(name = "endDate", description = "End date to filter the training sessions", required = true)
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		try {
			List<TrainingSessionDTO> trainingSessionsByDateDTO = new ArrayList<TrainingSessionDTO>();
			List<TrainingSession> trainingSessionsByDate = trainingSessionService.viewTrainingSessionsByDate(token, startDate, endDate);

			for(TrainingSession session:trainingSessionsByDate) {
				trainingSessionsByDateDTO.add(session.toDTO());	
			}

			return new ResponseEntity<>(trainingSessionsByDateDTO,HttpStatus.OK);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}
