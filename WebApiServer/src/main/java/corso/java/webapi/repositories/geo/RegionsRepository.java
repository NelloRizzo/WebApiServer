package corso.java.webapi.repositories.geo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import corso.java.webapi.entities.geo.Region;

public interface RegionsRepository extends JpaRepository<Region, Integer>, PagingAndSortingRepository<Region, Integer> {
	List<Region> findAll(Sort sort);
}
