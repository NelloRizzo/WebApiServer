package corso.java.webapi.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import corso.java.webapi.controllers.models.LoginResponseModel;
import corso.java.webapi.entities.blog.Role;
import corso.java.webapi.entities.blog.User;
import corso.java.webapi.repositories.blog.RolesRepository;
import corso.java.webapi.repositories.blog.UsersRepository;
import corso.java.webapi.services.dto.RegisterUserDto;
import corso.java.webapi.services.models.UserDetailsImpl;
import corso.java.webapi.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private RolesRepository rolesRepo;
	@Autowired
	private UsersRepository usersRepo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public Optional<User> getUserByEmail(String email) {
		try {
			return usersRepo.findByEmail(email);
		} catch (Exception e) {
			log.error("Exception retrieving user", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<Role> getRoleByName(String roleName) {
		try {
			return rolesRepo.findByName(roleName);
		} catch (Exception e) {
			log.error("Exception retrieving role", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<User> getUsers() {
		try {
			return usersRepo.findAll();
		} catch (Exception e) {
			log.error("Exception retrieving all users", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Role> getRoles() {
		try {
			return rolesRepo.findAll();
		} catch (Exception e) {
			log.error("Exception retrieving all roles", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<LoginResponseModel> login(String email, String password) {
		try {
			var user = usersRepo.findByEmail(email).orElseThrow();
			//password = encoder.encode(password);
			if (!encoder.matches(password, user.getPassword())) {
				throw new RuntimeException("Invalid password");
			}			
			var details = UserDetailsImpl.from(user);
			var authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return Optional.of(LoginResponseModel.builder() //
					.withToken(jwtUtils.generateJwtToken(authentication)) //
					.withEmail(user.getEmail()) //
					.withUsername(user.getUsername()) //
					.withRoles(user.getRoles().stream().map(Role::getName).toList()) //
					.build());
		} catch (Exception e) {
			log.error("Exception in login attempt", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<User> registerUser(RegisterUserDto dto) {
		try {
			var user = User.builder().withEmail(dto.getEmail()).withPassword(dto.getPassword())
					.withUsername(dto.getUsername()).build();
			user.setPassword(encoder.encode(user.getPassword()));
			return Optional.of(usersRepo.save(user));
		} catch (Exception e) {
			log.error("Exception registering user", e);
			throw new RuntimeException(e);
		}
	}

}
