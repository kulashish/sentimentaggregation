package com.ibm.irl.sentiment.restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.irl.sentiment.util.DataAccessException;

public class RestaurantDataAccess {

	public Connection getConnection() throws DataAccessException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/restaurant?"
							+ "user=sqluser&password=sqluser");
		} catch (ClassNotFoundException e) {
			throw new DataAccessException(e);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return conn;
	}

	public String getRestaurantId(String reviewId) throws DataAccessException {
		String restId = null;
		Connection conn = getConnection();
		Statement query = null;
		try {
			query = conn.createStatement();
			ResultSet rs = query
					.executeQuery("select business_id from review where review_id = '"
							+ reviewId + "'");
			if (rs != null && rs.next())
				restId = rs.getString(1);

		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			try {
				query.close();
				conn.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
		return restId;
	}

	public static void main(String[] args) {
		RestaurantDataAccess access = new RestaurantDataAccess();
		try {
			System.out
					.println(access.getRestaurantId("-pqrmUPwxT7ONheZZ63Tvg"));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
}
