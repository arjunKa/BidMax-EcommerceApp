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

import com.app.Objects.User;

import java.io.IOException;

public class PaymentProcessingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PaymentProcessingServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String itemId = request.getParameter("itemId");
		response.sendRedirect("Receipt.html?itemId=" + itemId);

	}
	
	

	// Utility method to redirect to the error page with a message
	private void redirectToErrorPage(HttpServletResponse response, String message) throws IOException {
		String encodedMessage = URLEncoder.encode(message, "UTF-8");
		response.sendRedirect("error.jsp?message=" + encodedMessage);
	}
}
