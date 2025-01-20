package es.deusto.sd.group6.strava.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceGateway {
	
	private JavaMailSender emailSender;
    
    @Value("${spring.mail.username}")
    private String sender;
    
    public MailServiceGateway(JavaMailSender emailSender) {
    	this.emailSender = emailSender;
    }
	
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
