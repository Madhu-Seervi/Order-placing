package com.application.orderRegistration.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.application.orderRegistration.modal.CodeValue;

/**
 * Class retrieves value from CodeValue table from the database
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class CodeValueRetriever {

	/**
	 * Method that returns list of {@link CodeValue}.
	 * 
	 * @return Returns list of {@link CodeValue}.
	 */
	public List<CodeValue> retrieveCodeValues() {
		Logger logger = Logger.getLogger(CodeValueRetriever.class.getName());
		String query = "SELECT * FROM CODE_VALUE";
		List<CodeValue> listCodeValue = new ArrayList<>();
		
		try (Connection connection = DatabaseConnection.getJdbcConnectionInstance();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet resultSet = preparedStatement.executeQuery(); 
			while (resultSet.next()) {
				CodeValue codeValue = new CodeValue();
				codeValue.setCodeValue(resultSet.getInt(1));
				codeValue.setMeaning(resultSet.getString(2));
				listCodeValue.add(codeValue);
			}
		} catch (SQLException ex) {
			logger.error(ex);
			ex.printStackTrace();
		}finally{
			logger.info(listCodeValue.size()+" Number of code values are retrieved");
		}
		return listCodeValue;
	}
}