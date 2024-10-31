package es.deusto.sd.group6.strava.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/prueba")
@Tag(name = "Prueba", description = "Dice hola mundo!!")
public class Prueba {
	
	@Operation(
		summary = "Hola Mundo!!",
		description = "Devuelve un saludo de Hola Mundo!!",
		responses = {
				@ApiResponse(responseCode = "200", description = "Un hola mundo"),
		})
	@GetMapping("")
	public ResponseEntity<String> holaMundo() {
		return ResponseEntity.ok("Hola Mundo!!");
	}

}
