package com.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SigninServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String JDBC_URL = "jdbc:sqlite:/Users/kakshilpatel/Desktop/register.db";

    public SigninServlet() {
        super();
    }
    
    private void createUsersTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "firstName TEXT NOT NULL,"
                + "lastName TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "address TEXT NOT NULL,"
                + "postalCode TEXT NOT NULL,"
                + "city TEXT NOT NULL,"
                + "country TEXT NOT NULL,"
                + "username TEXT NOT NULL,"
                + "password TEXT NOT NULL)";
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
        }
    }
    
    private String getParameterWithDefault(HttpServletRequest request, String paramName, String defaultValue) {
        String value = request.getParameter(paramName);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // JDBC URL, username, and password of SQLite server
        // ...

        // Retrieve user input from the signup form
    	String firstName = getParameterWithDefault(request, "firstName", "");
    	System.out.println("First Name: " + firstName);

    	String lastName = getParameterWithDefault(request, "lastName", "");
    	System.out.println("Last Name: " + lastName);

    	String email = getParameterWithDefault(request, "email", "");
    	System.out.println("Email: " + email);

    	String address = getParameterWithDefault(request, "address", "");
    	System.out.println("Address: " + address);

    	String postalCode = getParameterWithDefault(request, "pcode", "");
    	System.out.println("Postal Code: " + postalCode);

    	String city = getParameterWithDefault(request, "city", "");
    	System.out.println("City: " + city);

    	String country = getParameterWithDefault(request, "country", "");
    	System.out.println("Country: " + country);

    	String username = getParameterWithDefault(request, "username", "");
    	System.out.println("Username: " + username);

    	String password = getParameterWithDefault(request, "password", "");
    	System.out.println("Password: " + password);

    	String confirmPassword = getParameterWithDefault(request, "confirmPassword", "");
    	System.out.println("Confirm Password: " + confirmPassword);

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            // Passwords do not match, print out a message to the response
            PrintWriter out = response.getWriter();
            out.println("<html><body><h2>Passwords don't match. Please try again.</h2></body></html>");
            return;
        }

        try {
            // Load the JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish a connection
            try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
                // Create the 'users' table if it doesn't exist
                createUsersTable(connection);

                // Check if the username already exists
                if (isUsernameExists(connection, username)) {
                    // Username already exists, print out a message to the response
                    PrintWriter out = response.getWriter();
                    out.println("<html><body><h2>Username already exists. Please use another username.</h2></body></html>");
                    return;
                }

                // Check if the email already exists
                if (isEmailExists(connection, email)) {
                    // Email already exists, print out a message to the response
                    PrintWriter out = response.getWriter();
                    out.println("<html><body><h2>Email is already associated with an Account. Please Login into the account associated with this email or use another email.</h2></body></html>");
                    return;
                }

                // Create a SQL query to insert user data into the 'users' table
                String sql = "INSERT INTO users (firstName, lastName, email, address, postalCode, city, country, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // Create a prepared statement
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions (log, redirect, etc.)
        }

        // Redirect to a success page or login page
        response.sendRedirect("Login.html");
    }

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

}

