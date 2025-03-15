package corso.java.webapi.services.loader;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import corso.java.webapi.entities.geo.City;

public interface CitiesLoaderService {
	@Async
	List<City> loadCities();
}
