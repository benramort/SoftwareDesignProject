package es.deusto.sd.group6.strava.external;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class GoogleServiceGateway implements ILoginServiceGateway{
	
	private String serverIP;
	private int serverPort;
	private final HttpClient httpClient;

    public GoogleServiceGateway() {
        this.httpClient = HttpClient.newHttpClient();
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config/googleService.properties")) {
        	properties.load(fis);
        	serverIP = properties.getProperty("serverIP");
        	serverPort = Integer.parseInt(properties.getProperty("serverPort"));
		} catch (IOException e) {
			System.err.println("Error reading Google Service config file. Using default values");
			serverIP = "127.0.0.1";
			serverPort = 8081;
		}
    }
    
    @Override
    public boolean validateUser(String email, String password) {
    	
        String url = serverIP + ":"+serverPort + "/validation?email=" + email + "&password=" + password; //poner desde json

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
