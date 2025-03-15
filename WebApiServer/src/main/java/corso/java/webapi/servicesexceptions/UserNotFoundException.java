package corso.java.webapi.servicesexceptions;

import corso.java.webapi.services.exceptions.ServiceException;
import lombok.Getter;

public class UserNotFoundException extends ServiceException  {

	private static final long serialVersionUID = 1L;

	@Getter
	private String username;
	
	public UserNotFoundException(String username) {
		super("User not found");
		this.username = username;
	}
	
}
