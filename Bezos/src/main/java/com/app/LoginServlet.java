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

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //private static final String JDBC_URL = "jdbc:sqlite:/Users/kakshilpatel/Desktop/register.db";

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve user input params from the login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Load the JDBC driver
            Class.forName("org.sqlite.JDBC");

            //connection
            try (Connection connection = DatabaseConnection.connect()) {
                // Check if the provided username and password exists in the 'users' table
                if (isLoginValid(connection, username, password)) {
                    // Display Login successful
                    PrintWriter out = response.getWriter();
                    out.println("<html><body><h2>Login Successful!</h2></body></html>");
                    response.sendRedirect("Main.html");
                } else {
                    // Display Username or password is incorrect
                    PrintWriter out = response.getWriter();
                    out.println("<html><body><h2>The username or password is incorrect. If you do not have an account, please sign up first!</h2></body></html>");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions (log, redirect, etc.)
        }
        response.sendRedirect("Search.html");

    }

    private boolean isLoginValid(Connection connection, String username, String password) throws SQLException {
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
}
