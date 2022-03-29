package com.application.orderRegistration.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Custom exception to handle conditions like Exception in signing order, also maps them to error responses.
 * 
 * @author Madhu Seervi MS096722
 *
 */
@Provider
public class SignOrderException extends Exception implements ExceptionMapper<SignOrderException> {

	private static final long serialVersionUID = 1L;

	public SignOrderException() {
		super("Exception in signing order");
	}

	/**
	 * Custom Exception accepts message as parameter
	 * 
	 * @param message
	 */
	public SignOrderException(String message) {

		super(message);
	}

	/**
	 * Method which sets the proper status code for the exception in Response.
	 */
	@Override
	public Response toResponse(SignOrderException exception) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).type("text/plain").build();
	}
}
