package es.deusto.sd.group6.strava.dto;

import java.util.Date;

import es.deusto.sd.group6.strava.entity.AccountType;

public class UserDTO {
	
	private String email;
	private AccountType accountType;
	private String password;
	private String name;
	private String surname;
	private Date birthdate;
	private float weight;
	private float height;
	private float maxHeartRate;
	private float restHeartRate;
	
	public UserDTO() {}
	
	public UserDTO(String email, AccountType accountType, String password, String name, String surname, Date birthdate, float weight, float height,
			float maxHeartRate, float restHeartRate) {
		this.email = email;
		this.accountType = accountType;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.weight = weight;
		this.height = height;
		this.maxHeartRate = maxHeartRate;
		this.restHeartRate = restHeartRate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getMaxHeartRate() {
		return maxHeartRate;
	}

	public void setMaxHeartRate(float maxHeartRate) {
		this.maxHeartRate = maxHeartRate;
	}

	public float getRestHeartRate() {
		return restHeartRate;
	}

	public void setRestHeartRate(float restHeartRate) {
		this.restHeartRate = restHeartRate;
	}
	
	

}
