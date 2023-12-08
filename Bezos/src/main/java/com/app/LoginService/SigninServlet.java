package com.app.LoginService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.app.Objects.User;
import com.app.Objects.UserDAO;

public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userDAO = new UserDAO();

	public SigninServlet() {
		super();
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

		String output = userDAO
				.signUp(new User(firstName, lastName, email, address, postalCode, city, country, username, password));

		if (!output.equals("")) {
			PrintWriter out = response.getWriter();
			out.println(output);
			return;
		}
		// Redirect to login page
		response.sendRedirect("Login.html");
	}

}
