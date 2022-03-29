package com.application.orderRegistration.utility;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.application.orderRegistration.modal.Synonym;

/**
 * Test class for {@link SynonymRetriever}
 * 
 * @author Madhu Seervi MS096722
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ com.application.orderRegistration.utility.DatabaseConnection.class })
@PowerMockIgnore({ "javax.management.*" })

public class SynonymRetrieverTest {

	Connection mockConnection = mock(Connection.class);
	PreparedStatement mockStatement = mock(PreparedStatement.class);
	ResultSet mockResultSet = mock(ResultSet.class);

	/**
	 *  Test case to test if the synonyms are retrieved are of type PRESCRIPTION.
	 * @throws SQLException 
	 */
	@Test
	public void test_Synonym_Retrieved_Is_Prescription_Type() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);

		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(mockResultSet.getLong(1)).thenReturn((long) 1013);
		Mockito.when(mockResultSet.getString(2)).thenReturn("DOLO");
		Mockito.when(mockResultSet.getString(3)).thenReturn("PRESCRIPTION");

		SynonymRetriever systemRetriever = new SynonymRetriever();
		String orderType = "prescription";
		List<Synonym> actualSynonym = systemRetriever.retrieveSynonyms(orderType);
		List<Synonym> expectedSynonym = new ArrayList<Synonym>();
		Synonym synonym = new Synonym();
		synonym.setSynonymId(1013);
		synonym.setMnemonic("DOLO");
		synonym.setOrderType(orderType);
		expectedSynonym.add(synonym);

		assertEquals(expectedSynonym.get(0).getSynonymId(), actualSynonym.get(0).getSynonymId());
		assertEquals(expectedSynonym.get(0).getMnemonic(), actualSynonym.get(0).getMnemonic());
		assertEquals(expectedSynonym.get(0).getOrderType().toUpperCase(), actualSynonym.get(0).getOrderType());
	}

	/**
	 * Test case if the synonym retrieved are of type NON MEDICATION.
	 * @throws SQLException
	 */
	@Test
	public void test_Synonym_Retrieved_Is_NonMedication_Type() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);

		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(mockResultSet.getLong(1)).thenReturn((long) 1013);
		Mockito.when(mockResultSet.getString(2)).thenReturn("SCAN");
		Mockito.when(mockResultSet.getString(3)).thenReturn("NON MEDICATION");

		SynonymRetriever systemRetriever = new SynonymRetriever();
		String orderType = "non medication";
		List<Synonym> actualSynonym = systemRetriever.retrieveSynonyms(orderType);
		List<Synonym> expectedSynonym = new ArrayList<Synonym>();
		Synonym synonym = new Synonym();
		synonym.setSynonymId(1013);
		synonym.setMnemonic("SCAN");
		synonym.setOrderType(orderType);
		expectedSynonym.add(synonym);

		assertEquals(expectedSynonym.get(0).getSynonymId(), actualSynonym.get(0).getSynonymId());
		assertEquals(expectedSynonym.get(0).getMnemonic(), actualSynonym.get(0).getMnemonic());
		assertEquals(expectedSynonym.get(0).getOrderType().toUpperCase(), actualSynonym.get(0).getOrderType());
	}

	/**
	 * Test case if the synonym list is not retrieved.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void Test_Synonym_Retrieved_IsEmpty() throws SQLException{
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(false);

		SynonymRetriever synonymRetriever = new SynonymRetriever();
		List<Synonym> synonym = synonymRetriever.retrieveSynonyms("ABC");

		assertTrue(synonym.isEmpty());
	}
 
	/**
	 * Test case if SQLException is thrown.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_For_SQLException() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenThrow(SQLException.class);

		SynonymRetriever synonymRetriever = new SynonymRetriever();
		List<Synonym> synonym = synonymRetriever.retrieveSynonyms("prescription");

		assertTrue(synonym.isEmpty());
	}
}
