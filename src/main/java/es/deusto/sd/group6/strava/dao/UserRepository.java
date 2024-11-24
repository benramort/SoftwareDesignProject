package es.deusto.sd.group6.strava.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.deusto.sd.group6.strava.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByEmail(String email);
}
