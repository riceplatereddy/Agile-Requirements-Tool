package art.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperations {

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public DatabaseOperations() {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost/art_db?"
							+ "user=root&password=1");

			System.out
					.println("Connection Successfull and the DATABASE NAME IS:"
							+ connection.getMetaData().getDatabaseProductName());
		} catch (Exception e) {
			System.out.println("Error in Database Operations :"
					+ e.getMessage());
		}
	}

	public ResultSet executeQuery(String query) {
		try {
			// statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Error in Database Operations :"
					+ e.getMessage());
		} finally {
			try {
				// close all connections
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in Database Operations :"
						+ e.getMessage());
			}
		}
		return resultSet;

	}

	public void updateDB() {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost/art_db?"
							+ "user=root&password=1");

		} catch (Exception e) {
			System.out.println("Error in Database Operations :"
					+ e.getMessage());
		} finally {
			try {
				// close all connections
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in Database Operations :"
						+ e.getMessage());
			}
		}

	}
}
