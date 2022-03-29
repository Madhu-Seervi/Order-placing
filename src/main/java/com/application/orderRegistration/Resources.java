package com.application.orderRegistration;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.application.orderRegistration.Exception.DataUnavailableException;
import com.application.orderRegistration.Exception.LoginException;
import com.application.orderRegistration.Exception.SignOrderException;
import com.application.orderRegistration.Exception.VerifyException;
import com.application.orderRegistration.modal.Patient;
import com.application.orderRegistration.modal.PostOrder;
import com.application.orderRegistration.modal.ProviderInformation;
import com.application.orderRegistration.modal.Synonym;
import com.application.orderRegistration.modal.User;
import com.application.orderRegistration.services.PatientValidator;
import com.application.orderRegistration.services.SignOrderValidator;
import com.application.orderRegistration.services.SynonymValidator;
import com.application.orderRegistration.services.UserValidator;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Resource class.
 * 
 * @author Madhu Seervi MS096722
 */
@Path("/chart-order")
public class Resources {

	private PatientValidator patientValidator;
	private SynonymValidator synonymValidator; 
	private SignOrderValidator signOrderValidator;
	private UserValidator userValidator;

	public Resources(PatientValidator patientValidator, SynonymValidator synonymValidator,
			SignOrderValidator signOrderValidator, UserValidator userValidator) {

		this.patientValidator = patientValidator;
		this.synonymValidator = synonymValidator;
		this.signOrderValidator = signOrderValidator;
		this.userValidator = userValidator;
	}

	public Resources(){
		this.patientValidator = new PatientValidator();
		this.signOrderValidator = new SignOrderValidator();
		this.synonymValidator= new SynonymValidator();
		this.userValidator = new UserValidator();
	}
	/**
	 * This API validate the user credibility and returns provider information
	 * for a valid user.
	 * 
	 * @param user
	 *            accepts values provider id and secret code from class
	 *            {@link User}
	 * @return {@link ProviderInformation} is retrieved with status 200.
	 * @throws LoginException
	 *             when the {@link ProviderInformation} is null then it throws
	 *             exception user not found and if secret code does not match it
	 *             throws exception Incorrect password and if the person type
	 *             code is of type is other than physician and nurse it throws
	 *             exception unauthorized access.
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 *             when the providerId is not positive.
	 */
	@POST
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProviderDetails(User user) throws LoginException, JsonProcessingException, VerifyException {
		return Response.ok(userValidator.getValidProviderInformation(user.getProviderId(), user.getSecretCode()))
				.build();
	}

	/**
	 * This API returns list of patients registered in the system.
	 * 
	 * @return list of {@link Patient}s.
	 * @throws DataUnavailable
	 *             when list of patient is not retrieved.
	 * @throws JsonProcessingException
	 */
	@GET
	@Path("/patients")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getPatients() throws DataUnavailableException, JsonProcessingException {
		return Response.ok(patientValidator.getPatientInformation()).build(); 
	}

	/**
	 * This API returns list of synonyms registered in the system.
	 * 
	 * @param orderType
	 *            can be prescription or non medication.
	 * @return list of {@link Synonym}s.
	 * @throws DataUnavailable
	 *             when list of synonym is not retrieved.
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 *             when order type is incorrect.
	 */
	@GET
	@Path("/synonyms")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSynonyms(@QueryParam("orderType") String orderType)
			throws DataUnavailableException, JsonProcessingException, VerifyException {
		return Response.ok(synonymValidator.getSynonymInformation(orderType)).build();
	}

	/**
	 * This API charts an order on a patient.
	 * 
	 * @param postOrder
	 *            of type {@link PostOrder}.
	 * @return Response 200 status code and orderId if the value inserted
	 *         successfully.
	 * @throws SignOrderException
	 *             when order not placed successfully.
	 * @throws VerifyException
	 *             when {@link PostOrder} checks are violated.
	 */
	@POST
	@Path("/add-order")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOrder(PostOrder postOrder) throws SignOrderException, VerifyException {
		return Response.ok(signOrderValidator.performSignOrderCheck(postOrder)).build();
	}
}
