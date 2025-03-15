package corso.java.webapi.repositories.geo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import corso.java.webapi.entities.geo.City;

public interface CitiesRepository extends JpaRepository<City, Integer>, PagingAndSortingRepository<City, Integer> {

	List<City> findAll(Sort sort);

	List<City> findAllByProvinceAcronym(String acronym, Sort sort);
}
