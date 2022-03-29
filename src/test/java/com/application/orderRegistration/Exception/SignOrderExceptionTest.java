package com.application.orderRegistration.Exception;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

/**
 * Test class for {@link SignOrderException}
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class SignOrderExceptionTest {

	/**
	 * Test case to check toResponse method returns a 500 status code when exception message is 'Order not placed'.
	 */
	@Test
	public void test_ToResponse_CustomExceptionMessage() {
		SignOrderException signOrderException = new SignOrderException("Order not placed");
		Response response = signOrderException.toResponse(signOrderException);

		assertEquals("Order not placed", response.getEntity());
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
	
	/**
	 * Test case to check exception using default constructor(){
	 * 
	 */
	@Test
	public void testResponse_Default_Constructor(){
		SignOrderException signOrderException = new SignOrderException();
		Response response = signOrderException.toResponse(signOrderException);

		assertEquals("Exception in signing order", response.getEntity());
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
}
