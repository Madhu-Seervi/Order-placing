package com.application.orderRegistration.Exception;

import static org.junit.Assert.assertEquals;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

/**
 * Test class for {@link VerifyException}.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class VerifyExceptionTest {

	/**
	 * Test case to check toResponse method returns a 400 status code when exception message is 'Invalid input'.
	 */
	@Test
	public void test_ToResponse_CustomExceptionMessage() {
		VerifyException verifyException = new VerifyException("Invalid input");
		Response response = verifyException.toResponse(verifyException);

		assertEquals("Invalid input", response.getEntity());
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}
	
	/**
	 * Test case to check exception using default constructor(){
	 * 
	 */
	@Test
	public void testResponse_Default_Constructor(){
		VerifyException verifyException = new VerifyException();
		Response response = verifyException.toResponse(verifyException);

		assertEquals("Invalid input", response.getEntity());
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}
}
