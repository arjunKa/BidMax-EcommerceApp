package com.app.ItemService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.app.Objects.Item;
import com.app.Objects.ItemDAO;

public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JDBC URL, username, and password of SQLite database

	private static Item item;
	private ItemDAO itemDAO = new ItemDAO();

	public ItemServlet() {
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

		int id = Integer.valueOf(request.getParameter("itemId"));

		
		if (!itemDAO.isAuctionActive(request.getParameter("itemId"))) {
			response.sendRedirect("AuctionEnd.html?itemId=" + id);
			
		}else {
			response.sendRedirect("item.html?itemId=" + id);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		String type = request.getParameter("auctionTypeHidden");
		String itemId = request.getParameter("itemId");
		String currentPrice = request.getParameter("currentPriceHidden");
		System.out.print(type + currentPrice);
		
		
		if (!itemDAO.isAuctionActive(request.getParameter("itemId"))) {
			response.sendRedirect("AuctionEnd.html?itemId=" + itemId);
			return;
		}
		
		if (type.equals("forward")) {
			double amount = Double.parseDouble(request.getParameter("amount"));
			itemDAO.forwardAuction( itemId, username, amount);
			response.sendRedirect("item.html?itemId=" + itemId);
		} else if (type.equals("dutch")){
			itemDAO.dutchAuction(itemId, username);
			response.sendRedirect("AuctionEnd.html?itemId=" + itemId);			
		} else if (type.equals("japanese")){
			itemDAO.japaneseAuction(itemId, username, currentPrice);
			response.sendRedirect("AuctionEnd.html?itemId=" + itemId);			
		}

	}


}
