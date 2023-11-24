package com.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.app.DatabaseConnection;

import java.io.IOException;

public class PaymentProcessingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    
    public PaymentProcessingServlet() {
        super();
    }
    
    
    
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        try {  
        	
        	Class.forName("org.sqlite.JDBC");
        	
        	
        try (Connection connection = DatabaseConnection.connect()) {
        	
        	 HttpSession session = request.getSession(false);
        	 
        	 if (session == null || session.getAttribute("username") == null) {
        	        redirectToErrorPage(response, "Session not found or user not logged in.");
        	        return;
        	    }
        	 
        	 String itemDescription = (String) session.getAttribute("itemDescription");
        	 String username = (String) session.getAttribute("username");
        	 
        	 boolean expeditedShippingSelected = "on".equals(request.getParameter("expedited"));
        	 
        	 
        	 int itemId =  (int) session.getAttribute("itemId");
            double winningprice= (double) session.getAttribute("highestBid");
            String winningUsername=  (String) session.getAttribute("winningUsername");

            String shippingTime = getShippingTime(connection, itemId);
            
            
            int highestBidderId = getUserIdFromName(connection, winningUsername);
            
            if (highestBidderId == -1) {
                redirectToErrorPage(response, "User not found.");
                return;
            }

            if (username.equals(winningUsername)) {
                double totalCost = calculateTotalCost(connection, itemId, expeditedShippingSelected, winningprice);
                User winnerDetails = getWinnerDetails(connection, highestBidderId);

                
      
                
           
                session.setAttribute("totalCost", totalCost);
                session.setAttribute("winnerDetails", winnerDetails);
                session.setAttribute("shippingTime", shippingTime);
                session.setAttribute("itemId", itemId);
                
                
                
                response.sendRedirect("Receipt.jsp"); // if it does not work use dispatcher
               
            }
             else {
                redirectToErrorPage(response, "You are not the highest bidder.");
                
            }
            
           }
        
    } catch (SQLException e) {
            // Log the exception and redirect to an error page
            e.printStackTrace(); // Replace with a logger in production
            redirectToErrorPage(response, "Database error occurred.");
        } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }

    

    private String getShippingTime(Connection connection, int itemId) throws SQLException {
        String sql = "SELECT shipping_time FROM items WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("shipping_time");
                } else {
                    return "Shipping time not available"; // Default message or throw exception
                }
            
            }
        }
    }
        
    
    private int getUserIdFromName(Connection connection, String highestBidderName) throws SQLException {
        String sql = "SELECT id FROM users WHERE username = ?"; // Assumes username is unique
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, highestBidderName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Return an int
                }
            }
        }
        return -1; // Return -1 to indicate user not found or error occurred
    }


  


    
    private User getWinnerDetails(Connection connection, int userId) throws SQLException {
        String sql = "SELECT id, firstName, lastName, email, address, postalCode, city, country, username FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId); // Set the user ID as an integer
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"), // Assuming the constructor's first parameter is of type int
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("postalCode"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getString("username")
                    );
                }
            }
        }
        return null; // User details not found or error occurred
    }


    private double calculateTotalCost(Connection connection, int itemId, boolean expeditedShipping, double winningPrice) throws SQLException {
        double totalCost = winningPrice; // Start with the winning price
        if (expeditedShipping) {
            // Get the expedited shipping cost from the items table
            String sql = "SELECT shipping FROM items WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, itemId); // Set int instead of String
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        totalCost += rs.getDouble("shipping"); // Add the expedited shipping cost
                    }
                }
            }
        }
        return totalCost; // Return the total cost
    }

    
    // Utility method to redirect to the error page with a message
    private void redirectToErrorPage(HttpServletResponse response, String message) throws IOException {
        String encodedMessage = URLEncoder.encode(message, "UTF-8");
        response.sendRedirect("error.jsp?message=" + encodedMessage);
    }
}

