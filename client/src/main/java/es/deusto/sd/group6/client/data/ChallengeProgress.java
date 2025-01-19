package es.deusto.sd.group6.client.data;

import java.util.Date;

public record ChallengeProgress(
		Long id,
	    String name,
	    Date startDate,
	    Date endDate,
	    boolean isDistance,
	    float goal,
	    Sport sport,
	    float progress) {

}
