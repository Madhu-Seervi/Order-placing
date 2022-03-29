package com.application.orderRegistration.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.application.orderRegistration.modal.Patient;

/**
 * Class retrieves Patient details from Person table from database
 * 
 * @author MS096722
 *
 */
public class PatientRetriever {

	/**
	 * Method that returns list of {@link Patient} from person table whose type
	 * code is 2460
	 * 
	 * @return Returns list of {@link Patient}
	 */
	public List<Patient> retrievePatientDetails() {
 
		Logger logger = Logger.getLogger(PatientRetriever.class.getName());
		String query = "SELECT PERSON_ID, LASTNAME, FIRSTNAME FROM PERSON INNER JOIN CODE_VALUE ON CODE_VALUE.CODE_VALUE_ID = PERSON.PERSON_TYPE_CODE WHERE CODE_VALUE.MEANING = 'Patient'";
		List<Patient> listPatients = new ArrayList<>();

		try (Connection connection = DatabaseConnection.getJdbcConnectionInstance();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Patient patient = new Patient();
				patient.setPatientId(resultSet.getLong(1));
				patient.setPatientLastname(resultSet.getString(2));
				patient.setPatientFirstname(resultSet.getString(3));
				listPatients.add(patient);
			}
		} catch (SQLException ex) {
			logger.error(ex);
			ex.printStackTrace();
		} finally {
			logger.info(listPatients.size() + " Patient details retrieved");
		}
		return listPatients;
	}
}