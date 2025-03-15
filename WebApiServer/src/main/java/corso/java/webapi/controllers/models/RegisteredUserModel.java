package corso.java.webapi.controllers.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class RegisteredUserModel {
	private String username;
	private String email;
	private List<String> roles;
}
