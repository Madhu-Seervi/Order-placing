package com.application.orderRegistration.Exception;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

/**
 * Test class for custom login exception {@link LoginException}.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class LoginExceptionTest {

	/**
	 * Test case to check toResponse method returns 404 status code when
	 * exception message is 'user not found'.
	 */
	@Test
	public void test_ToResponse_CustomExceptionMessage_UserNotFound() {
		LoginException loginException = new LoginException("User not found");
		Response response = loginException.toResponse(loginException);

		assertEquals("User not found", response.getEntity());
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	/**
	 * Test case to check toResponse method returns 401 status code when
	 * exception message is 'Unauthorized access'.
	 * 
	 */
	@Test
	public void test_ToResponse_CustomExceptionMessage_Unauthorized_access() {
		LoginException loginException = new LoginException("Unauthorized access");
		Response response = loginException.toResponse(loginException);

		assertEquals("Unauthorized access", response.getEntity());
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}

	/**
	 * Test case to check toResponse method returns 401 status code when
	 * exception message is 'Incorrect password'.
	 */
	@Test
	public void test_ToResponse_CustomExceptionMessage_Incorrect_Password() {
		LoginException loginException = new LoginException("Incorrect password");
		Response response = loginException.toResponse(loginException);

		assertEquals("Incorrect password", response.getEntity());
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}

	/**
	 * Test case to check exception using default constructor(){
	 * 
	 */
	@Test
	public void testResponse_Default_Constructor() {
		LoginException loginException = new LoginException();
		Response response = loginException.toResponse(loginException);

		assertEquals("Login exception", response.getEntity());
		assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
	}
}
