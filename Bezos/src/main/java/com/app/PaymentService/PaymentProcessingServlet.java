package com.app.PaymentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
}
