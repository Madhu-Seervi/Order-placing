package com.application.orderRegistration.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Custom exception to handle conditions like not found and unauthorized , also maps them to error responses.
 * 
 * @author Madhu Seervi MS096722
 */

@Provider
public class LoginException extends Exception implements ExceptionMapper<LoginException> {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginException() {
		super("Login exception");
	}

	/**
	 * Custom Exception accepts message as parameterized constructor.
	 * 
	 * @param message
	 */
	public LoginException(String message) {

		super(message);
	}

	/**
	 * Method which sets the proper status code for the exception in Response.
	 */
	@Override
	public Response toResponse(LoginException exception) {
		if (exception.getMessage() == "User not found") {
			return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type("text/plain").build();
		}
		return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).type("text/plain").build();
	}
}
