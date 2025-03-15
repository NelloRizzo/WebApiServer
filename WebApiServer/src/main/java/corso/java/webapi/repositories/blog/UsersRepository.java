package corso.java.webapi.repositories.blog;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import corso.java.webapi.entities.blog.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);

	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User>  findByUsername(String username);
}
