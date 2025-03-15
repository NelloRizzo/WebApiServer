package corso.java.webapi.services;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import corso.java.webapi.entities.geo.Area;
import corso.java.webapi.entities.geo.City;
import corso.java.webapi.entities.geo.Province;
import corso.java.webapi.entities.geo.Region;

public interface CityService {
	@Async
	void populate();
	
	@Async
	List<City> getCities();
	@Async
	List<City> getCitiesByAcronym(String acronym);
	@Async
	List<Province> getProvinces();
	@Async
	List<Region> getRegions();
	@Async
	List<Area> getAreas();
}
