import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseController {

	// Local SQL database credentials
	private final String SQL_USERNAME = "root";
	private final String SQL_PASSWORD = "";
	private final String SERVER_NAME = "localhost";
	private final int PORT_NUMBER = 3306;
	private final String DATABASE_NAME = "test";
	private final String[] TABLE_NAMES = { "users", "students" };
	private Connection sql; // Main connection variable to the database
	
	/**
	 * Sets up JDBC connection to the locally hosted SQL database.
	 */
	private void connectToSQLDatabase() {
		Properties connectionProps = new Properties();
		
		// Logging in using the SQL credentials
		connectionProps.put("user", this.SQL_USERNAME);
		connectionProps.put("password", this.SQL_PASSWORD);
		
		try {
			// Making connection to the chosen database
			sql = DriverManager.getConnection(
					"jdbc:mysql://" + this.SERVER_NAME + ":" + this.PORT_NUMBER + "/" + this.DATABASE_NAME + "?serverTimezone=UTC",
					connectionProps
			);
			
			// consoleScreen.append("Connected to SQL database " + DATABASE_NAME + ".\n");
		} catch (Exception e) {
		}
	}
}
