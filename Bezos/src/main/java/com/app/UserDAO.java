package com.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

	User login(String username, String password) {
		// Retrieve user input params from the login form

		try {
			// Load the JDBC driver
			Class.forName("org.sqlite.JDBC");

			// connection
			try (Connection connection = DatabaseConnection.connect()) {
				// Check if the provided username and password exist in the 'users' table
				if (!isLoginValid(connection, username, password)) {
					return null;
				}

				String sql = "SELECT * FROM users WHERE username = ?";

				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
					preparedStatement.setString(1, username);
					try (ResultSet rs = preparedStatement.executeQuery()) {
						if (rs.next()) {
							user = new User();
							// Set the properties of the user object
							user.setId(rs.getInt("id"));
							user.setFirstName(rs.getString("firstName"));
							user.setUsername("username");
							user.setAddress(rs.getString("address"));
							user.setLastName(rs.getString("lastName"));
							user.setCountry(rs.getString("city"));
							user.setPostalCode(rs.getString("postalCode"));
						}
					}
				}

			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			// Handle exceptions (log, redirect, etc.)
		}
		return user;
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