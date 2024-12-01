package es.deusto.sd.group6.strava.external;

import java.util.List;
import java.util.Optional;
import es.deusto.sd.group6.strava.entity.User;

public interface ILoginServiceGateway {
	public boolean validateUser(String email, String password);
}
