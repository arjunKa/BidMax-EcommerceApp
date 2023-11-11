package com.app;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password of SQLite database
    //private static final String JDBC_URL = "jdbc:sqlite:C:/Users/arjun/Documents/GitHub/BezosProject/accounts.db";

    public LoginServlet() {
        super();
    }
    
    public void init() throws ServletException {
        // Load the SQLite JDBC driver class when the servlet is initialized
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		out.print("Username: " + userName);
		out.print("Password: " + password);
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        // Check the credentials and store them in the database
        if (validateUser(userName, password)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("Login successful!");
            response.sendRedirect("Search.html");

            // You can add code here to perform further actions after a successful login.
        } else {
            response.sendRedirect("Login.html"); // Redirect back to the login page on failure
        }
    }
    
    
    private void createUsersTable() {
        try (Connection conn = DatabaseConnection.connect();
             Statement statement = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    private boolean validateUser(String userName, String password) {
        try {
        	Connection conn = DatabaseConnection.connect();
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
