package com.app;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseConnection {
	public static Connection connect() {
		Connection conn = null;
		try {
			// Obtain our environment naming context
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			// Look up our data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/Bezos");
			// Allocate and use a connection from the pool
			conn = ds.getConnection();
		} catch (SQLException | NamingException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
}