package corso.java.webapi.services.loader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import corso.java.webapi.entities.geo.Area;
import corso.java.webapi.entities.geo.City;
import corso.java.webapi.entities.geo.Province;
import corso.java.webapi.entities.geo.Region;

@Service
@Scope("singleton")
public class JsonCitiesLoaderService implements CitiesLoaderService {
	private static final Logger log = LoggerFactory.getLogger(JsonCitiesLoaderService.class);
	private static final String citiesFileName = "static/comuni.json";

	@Override
	public List<City> loadCities() {
		try {
			var resource = new ClassPathResource(citiesFileName);
			var mapper = new ObjectMapper();
			try (var is = resource.getInputStream()) {
				var reader = mapper.readerForArrayOf(JsonCity.class);
				var cities = List.of(reader.readValue(is)).stream().map(c -> (JsonCity) c).distinct().toList();
				return cities.stream() //
						.map(c -> City.builder() //
								.withCadastralCode(c.getCodiceCatastale()) //
								.withId(Integer.parseInt(c.getCodice())) //
								.withName(c.getNome()) //
								.withPeople(c.getPopolazione()) //
								.withProvince( //
										Province.builder() //
												.withAcronym(c.getSigla()) //
												.withId(Integer.parseInt(c.getProvincia().getCodice())) //
												.withName(c.getProvincia().getNome()) //
												.withRegion( //
														Region.builder() //
																.withArea( //
																		Area.builder() //
																				.withId(Integer.parseInt(
																						c.getZona().getCodice())) //
																				.withName(c.getZona().getNome()) //
																				.build() //
																) //
																.withId(Integer.parseInt(c.getRegione().getCodice())) //
																.withName(c.getRegione().getNome()) //
																.build() //
												) //
												.build()) //
								.build())
						.toList();
			}
		} catch (Exception e) {
			log.error("Exception reading from resource file", e);
			throw new RuntimeException("Exception reading from resource file");
		}
	}

}
