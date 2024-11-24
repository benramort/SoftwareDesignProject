package es.deusto.sd.group6.strava.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.deusto.sd.group6.strava.entity.TrainingSession;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long>{
	Optional<TrainingSession> findByStartDate(Date startDate);
	List<TrainingSession> findAllByOrderByStartDateDesc();
}
