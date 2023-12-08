package com.app.LoginService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import com.app.Objects.User;
import com.app.Objects.UserDAO;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userDAO = new UserDAO();

	public LoginServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("Login.html");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = userDAO.login(username, password);

		if (user == null) {
			PrintWriter out = response.getWriter();
			out.println("<html><body><h2>The username or password is incorrect. "
					+ "If you do not have an account, please sign up first!</h2></body></html>");
		} else {
			HttpSession session = request.getSession();
            session.setAttribute("username", username);

			response.sendRedirect("Main.html");
		}

	}

}
