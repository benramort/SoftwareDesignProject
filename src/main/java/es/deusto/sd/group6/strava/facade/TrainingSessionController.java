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

@RestController
@RequestMapping("/trainingSessions")
public class TrainingSessionController {

	private TrainingSessionService trainingSessionService;

	public TrainingSessionController(TrainingSessionService trainingSessionService) {
		super();
		this.trainingSessionService = trainingSessionService;
	}
//TODO mandar json devuelve ok
	@PostMapping("")
	public ResponseEntity<Void> createTrainingSession(@RequestParam("token") long token, @RequestBody TrainingSessionDTO trainingSession){
		try {

			trainingSessionService.createTrainingSession(token, trainingSession.getTitle(), trainingSession.getSport(), trainingSession.getStartDate(), trainingSession.getDistance(), trainingSession.getDuration());
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<List<TrainingSessionDTO>> viewRecentTrainingSessions(@RequestParam("token") long token){
		try {
			//List<TrainingSessionDTO> recentTrainignSessions = trainingSessionService.viewRecentTrainingSessions(token);
			List<TrainingSessionDTO> recentTrainingSessionsDTO = new ArrayList<TrainingSessionDTO>();
			List<TrainingSession> recentTrainingSessions = trainingSessionService.viewRecentTrainingSessions(token);
			
			/*
			for(TrainingSession session:recentTrainignSessions) {
				TrainingSessionDTO sessionDTO = new TrainingSessionDTO(session.getTitle(),session.getSport(),session.getStartDate(),session.getStartTime(),session.getDistance(),session.getDuration());
				recentTrainignSessionsDTO.add(sessionDTO);
			}*/
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
	public ResponseEntity<List<TrainingSessionDTO>> viewTrainingSessionsByDate(@RequestParam("token") long token, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		try {
			List<TrainingSessionDTO> trainingSessionsByDateDTO = new ArrayList<TrainingSessionDTO>();
			List<TrainingSession> trainingSessionsByDate = trainingSessionService.viewTrainingSessionsByDate(token, startDate, endDate);

			for(TrainingSession session:trainingSessionsByDate) {
				trainingSessionsByDateDTO.add(session.toDTO());	
			}

			return new ResponseEntity<>(trainingSessionsByDateDTO,HttpStatus.OK);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
}
