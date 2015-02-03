package ch.fhnw.edu.rental;

import java.io.FileInputStream;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DatabaseSetup {
	private static String TEST_DATA_FILE = "etc/dataset.xml";

	public static void insertTestDataIntoDB(ApplicationContext context)
			throws Exception {
		DataSource ds = (DataSource) context.getBean("dataSource");
		Connection conn = ds.getConnection();

		try {
			IDatabaseConnection connection = new DatabaseConnection(conn);
			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					new HsqldbDataTypeFactory());
			DatabaseOperation.CLEAN_INSERT.execute(connection, new FlatXmlDataSetBuilder().build(new FileInputStream(TEST_DATA_FILE)));
		}
		finally {
			DataSourceUtils.releaseConnection(conn, ds);
		}
	}

	public static void removeTestDataFromDB(ApplicationContext context)
			throws Exception {
		// Delete the data
		DataSource ds = (DataSource) context.getBean("dataSource");
		Connection conn = ds.getConnection();
		try {
			IDatabaseConnection connection = new DatabaseConnection(conn);
			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					new HsqldbDataTypeFactory());
			DatabaseOperation.CLEAN_INSERT.execute(connection, new FlatXmlDataSetBuilder().build(new FileInputStream(TEST_DATA_FILE)));
		}
		finally {
			DataSourceUtils.releaseConnection(conn, ds);
		}
	}
}
