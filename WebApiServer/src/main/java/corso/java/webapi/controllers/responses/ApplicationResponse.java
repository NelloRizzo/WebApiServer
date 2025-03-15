package corso.java.webapi.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationResponse<T> {
	private ApiError error;
	private T data;

	public ApplicationResponse(T data) {
		this();
		this.data = data;
	}

	public ApplicationResponse(ApiError error) {
		this();
		this.error = error;
	}
}
