package es.deusto.sd.group6.strava.dao;

import es.deusto.sd.group6.strava.entity.Challenge;
import es.deusto.sd.group6.strava.entity.Sport;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long>{
	List<Challenge> findByStartDateBeforeAndEndDateAfter(Date currentDate, Date currentDate2);
    List<Challenge> findByStartDateBeforeAndEndDateAfterAndSport(Date currentDate, Date currentDate2, Sport sport);
}
