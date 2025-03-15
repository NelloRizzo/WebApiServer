package corso.java.webapi.repositories.blog;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import corso.java.webapi.entities.blog.Role;

public interface RolesRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(String name);
}
