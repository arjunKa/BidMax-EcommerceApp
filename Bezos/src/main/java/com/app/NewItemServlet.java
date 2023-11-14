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
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC URL, username, and password of SQLite database

	private static List<Item> items;
	private ItemDAO itemDAO = new ItemDAO();

	public NewItemServlet() {
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
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		try {
			String itemName = request.getParameter("itemName");
			String price = request.getParameter("price");
			String type = request.getParameter("auctionType");
			String endDate = request.getParameter("endTime");
			double shipping = Double.valueOf(request.getParameter("shipping"));
			String desc = request.getParameter("description");
			double price_val = Double.valueOf(price);
			System.out.println(endDate);
			
			Connection conn = DatabaseConnection.connect();
			String sql = "INSERT INTO items (name, price, type, endTime, description, shipping) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, itemName);
			preparedStatement.setDouble(2, price_val);
			preparedStatement.setString(3, type);
	        Date parsedDate;
	        try {
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	            parsedDate = formatter.parse(endDate);
	            
	        } catch (ParseException e) {
	            e.printStackTrace();
	            System.out.println("Error parsing datetime value.");
	            return;
	        }

	        // Convert the java.util.Date to java.sql.Timestamp
	        Timestamp sqlTimestamp = new Timestamp(parsedDate.getTime());
	        preparedStatement.setTimestamp(4, sqlTimestamp);
			
	        preparedStatement.setString(5, desc);
	        preparedStatement.setDouble(6, shipping);
			int rowsAffected = preparedStatement.executeUpdate();
			preparedStatement.close();
			conn.close();
			response.sendRedirect("Search.html");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}



}
