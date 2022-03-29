package com.application.orderRegistration.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.application.orderRegistration.modal.Credential;

/**
 * Class retrieves values from credential and person table from the database.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class CredentialRetriever {

	/**
	 * Class that retrieves the credential and user information for the given
	 * user id.
	 * 
	 * @param userId
	 *            the id of the user trying to access the service.(must be
	 *            positive integer)
	 * @return credential the {@link Credential} of the user containing
	 *         credential and user information (possibly null).
	 */
	public Credential retrieveUserCredential(Long userId) {

		String query = "SELECT CREDENTIALS.PERSON_ID, CREDENTIALS .SECRET_CODE, PERSON.PERSON_TYPE_CODE ,PERSON.LASTNAME , PERSON.FIRSTNAME FROM PERSON INNER JOIN CREDENTIALS ON (PERSON.PERSON_ID=CREDENTIALS.PERSON_ID) WHERE CREDENTIALS.PERSON_ID=?";
		Logger logger = Logger.getLogger(CredentialRetriever.class.getName());
		Credential credential = null;

		try (Connection connection = DatabaseConnection.getJdbcConnectionInstance();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				credential = new Credential();
				credential.setPersonId(resultSet.getLong("person_id"));
				credential.setSecretCode(resultSet.getString("secret_code"));
				credential.setPersonTypeCode(resultSet.getInt("person_type_code"));
				credential.setLastName(resultSet.getString("lastname"));
				credential.setFirstName(resultSet.getString("firstname"));
				logger.info("User successfully retrieved for user ID" + credential.getPersonId());
				return credential;
			} else {
				logger.info("No credentials Retrieved");
			}

		} catch (SQLException ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
		return credential; 
	}
}