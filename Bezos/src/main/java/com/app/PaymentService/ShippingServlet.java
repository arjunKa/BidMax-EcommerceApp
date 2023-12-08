package com.app.PaymentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.app.Objects.Item;
import com.app.Objects.ItemDAO;

public class ShippingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC URL, username, and password of SQLite database

	private static Item item;
	private ItemDAO itemDAO = new ItemDAO();

	public ShippingServlet() {
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

		HttpSession session = request.getSession();
		int id = Integer.valueOf(request.getParameter("itemId"));
		String username = session.getAttribute("username").toString();
		String total = request.getParameter("totalPaid");
		itemDAO.purchase(id, username, total);
		response.sendRedirect("shipping.html?itemId=" + id);

	}

}
