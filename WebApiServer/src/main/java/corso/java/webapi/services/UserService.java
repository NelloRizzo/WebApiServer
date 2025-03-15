package corso.java.webapi.services;

import java.util.List;
import java.util.Optional;

import corso.java.webapi.controllers.models.LoginResponseModel;
import corso.java.webapi.entities.blog.Role;
import corso.java.webapi.entities.blog.User;
import corso.java.webapi.services.dto.RegisterUserDto;

public interface UserService {
	Optional<User> getUserByEmail(String email);

	Optional<Role> getRoleByName(String roleName);

	List<User> getUsers();

	List<Role> getRoles();

	Optional<LoginResponseModel> login(String email, String password);

	Optional<User> registerUser(RegisterUserDto user);
}
