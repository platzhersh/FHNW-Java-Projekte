package webfr.flashcards.server.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Responsible to initialize database.
 * 
 */
public class Database {
    /** SQL command to create table CARDBOXES. */
    protected static final String CREATE_CARDBOXES = 
        "drop table if exists CARDBOXES; "  
      + "create table CARDBOXES ("
      + "  Id INTEGER IDENTITY, " 
      + "  Title VARCHAR(50), " 
      + "  Description VARCHAR(1000), " 
      + "  Version TIMESTAMP" 
      + ")";

    /** SQL command to create table CARDS. */
    protected static final String CREATE_CARDS = 
        "drop table if exists CARDS; " 
      + "create table CARDS ("
      + "  Id INTEGER IDENTITY," 
      + "  CardboxId INTEGER," 
      + "  Question VARCHAR(10000),"
      + "  Answer VARCHAR(10000)" 
      + ")";
    
    /** SQL command to create a SEQUENCE for primary keys. */
    protected static final String CREATE_SEQUENCE = "CREATE SEQUENCE seq";
    
    private String datafile;
    
    public Database(String datafile) {
      this.datafile = datafile;
    }
    
    /**
     * Initializes the database.
     * 
     * @throws Exception none.
     * 
     */
    public void initDatabase() throws Exception {

        Connection connection = DatabaseJdbcDriver.loadDriver();

        createDatabaseModel(connection);

        importData(connection);

        connection.close();
    }
    
    /**
     * Create the database tables.
     */
    private void createDatabaseModel(Connection connection) {
        // create tables in db
        try {
            command(CREATE_CARDBOXES, connection);
            command(CREATE_CARDS, connection);
            command(CREATE_SEQUENCE, connection);
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }
    }

    /**
     * Import data into database.
     * 
     * @throws Exception throws various exceptions
     */
    protected void importData(Connection connection) throws Exception {
      if (datafile != null) {
        DatabaseImporter dbImport = new DatabaseImporter();
        dbImport.setDataFile(datafile);
        dbImport.importData(connection);
      }
    }

    /**
     * use for SQL commands CREATE, DROP, INSERT and UPDATE.
     * 
     * @param expression SQL command
     * @throws SQLException when something went wrong
     */
    protected synchronized void command(String expression, Connection connection) throws SQLException {
        Statement st = null;
        st = connection.createStatement(); // statements
        int i = st.executeUpdate(expression); // run the query
        if (i == -1) {
            System.out.println("db error : " + expression);
        }
        st.close();
    }

}
