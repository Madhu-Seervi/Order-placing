package com.application.orderRegistration.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.application.orderRegistration.modal.Synonym;

/**
 * Class that retrieve synonym details from synonyms table from database
 * 
 * @author MS096722
 *
 */
public class SynonymRetriever {

	/**
	 * Method that return list of synonyms of type {@link Synonym} from synonyms
	 * table whose order type is given.
	 * @param orderType.
	 * @return Returns list of {@link Synonym}
	 */
	public List<Synonym> retrieveSynonyms(String orderType) {

		Logger logger = Logger.getLogger(SynonymRetriever.class.getName());
		String query = "SELECT SYNONYMS.SYNONYM_ID, SYNONYMS.MNEMONIC, CODE_VALUE.MEANING FROM SYNONYMS INNER JOIN CODE_VALUE ON SYNONYMS.ORDER_TYPE_CODE=CODE_VALUE.CODE_VALUE_ID WHERE CODE_VALUE.MEANING=?";
		List<Synonym> listSynonym = new ArrayList<>();

		try (Connection connection = DatabaseConnection.getJdbcConnectionInstance();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, orderType.toUpperCase());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Synonym synonym = new Synonym();
				synonym.setSynonymId(resultSet.getLong(1));
				synonym.setMnemonic(resultSet.getString(2));
				synonym.setOrderType(resultSet.getString(3));
				listSynonym.add(synonym);
			}

		} catch (SQLException ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
		logger.info(listSynonym.size() + " Synonyms details retrieved");
		return listSynonym;
	}
}