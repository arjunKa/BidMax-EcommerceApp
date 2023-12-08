package com.app.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.DatabaseConnection;

public class UserDAO {

	private static List<User> users = new ArrayList<>();

	User user;

	public User create(User user) {
		users.add(user);
		return user;
	}

	public List<User> readAll() {
		return users;
	}

	public User login(String username, String password) {
		// Retrieve user input params from the login form
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DatabaseConnection.connect();

			// Check if the provided username and password exist in the 'users' table
			if (!isLoginValid(conn, username, password)) {
				return null;
			}

			String sql = "SELECT * FROM users WHERE username = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				// Set the properties of the user object
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("firstName"));
				user.setEmail(rs.getString("email"));
				user.setCity(rs.getString("city"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setAddress(rs.getString("address"));
				user.setLastName(rs.getString("lastName"));
				user.setCountry(rs.getString("country"));
				user.setPostalCode(rs.getString("postalCode"));
				user.setCity(rs.getString("city"));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle exceptions (log, redirect, etc.)
		}
		return user;
	}

	public User login(String username) {
		// Retrieve user input params from the login form
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DatabaseConnection.connect();

			String sql = "SELECT * FROM users WHERE username = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				// Set the properties of the user object
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setUsername("username");
				user.setAddress(rs.getString("address"));
				user.setUsername(rs.getString("username"));
				user.setCountry(rs.getString("country"));
				user.setPostalCode(rs.getString("postalCode"));
				user.setCity(rs.getString("city"));
			}
			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			// Handle exceptions (log, redirect, etc.)
		}

		return user;
	}

	public String signUp(User u) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Retrieve user input data from the signup form
		String firstName = u.getFirstName();
		String lastName = u.getLastName();
		String email = u.getEmail();
		String address = u.getAddress();
		String postalCode = u.getPostalCode();
		String city = u.getCity();
		String country = u.getCountry();
		String username = u.getUsername();
		String password = u.getPassword();

		try {
			// connection

			// Create the 'users' table if it doesn't exist
			conn = DatabaseConnection.connect();

			// Check if the username already exists
			if (isUsernameExists(conn, username)) {
				// Display Username already exists

				return ("<html><body><h2>Username already exists. Please use another username.</h2></body></html>");

			}

			// Check if the email already exists
			if (isEmailExists(conn, email)) {
				// Display Email already exists

				return ("<html><body><h2>Email is already associated with an Account. Please Login into the account associated with this email or use another email.</h2></body></html>");

			}

			// Create a SQL query to insert user data into the 'users' table
			String sql = "INSERT INTO users (firstName, lastName, email, address, postalCode, city, country, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// Create a prepared statement
			try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, email);
				preparedStatement.setString(4, address);
				preparedStatement.setString(5, postalCode);
				preparedStatement.setString(6, city);
				preparedStatement.setString(7, country);
				preparedStatement.setString(8, username);
				preparedStatement.setString(9, password);

				// Execute the query
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// Handle exceptions (log, redirect, etc.)
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					/* Ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					/* Ignored */}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					/* Ignored */}
			}

		}
		return "";
	}

	// if count > 0 of a certain username, return false
	private boolean isUsernameExists(Connection connection, String username) throws SQLException {
		String checkUsernameSQL = "SELECT COUNT(*) FROM users WHERE username = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(checkUsernameSQL)) {
			preparedStatement.setString(1, username);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0;
				}
			}
		}
		return false;
	}

	// if count > 0 of a certain email, return false
	private boolean isEmailExists(Connection connection, String email) throws SQLException {
		String checkEmailSQL = "SELECT COUNT(*) FROM users WHERE email = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(checkEmailSQL)) {
			preparedStatement.setString(1, email);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0;
				}
			}
		}
		return false;
	}

	boolean isLoginValid(Connection connection, String username, String password) throws SQLException {
		String checkLoginSQL = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(checkLoginSQL)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0;
				}
			}
		}
		return false;
	}

	public void delete(int id) {
		users.removeIf(s -> s.getId() == id);
	}
}
