package com.echarts.bean;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Colors {

	public static Map<String, String[]> colorsMap = new HashMap<String, String[]>();

	static {
		try {
			File dbFile = new File("chartData.db");

			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"
					+ dbFile.getAbsolutePath());

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM COLOR");

			while (rs.next()) {
				colorsMap.put(rs.getString("NAME"), rs.getString("COLORS")
						.split(","));
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.toString());
		}
	}
}
