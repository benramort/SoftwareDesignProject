package es.deusto.sd.group6.strava.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

public class MailServiceGateway {
	
	@Autowired
	private JavaMailSender emailSender;
    
    @Value("${spring.mail.username}")
    private String sender;
	
	@Async
    public void sendSimpleMessage(String to, String text) {

        SimpleMailMessage message = new SimpleMailMessage(); 
        System.out.println("Sending from: " + sender);
        message.setFrom(sender);
        message.setTo(to); 
        message.setSubject("Testing Message - Spring Boot Email Sender"); 
        message.setText(text);
        emailSender.send(message);
    }

}
