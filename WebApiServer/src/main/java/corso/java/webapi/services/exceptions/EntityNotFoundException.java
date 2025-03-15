package corso.java.webapi.services.exceptions;

import lombok.Getter;

public class EntityNotFoundException extends ServiceException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private String searchedKey;
	
	public EntityNotFoundException(String searchedKey) {
		this("Entity not found", searchedKey);
	}

	public EntityNotFoundException(String message, String searchedKey) {
		super(message);
		this.searchedKey = searchedKey;
	}
}
