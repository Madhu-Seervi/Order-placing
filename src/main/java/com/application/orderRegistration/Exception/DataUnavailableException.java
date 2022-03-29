package com.application.orderRegistration.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *Custom exception to handle conditions like data unavailability, also maps them to error response.
 * 
 * @author Madhu Seervi MS096722
 *
 */
@Provider
public class DataUnavailableException extends Exception implements ExceptionMapper<DataUnavailableException> {

	/**
	 * default serial variable.
	 */
	private static final long serialVersionUID = 1L;

	public DataUnavailableException() {
		super("Data not retrirved");
	}

	/**
	 * Custom Exception accepts message as parameterized constructor.
	 * 
	 * @param message
	 */
	public DataUnavailableException(String message) {
		super(message);
	}

	/**
	 * Method which sets the proper status code for the exception in Response.
	 */
	@Override
	public Response toResponse(DataUnavailableException exception) {
		return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type("text/plain").build();
	}
}
