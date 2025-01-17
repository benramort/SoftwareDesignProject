package es.deusto.sd.group6.client.web;

import java.net.http.HttpClient;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceProxy implements IStravaServiceProxy {
	
	private static final String BASE_URL = "http://localhost:8080";
	
	private final ObjectMapper objectMapper;
	private final HttpClient httpClient;
	
	
	public ServiceProxy() {
		objectMapper = new ObjectMapper();
        httpClient = HttpClient.newHttpClient();
	}

}
