package es.deusto.sd.group6.strava.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.deusto.sd.group6.strava.entity.User;

@Component
public class GoogleServiceGateway implements ILoginServiceGateway{
	
	private final String API_URL = "http://localhost:8081";
	
	private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GoogleServiceGateway() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public boolean validateUser(String email, String password) {
    	
        String url = API_URL + "/validation?email=" + email + "&password=" + password; //poner desde json

        try {
            // Create the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send the request and obtain the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        	System.out.println(response);
        	// If response is OK, parse the response body
        	if (response.statusCode() == 200) {
                String responseBody = response.body();
                boolean isValid = Boolean.parseBoolean(responseBody); // Modify based on response format
                return isValid;
            } else if(response.statusCode() == 404){
            	throw new RuntimeException("User not found");
            } else {
            	throw new RuntimeException("Unexpected error");
            }
        	
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
