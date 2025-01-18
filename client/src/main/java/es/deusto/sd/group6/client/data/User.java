package es.deusto.sd.group6.client.data;

import java.util.Date;

public record User(
	String email,
    AccountType accountType,
    String password,
    String name,
    String surname,
    Date birthdate,
    int weight,
    int height,
    float maxHeartRate,
    float restHeartRate) {

}
