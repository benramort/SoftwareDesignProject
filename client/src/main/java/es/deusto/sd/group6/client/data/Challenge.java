package es.deusto.sd.group6.client.data;

import java.util.Date;

public record Challenge(
		Long id,
	    String name,
	    Date startDate,
	    Date endDate,
	    boolean isDistance,
	    float goal,
	    Sport sport) {

}
