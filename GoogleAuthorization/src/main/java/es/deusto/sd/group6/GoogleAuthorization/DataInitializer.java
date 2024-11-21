package es.deusto.sd.group6.GoogleAuthorization;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.sd.group6.GoogleAuthorization.dao.UserRepository;
import es.deusto.sd.group6.GoogleAuthorization.entity.User;

@Configuration
public class DataInitializer {
	
	@Bean
	CommandLineRunner init(UserRepository userRepository) { //Que es este return??
		userRepository.deleteAll();
		
		userRepository.save(new User("patricia@patricia.com", "patricia"));
		userRepository.save(new User("benat@benat.com", "benat"));
		userRepository.save(new User("giovanna@giovanna.com", "giovanna"));
		userRepository.save(new User("joana@joana.com", "joana"));
		
		return args -> {
			System.out.println("Data initialized");
		};
	}

}
