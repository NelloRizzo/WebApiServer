package corso.java.webapi.repositories.geo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import corso.java.webapi.entities.geo.Province;

public interface ProvincesRepository
		extends JpaRepository<Province, Integer>, PagingAndSortingRepository<Province, Integer> {

	List<Province> findAll(Sort sort);
}
