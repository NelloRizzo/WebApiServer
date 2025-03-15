package corso.java.webapi.repositories.geo;



import org.springframework.data.jpa.repository.JpaRepository;

import corso.java.webapi.entities.geo.Area;

public interface AreasRepository extends JpaRepository<Area, Integer> {

}
