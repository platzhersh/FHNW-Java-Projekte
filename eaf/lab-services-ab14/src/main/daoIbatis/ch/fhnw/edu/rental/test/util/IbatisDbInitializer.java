package ch.fhnw.edu.rental.test.util;

import java.io.InputStream;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.context.ApplicationContext;

public class IbatisDbInitializer implements DbInitializer {

	@Override
	public void resetData(ApplicationContext context) throws Exception {
		// initialize the database connection
		DataSource dataSource = (DataSource)context.getBean("dataSource");
		Connection dbconn = dataSource.getConnection();

		IDatabaseConnection connection = new DatabaseConnection(dbconn);
		DatabaseConfig config = connection.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new HsqldbDataTypeFactory());

		// initialize the dataset
		//InputStream stream = TestUtil.class.getResourceAsStream("dataset.xml");

		InputStream stream = TestUtil.class.getResourceAsStream("dataset.xml");
//		FileInputStream stream = new FileInputStream("dataset.xml");
		IDataSet dataSet = new FlatXmlDataSet(stream);
        
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		} finally {
			connection.close();
		}
	}

}
