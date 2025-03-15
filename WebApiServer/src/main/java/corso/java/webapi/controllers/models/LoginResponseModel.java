package corso.java.webapi.controllers.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class LoginResponseModel {
	private String username;
	private String email;
	private String token;
	@Builder.Default
	private List<String> roles = new ArrayList<String>();
}
