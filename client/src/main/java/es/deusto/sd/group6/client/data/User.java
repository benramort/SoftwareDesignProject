package es.deusto.sd.group6.client.data;

import java.util.Date;

public record User(
	String email,
    AccountType accountType,
    String password,
    String name,
    String surname,
    Date birthdate,
    Integer weight,
    Integer heigh,
    Float maxHeartRate,
    Float minHeartRate
    ) {
}


