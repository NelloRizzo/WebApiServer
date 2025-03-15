package corso.java.webapi.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import corso.java.webapi.entities.geo.Area;
import corso.java.webapi.entities.geo.City;
import corso.java.webapi.entities.geo.Province;
import corso.java.webapi.entities.geo.Region;
import corso.java.webapi.repositories.geo.AreasRepository;
import corso.java.webapi.repositories.geo.CitiesRepository;
import corso.java.webapi.repositories.geo.ProvincesRepository;
import corso.java.webapi.repositories.geo.RegionsRepository;
import corso.java.webapi.services.exceptions.ServiceException;
import corso.java.webapi.services.loader.CitiesLoaderService;

@Service
public class CityServiceImpl implements CityService {
	private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);
	@Autowired
	private CitiesLoaderService loader;
	@Autowired
	private CitiesRepository citiesRepo;
	@Autowired
	private ProvincesRepository provincesRepo;
	@Autowired
	private RegionsRepository regionsRepo;
	@Autowired
	private AreasRepository areasRepo;

	@Override
	public void populate() {
		try {
			var cities = loader.loadCities();
			var provinces = cities.stream().map(City::getProvince).distinct().toList();
			var regions = provinces.stream().map(Province::getRegion).distinct().toList();
			var areas = regions.stream().map(Region::getArea).distinct().toList();
			areasRepo.saveAll(areas);
			regions.stream().map(r -> {
				r.setArea(areasRepo.findById(r.getArea().getId()).orElseThrow());
				return r;
			}).forEach(regionsRepo::save);
			provinces.stream().map(p -> {
				p.setRegion(regionsRepo.findById(p.getRegion().getId()).orElseThrow());
				return p;
			}).forEach(provincesRepo::save);
			cities.stream().map(c -> {
				c.setProvince(provincesRepo.findById(c.getProvince().getId()).orElseThrow());
				return c;
			}).forEach(citiesRepo::save);
		} catch (Exception e) {
			log.error("Exception populating database", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<City> getCities() {
		return citiesRepo.findAll(Sort.by("name"));
	}

	@Override
	public List<City> getCitiesByAcronym(String acronym) {
		return citiesRepo.findAllByProvinceAcronym(acronym, Sort.by("name"));
	}

	@Override
	public List<Province> getProvinces() {
		return provincesRepo.findAll(Sort.by("name"));
	}

	@Override
	public List<Region> getRegions() {
		return regionsRepo.findAll(Sort.by("name"));
	}

	@Override
	public List<Area> getAreas() {
		return areasRepo.findAll();
	}

}
