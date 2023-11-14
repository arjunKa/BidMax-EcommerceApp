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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         

        // Retrieve user input data from the signup form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String postalCode = request.getParameter("pcode");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            // Display Passwords do not match
            PrintWriter out = response.getWriter();
            out.println("<html><body><h2>Passwords don't match. Please try again.</h2></body></html>");
            return;
        }

        try {
            // Load the JDBC driver
            Class.forName("org.sqlite.JDBC");

            // connection
            try (Connection connection = DatabaseConnection.connect()) {
                // Create the 'users' table if it doesn't exist
                createUsersTable(connection);

                // Check if the username already exists
                if (isUsernameExists(connection, username)) {
                    // Display Username already exists
                    PrintWriter out = response.getWriter();
                    out.println("<html><body><h2>Username already exists. Please use another username.</h2></body></html>");
                    return;
                }

                // Check if the email already exists
                if (isEmailExists(connection, email)) {
                    // Display Email already exists
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

        // Redirect to login page
        response.sendRedirect("Login.html");
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

}
