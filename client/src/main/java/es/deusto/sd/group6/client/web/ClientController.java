package es.deusto.sd.group6.client.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.deusto.sd.group6.client.data.Challenge;
import es.deusto.sd.group6.client.data.Sport;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ClientController {
	
	@Autowired
	IStravaServiceProxy stravaService;
	
	private String token="1737223418228";

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		String currentUrl = ServletUriComponentsBuilder.fromRequestUri(request).toUriString();
		model.addAttribute("currentUrl", currentUrl);
		model.addAttribute("token", token);
	}
	
	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}
	
	@GetMapping("/activeChallenges")
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
	@PostMapping("/challenge")
	public String makeBid(@RequestParam("id") Long id,
			@RequestParam(value = "redirectUrl", required = false) String redirectUrl,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			stravaService.joinChallenge(id, token);
			redirectAttributes.addFlashAttribute("successMessageId", id);
			redirectAttributes.addFlashAttribute("successMessage", "Challenge join!");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("errorMessageId", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to join the challenge: " + e.getMessage());
		}
		
		return "redirect:" + (redirectUrl != null && !redirectUrl.isEmpty() ? redirectUrl : "/");
	}

}
