package com.app.ItemService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.app.Objects.Item;
import com.app.Objects.ItemDAO;

public class NewItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		
		String itemName = request.getParameter("itemName");
		String price = request.getParameter("price");
		String type = request.getParameter("auctionType");
		double shipping = Double.valueOf(request.getParameter("shipping"));
		String desc = request.getParameter("description");
		double price_val = Double.valueOf(price);
		
		Item item = new Item(0, itemName, "", "", price_val, type, "", desc, shipping, 0);
		itemDAO.create(item, username);
		response.sendRedirect("Search.html");
	}

}
