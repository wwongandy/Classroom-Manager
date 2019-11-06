import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	private boolean isConnected; // Used to check if SQL connection is valid
	
	public DatabaseController() {
		connectToSQLDatabase();
	}
	
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
			
			this.setConnected(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setConnected(false);
	}
	
	/**
	 * Returns a boolean according to the given UNAME and if it is a valid username from the `users` table.
	 * 
	 * @param uname
	 * @return
	 */
	public boolean userLogin(String uname) {
		try {
			Statement stmt = this.sql.createStatement();
			
			stmt.executeQuery(
					"SELECT * FROM `users` " +
					"WHERE (UNAME = " + uname + ")"
			);
			
			ResultSet found = stmt.getResultSet();
			
			// found.first gives true if value exists
			boolean loggedIn = found.first();
			found.close();
			
			return loggedIn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Returns a ResultSet corresponding to all the students in the `students` table
	 * 
	 * @return
	 */
	public ResultSet getAllStudentRecords() {
		try {
			Statement stmt = this.sql.createStatement();
			
			stmt.executeQuery(
					"SELECT * FROM `students`"
			);
			
			return stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Returns a ResultSet corresponding to the list of students in the `students table` with the given surname (SNAME)
	 * 
	 * @param sname
	 * @return
	 */
	public ResultSet searchStudentBySurname(String sname) {
		try {
			Statement stmt = this.sql.createStatement();
			
			stmt.executeQuery(
					"SELECT * FROM `students` " +
					"WHERE (SNAME = " + sname + ")"
			);
			
			return stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
}
