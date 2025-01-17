package es.deusto.sd.group6.client.web;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceProxy implements IStravaServiceProxy {
	
	RestTemplate restTemplate;
	
	
	public ServiceProxy(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
