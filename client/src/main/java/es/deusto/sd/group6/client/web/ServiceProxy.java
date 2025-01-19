package es.deusto.sd.group6.client.web;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import es.deusto.sd.group6.client.data.Challenge;
import es.deusto.sd.group6.client.data.LoginDTO;
import es.deusto.sd.group6.client.data.Sport;
import es.deusto.sd.group6.client.data.User;

import es.deusto.sd.group6.client.data.TrainingSession;

@Service
public class ServiceProxy implements IStravaServiceProxy {

	RestTemplate restTemplate;

	@Value("${api.base.url}")
	private String apiBaseUrl;

	public ServiceProxy(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
    @Override
    public List<Challenge> getActiveChallenges(Sport filterSport, Date filterDate) {
        StringBuilder url = new StringBuilder(apiBaseUrl + "/challenges/active?");
        if (filterSport != null) {
            url.append("filterSport=").append(filterSport.name()).append("&");
        }
        if (filterDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            url.append("filterDate=").append(dateFormat.format(filterDate));
        }
        try {
        	return restTemplate.exchange(
                    url.toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Challenge>>() {}
                ).getBody();
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 404 -> throw new RuntimeException("No challenges found.");
                default -> throw new RuntimeException("Failed to retrieve challenges: " + e.getStatusText());
            }
        }
    }
    
    @Override
    public void joinChallenge(long id, long token){
    	String url = apiBaseUrl + "/challenges/" + id + "?token=" +  token;
    	try {
            restTemplate.postForObject(url, null, Void.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 403 -> throw new RuntimeException("User not authenticated");
                case 409 -> throw new RuntimeException("Challenge already accepted");
                case 200 -> { /* Successful */ }
                default -> throw new RuntimeException("Failed with status code: " + e.getStatusCode());
            }
        }

    }

	@Override
	public void createUser(User user) {
		try {
			restTemplate.postForObject(apiBaseUrl + "/users", user, Void.class);
		} catch (HttpStatusCodeException e) {
			switch (e.getStatusCode().value()) {
			case 409 -> throw new RuntimeException("User already exists");
			case 403 -> throw new RuntimeException("Invalid credentials");
			default -> throw new RuntimeException("Failed to create user: " + e.getStatusText());
			}
		}
	}

	@Override
	public Long login(String email, String password) {
		LoginDTO login = new LoginDTO(email, password);
		try {
			Long token = restTemplate.postForObject(apiBaseUrl + "/users/login", login, Long.class);
			return token;
		} catch (HttpStatusCodeException e) {
			switch (e.getStatusCode().value()) {
			case 403 -> throw new RuntimeException("Invalid credentials");
			case 404 -> throw new RuntimeException("User not found");
			default -> throw new RuntimeException("Failed to login: " + e.getStatusText());
			}
		}
	}

	@Override
	public void logout(long token) {
		try {
			restTemplate.postForObject(apiBaseUrl + "/users/logout?token=" + token, null, Void.class);
		} catch (HttpStatusCodeException e) {
			switch (e.getStatusCode().value()) {
			case 404 -> throw new RuntimeException("User not found");
			default -> throw new RuntimeException("Failed to log out: " + e.getStatusText());
			}
		}
	}

	@Override
	public 	void createTrainingSession(TrainingSession trainingSession, long token) {

		String url = apiBaseUrl + "/trainingSessions?token=" + token;

		try {
			restTemplate.postForObject(url, trainingSession, Void.class);
		} catch (HttpStatusCodeException e) {
			switch (e.getStatusCode().value()) {
			case 403 -> throw new RuntimeException("User not found");

			//
			default -> throw new RuntimeException("Create training session failed with status code: " + e.getStatusCode());
			}
		}
	}
	
	@Override
	public List<TrainingSession> getTrainingSessions(String token){
		String url = apiBaseUrl + "/trainingSessions?token=" + token;
		try {
			return restTemplate.exchange(
		            url,
		            HttpMethod.GET,
		            null,
		            new ParameterizedTypeReference<List<TrainingSession>>() {}
		        ).getBody();
		} catch (HttpStatusCodeException e) {
			switch (e.getStatusCode().value()) {
			case 403 -> throw new RuntimeException("User not found");
			default -> throw new RuntimeException("Failed to retrieve training sessions: " + e.getStatusText());
			}
		}
	}
	
	@Override
	public List<TrainingSession> getTrainingSessionsByDate(String token, Date startDate, Date endDate)
	{
		String url = apiBaseUrl + "/trainingSessions?token=" + token+"&startDate="+startDate+"&endDate="+endDate;
		try {
			return restTemplate.exchange(
		            url,
		            HttpMethod.GET,
		            null,
		            new ParameterizedTypeReference<List<TrainingSession>>() {}
		        ).getBody();
		} catch (HttpStatusCodeException e) {
			switch (e.getStatusCode().value()) {
			case 403 -> throw new RuntimeException("User not found");
			default -> throw new RuntimeException("Failed to retrieve training sessions: " + e.getStatusText());
			}
		}
	}
}

