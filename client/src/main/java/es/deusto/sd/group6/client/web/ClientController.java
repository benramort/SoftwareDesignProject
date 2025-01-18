package es.deusto.sd.group6.client.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.deusto.sd.group6.client.data.AccountType;
import es.deusto.sd.group6.client.data.Challenge;
import es.deusto.sd.group6.client.data.Sport;
import es.deusto.sd.group6.client.data.User;

@Controller
public class ClientController {
	
	@Autowired
	IStravaServiceProxy stravaService;
	
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

}
