package com.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

import java.io.PrintWriter;
import java.net.URLEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuctionEndedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuctionEndedServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Initialize variables
		boolean isWinner = false;
		String username = null;
		double highestBid = 0.0;
		int itemId = 0;
		String itemDescription = null;
		String winningUsername = null;
		double Price = 0.0;
		double expeditedCost = 0.0;

		// HttpSession session = request.getSession();
		// session.setAttribute("username", "yr" );

		try {
			Class.forName("org.sqlite.JDBC");

			try (Connection connection = DatabaseConnection.connect()) {
				HttpSession session = request.getSession(false); // Retrieve the existing session
				if (session != null) {
					username = (String) session.getAttribute("username");
					if (username != null) {
						// Query to get the bids by the logged-in user
						String userBidsQuery = "SELECT item_id, bid_amount FROM bids WHERE username = ?";
						try (PreparedStatement userBidsStmt = connection.prepareStatement(userBidsQuery)) {
							userBidsStmt.setString(1, username);
							ResultSet userBidsRs = userBidsStmt.executeQuery();

							// Process the bids made by the user
							while (userBidsRs.next()) {
								itemId = userBidsRs.getInt("item_id");
								double amountBid = userBidsRs.getDouble("bid_amount");

								// Query to find the highest bid for the item
								String highestBidQuery = "SELECT username, MAX(bid_amount) AS highest_bid FROM bids WHERE item_id = ? GROUP BY item_id";
								try (PreparedStatement highestBidStmt = connection.prepareStatement(highestBidQuery)) {
									highestBidStmt.setInt(1, itemId);
									ResultSet highestBidRs = highestBidStmt.executeQuery();

									if (highestBidRs.next()) {
										highestBid = highestBidRs.getDouble("highest_bid");
										winningUsername = highestBidRs.getString("username");

										// Check if the logged-in user is the winner
										isWinner = username.equals(winningUsername) && amountBid == highestBid;
									}
								}
							}
						}

						String itemDescriptionQuery = "SELECT description FROM items WHERE id = ?";
						try (PreparedStatement itemDescriptionStmt = connection
								.prepareStatement(itemDescriptionQuery)) {
							itemDescriptionStmt.setInt(1, itemId);
							try (ResultSet itemDescriptionRs = itemDescriptionStmt.executeQuery()) {
								if (itemDescriptionRs.next()) {
									itemDescription = itemDescriptionRs.getString("description");
								}
							}

						}

						String shippingCostQuery = "SELECT shipping FROM items WHERE id = ?";

						try (PreparedStatement shippingCostStmt = connection.prepareStatement(shippingCostQuery)) {
							shippingCostStmt.setInt(1, itemId);
							try (ResultSet shippingCostRs = shippingCostStmt.executeQuery()) {
								if (shippingCostRs.next()) {
									Price = shippingCostRs.getDouble("shipping");
								}
							}
						}

						String expeditedShippingCostQuery = "SELECT price FROM items WHERE id = ?";

						try (PreparedStatement expeditedShippingCostStmt = connection
								.prepareStatement(expeditedShippingCostQuery)) {
							expeditedShippingCostStmt.setInt(1, itemId);
							try (ResultSet expeditedShippingCostRs = expeditedShippingCostStmt.executeQuery()) {
								if (expeditedShippingCostRs.next()) {
									expeditedCost = expeditedShippingCostRs.getDouble("price");
								}
							}
						}

//                        String expeditedShippingCostQuery = "SELECT expedited_shipping FROM items WHERE item_id = ?";
//                      
//                        try (PreparedStatement expeditedShippingCostStmt = connection.prepareStatement(expeditedShippingCostQuery)) {
//                            expeditedShippingCostStmt.setInt(1, itemId);
//                            try (ResultSet expeditedShippingCostRs = expeditedShippingCostStmt.executeQuery()) {
//                                if (expeditedShippingCostRs.next()) {
//                                	expeditedCost = expeditedShippingCostRs.getDouble("expedited_shipping");
//                                } 
//                            }
//                        }

						// Set session attributes for the winner

						session.setAttribute("username", username);
						session.setAttribute("highestBid", highestBid);
						session.setAttribute("itemId", itemId);
						session.setAttribute("itemDescription", itemDescription);
						session.setAttribute("isWinner", isWinner);
						session.setAttribute("winningUsername", winningUsername);
						session.setAttribute("shippingCost", Price);
						session.setAttribute("expeditedCost", expeditedCost);

						// Redirect to a receipt or confirmation page if the user is the winner
						response.sendRedirect("AuctionEnded.jsp"); // or use RequestDispatcher if you need to forward
																	// request attributes

					} else {
						// No username attribute in session, redirect to login or error page
						redirectToErrorPage(response, "You must be logged in to view this page.");
					}
				} else {
					// No session, redirect to login or error page
					redirectToErrorPage(response, "No session found. Please log in.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				redirectToErrorPage(response, "Database error occurred.");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			redirectToErrorPage(response, "JDBC driver not found.");
		}
	}

	private void redirectToErrorPage(HttpServletResponse response, String message) throws IOException {
		String encodedMessage = URLEncoder.encode(message, "UTF-8");
		response.sendRedirect("error.jsp?message=" + encodedMessage);
	}
}
