package es.deusto.sd.group6.client.data;

import java.util.Date;

public record TrainingSession(
		Long id,
		String title,
		Sport sport,
		Date startDate,
		Float distance,
		Float duration
	) {}