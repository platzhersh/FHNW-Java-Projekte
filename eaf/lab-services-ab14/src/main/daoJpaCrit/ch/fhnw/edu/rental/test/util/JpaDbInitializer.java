package ch.fhnw.edu.rental.test.util;

import java.io.InputStream;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JpaDbInitializer implements DbInitializer {

	@Autowired
	private ApplicationContext context;

	private static String TEST_DATA_FILE = "dataset.xml";
	
	private String testDataFile = TEST_DATA_FILE;

	/**
	 * Allows to change the test data file to be used.
	 * 
	 * @param testDataFile
	 *            name of the test data file which has to be available on the
	 *            class path
	 */
	public void setTestDataFile(String testDataFile) {
		this.testDataFile = testDataFile;
	}

	public void resetData() throws Exception {
		DataSource ds = (DataSource) context.getBean("dataSource");
		Connection conn = ds.getConnection();
	
		try {
			IDatabaseConnection connection = new DatabaseConnection(conn);
			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					new HsqldbDataTypeFactory());
			
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream(testDataFile);
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			IDataSet dataSet = builder.build(stream);
			
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		}
		finally {
			DataSourceUtils.releaseConnection(conn, ds);
		}
	}
	
	private boolean resetOnStartup;

	/**
	 * Defines whether the data is reset on startup.
	 * 
	 * @param value
	 *            if set to true, then the database will be initialized upon
	 *            startup, i.e. when this bean is loaded by spring.
	 */
	public void setResetOnStartup(boolean value) {
		resetOnStartup = value;
	}

	/**
	 * The initilaize method is invoked when the bean is loaded. Depending on
	 * the value of the field resetOnStartup the database is reset.
	 */
	@SuppressWarnings("unused")
	private void initialize() throws Exception {
		if(resetOnStartup) {
			resetData();
		}
	}

}
