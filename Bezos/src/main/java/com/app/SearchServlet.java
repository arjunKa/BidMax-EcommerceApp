package com.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.app.Objects.Item;
import com.app.Objects.ItemDAO;

//@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC URL, username, and password of SQLite database

	private static List<Item> items;
	private ItemDAO itemDAO = new ItemDAO();

	public SearchServlet() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		response.sendRedirect("Search.html");

	}

	private void createItemsTable() {
		try (Connection conn = DatabaseConnection.connect(); Statement statement = conn.createStatement()) {
			String createTableSQL = "CREATE TABLE IF NOT EXISTS items ("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + "name TEXT," + "price DECIMAL(10,2),"
					+ "type TEXT," + "endtime DATETIME," + "description TEXT,"
					+ "shipping DECIMAL(10,2), sold BIT DEFAULT 0);"; // Added
			// description
			// and shipping
			// columns
			statement.executeUpdate(createTableSQL);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
