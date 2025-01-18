package es.deusto.sd.group6.client.web;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.deusto.sd.group6.client.data.AccountType;
import es.deusto.sd.group6.client.data.Challenge;
import es.deusto.sd.group6.client.data.Sport;
import es.deusto.sd.group6.client.data.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.deusto.sd.group6.client.data.Sport;
import es.deusto.sd.group6.client.data.TrainingSession;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ClientController {

	@Autowired
	IStravaServiceProxy stravaService;

	private String token = "1737225735002"; 
	
	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		String currentUrl = ServletUriComponentsBuilder.fromRequestUri(request).toUriString();
		model.addAttribute("currentUrl", currentUrl); // Makes current URL available in all templates
		model.addAttribute("token", token); // Makes token available in all templates
	}
	
	@GetMapping("/")
	public String home(Model model) {
		stravaService.createUser(new User("user1", AccountType.FACEBOOK, "password1", "name1", "surname1", new Date(), -1, -1, -1f, -1f));
		System.out.println("Hola");
		Long token = stravaService.login("user1", "password1");
		System.out.println(token);
		stravaService.logout(token);
		return "index";
	}
	
	@GetMapping("/challenges")
	public String getActiveChallenges(
	    @RequestParam(value = "filterSport", required = false) Sport filterSport,
	    @RequestParam(value = "filterDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date filterDate,
	    Model model) {

	    List<Challenge> challenges;

	    try {
	        challenges = stravaService.getActiveChallenges(filterSport, filterDate);
	        model.addAttribute("challenges", challenges);
	        model.addAttribute("filterSport", filterSport);
	        model.addAttribute("filterDate", filterDate);
	        
	        System.out.println("Challenge list: " + challenges.size());
	    } catch (RuntimeException e) {
	        model.addAttribute("errorMessage", "Failed to load challenges: " + e.getMessage());
	    }

	    return "challengeList";
	}

	@GetMapping("/trainingSessions")
    public String showTrainingSessionForm(Model model) {
        return "createTrainingSession";
    }
	
	@PostMapping("/trainingSessions") 
	public String createTrainingSession(
			@RequestParam("title") String title,
			@RequestParam("sport") String sport,
			@RequestParam("startDate") String startDate,
			@RequestParam("distance") float distance,
			@RequestParam("duration") float duration,
			Model model) {

		try {
			Sport sportEnum = Sport.valueOf(sport.toUpperCase());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date startDateParsed = formatter.parse(startDate);
			TrainingSession trainingSession = new TrainingSession(0L, title, sportEnum, startDateParsed, distance, duration);

			stravaService.createTrainingSession(trainingSession, token);
			model.addAttribute("message", "Training session created successfully!");
		} catch (Exception e) {
			model.addAttribute("error", "An error occurred: " + e.getMessage());
		}
		return "createTrainingSession"; 

	}
}
