package corso.java.webapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import corso.java.webapi.controllers.models.LoginModel;
import corso.java.webapi.controllers.models.LoginResponseModel;
import corso.java.webapi.controllers.models.RegisteredUserModel;
import corso.java.webapi.controllers.responses.ApiError;
import corso.java.webapi.controllers.responses.ApplicationResponse;
import corso.java.webapi.entities.blog.Role;
import corso.java.webapi.services.UserService;
import corso.java.webapi.services.dto.RegisterUserDto;
import corso.java.webapi.services.exceptions.LoginFailedException;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	@Autowired
	private UserService service;

	@PostMapping("register")
	public ResponseEntity<ApplicationResponse<RegisteredUserModel>> registerUser(@RequestBody RegisterUserDto userDto) {
		try {
			var user = service.registerUser(userDto).orElseThrow();
			return ResponseEntity
					.ok(new ApplicationResponse<RegisteredUserModel>(new RegisteredUserModel(user.getUsername(),
							user.getEmail(), user.getRoles().stream().map(Role::getName).toList())));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApplicationResponse<RegisteredUserModel>(new ApiError(e.getMessage())));
		}
	}

	@PostMapping("login")
	public ResponseEntity<ApplicationResponse<LoginResponseModel>> loginUser(@RequestBody LoginModel login) {
		try {
			var response = service.login(login.getEmail(), login.getPassword())
					.orElseThrow(() -> new LoginFailedException());
			return ResponseEntity.ok(new ApplicationResponse<LoginResponseModel>(response));
		} catch (LoginFailedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApplicationResponse<LoginResponseModel>(new ApiError("Login failed")));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ApplicationResponse<LoginResponseModel>(new ApiError("Login failed")));
		}
	}
}
