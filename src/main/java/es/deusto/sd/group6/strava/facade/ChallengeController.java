package es.deusto.sd.group6.strava.facade;

import java.util.ArrayList;
import java.util.List;

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
import es.deusto.sd.group6.strava.entity.Challenge;
import es.deusto.sd.group6.strava.service.ChallengeService;



@RestController
@RequestMapping("/challenges")
public class ChallengeController {
	private ChallengeService challengeService;

	public ChallengeController(ChallengeService challengeService) {
		super();
		this.challengeService = challengeService;
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

	@PostMapping("/accept/{challengeName}")
	public ResponseEntity<Void> acceptChallenge(@RequestParam("token") long token, @PathVariable String challengeName) {
		try {
			System.out.println(challengeName);
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


}
