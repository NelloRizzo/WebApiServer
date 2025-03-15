package corso.java.webapi.services.exceptions;

public class OperationUnauthorizedException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public OperationUnauthorizedException() {
		this("Operation unauthorized");
	}
	
	public OperationUnauthorizedException(String message) {
		super(message);
	}
}
