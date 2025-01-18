package es.deusto.sd.group6.client.web;

import java.util.Date;
import java.util.List;

import es.deusto.sd.group6.client.data.Challenge;
import es.deusto.sd.group6.client.data.Sport;

public interface IStravaServiceProxy {
	
	List<Challenge> getActiveChallenges(Sport filterSport, Date filterDate);

}
