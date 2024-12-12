package es.deusto.sd.group6.client.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {
	
	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}
	
}
