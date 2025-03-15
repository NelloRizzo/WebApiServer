package corso.java.webapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import corso.java.webapi.controllers.responses.ApiError;
import corso.java.webapi.controllers.responses.ApplicationResponse;
import corso.java.webapi.entities.geo.Area;
import corso.java.webapi.entities.geo.City;
import corso.java.webapi.entities.geo.Province;
import corso.java.webapi.entities.geo.Region;
import corso.java.webapi.services.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {
	@Autowired
	CityService service;

	@Operation(summary = "Popola il database delle città", description = "Andrebbe richiamato dopo il primo avvio dell'applicazione.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Il database è stato popolato."),
			@ApiResponse(responseCode = "400", description = "Si è verificato un errore durante il popolamento.") })
	@GetMapping("populate")
	public ResponseEntity<ApplicationResponse<String>> populateCities() {
		try {
			service.populate();
			return ResponseEntity.ok(new ApplicationResponse<String>("Fatto"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApplicationResponse<String>(new ApiError(e.getMessage())));
		}
	}

	@Operation(summary = "Recupera tutte le città")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "L'elenco delle città è disponibile nell'attributo 'data'."),
			@ApiResponse(responseCode = "400", description = "Si è verificato un errore durante il recupero dei dati.") })
	@GetMapping
	public ResponseEntity<ApplicationResponse<List<City>>> getCities() {
		try {
			var cities = service.getCities();
			return ResponseEntity.ok(new ApplicationResponse<List<City>>(cities));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApplicationResponse<List<City>>(new ApiError(e.getMessage())));
		}
	}

	@Operation(summary = "Recupera tutte le città in una determinata provincia", parameters = @Parameter(name = "acronym", description = "La sigla della provincia."))
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "L'elenco delle città è disponibile nell'attributo 'data'."),
			@ApiResponse(responseCode = "400", description = "Si è verificato un errore durante il recupero dei dati.") })
	@GetMapping("by-prov/{acronym}")
	public ResponseEntity<ApplicationResponse<List<City>>> getCitiesByAcronym(@PathVariable String acronym) {
		try {
			var cities = service.getCitiesByAcronym(acronym);
			return ResponseEntity.ok(new ApplicationResponse<List<City>>(cities));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApplicationResponse<List<City>>(new ApiError(e.getMessage())));
		}
	}

	@Operation(summary = "Recupera tutte le province.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "L'elenco delle province è disponibile nell'attributo 'data'."),
			@ApiResponse(responseCode = "400", description = "Si è verificato un errore durante il recupero dei dati.") })
	@GetMapping("provinces")
	public ResponseEntity<ApplicationResponse<List<Province>>> getProvinces() {
		try {
			var provinces = service.getProvinces();
			return ResponseEntity.ok(new ApplicationResponse<List<Province>>(provinces));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApplicationResponse<List<Province>>(new ApiError(e.getMessage())));
		}
	}

	@Operation(summary = "Recupera tutte le regioni.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "L'elenco delle regioni è disponibile nell'attributo 'data'."),
			@ApiResponse(responseCode = "400", description = "Si è verificato un errore durante il recupero dei dati.") })
	@GetMapping("regions")
	public ResponseEntity<ApplicationResponse<List<Region>>> getRegions() {
		try {
			var regions = service.getRegions();
			return ResponseEntity.ok(new ApplicationResponse<List<Region>>(regions));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApplicationResponse<List<Region>>(new ApiError(e.getMessage())));
		}
	}

	@Operation(summary = "Recupera tutte le aree geografiche.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "L'elenco delle aree geografiche è disponibile nell'attributo 'data'."),
			@ApiResponse(responseCode = "400", description = "Si è verificato un errore durante il recupero dei dati.") })
	@GetMapping("areas")
	public ResponseEntity<ApplicationResponse<List<Area>>> getAreas() {
		try {
			var areas = service.getAreas();
			return ResponseEntity.ok(new ApplicationResponse<List<Area>>(areas));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApplicationResponse<List<Area>>(new ApiError(e.getMessage())));
		}
	}
}
