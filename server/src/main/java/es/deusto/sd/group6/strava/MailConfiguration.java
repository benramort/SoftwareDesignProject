package es.deusto.sd.group6.strava;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import es.deusto.sd.group6.strava.external.MailServiceGateway;

@Configuration
public class MailConfiguration {
	
	@Value("${spring.mail.host}")		
	private String host;
	@Value("${spring.mail.port}")		
	private int port;
	@Value("${spring.mail.username}")
    private String sender;
	@Value("${spring.mail.password}")
	private String password;
	
	@Bean
    JavaMailSender getJavaMailSender() {
    	// Configuration programmatically done - automatic Spring mapping does not work
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); 
       
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(sender);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
	
	@Bean
	CommandLineRunner pruebita() {
		return args -> {
			System.out.println("Probando...");
			MailServiceGateway mailServiceGateway = new MailServiceGateway();
			mailServiceGateway.sendSimpleMessage("benat.ramirez@opendeusto.es", "Hola");
		};
		
	}
}
