package com.application.orderRegistration.Exception;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

/**
 * Test class for {@link DataUnavailableException}.
 * @author Madhu Seervi MS096722
 *
 */
public class DataUnavailableExceptionTest {
	/**
	 * Test case to check toResponse method returns a 404 status code when exception message is 'No list found'.
	 */
	@Test
	public void test_ToResponse_CustomExceptionMessage() {
		DataUnavailableException dataUnavailableException = new DataUnavailableException("No list found");
		Response response = dataUnavailableException.toResponse(dataUnavailableException);

		assertEquals("No list found", response.getEntity());
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
	/**
	 * Test case to check exception using default constructor(){
	 * 
	 */
	@Test
	public void testResponse_Default_Constructor(){
		DataUnavailableException dataUnavailableException = new DataUnavailableException();
		Response response = dataUnavailableException.toResponse(dataUnavailableException);

		assertEquals("Data not retrirved", response.getEntity());
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
	
}
