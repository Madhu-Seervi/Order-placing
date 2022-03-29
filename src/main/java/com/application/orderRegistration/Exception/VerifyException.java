package com.application.orderRegistration.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Custom exception to handle conditions when input parameters to the system are not acceptable or invalid, also maps them to error responses.
 * 
 * @author Madhu Seervi MS096722
 *
 */
@Provider
public class VerifyException extends Exception implements ExceptionMapper<VerifyException> {

	/**
	 * default serial variable.
	 */
	private static final long serialVersionUID = 1L;

	public VerifyException() {
		super("Invalid input");
	}

	/**
	 * Custom Exception accepts message as parameterized constructor.
	 * 
	 * @param message
	 */
	public VerifyException(String message) {
		super(message);
	}

	/**
	 * Method which sets the proper status code for the exception in Response.
	 */
	@Override
	public Response toResponse(VerifyException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("text/plain").build();
	}
}
