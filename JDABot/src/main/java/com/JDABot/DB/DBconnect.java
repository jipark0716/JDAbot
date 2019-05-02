package com.JDABot.DB;

import java.sql.*;
import java.util.*;

public class DBconnect {
	
	private static ResultSet res = null;
	private static PreparedStatement pstmt = null;
	private static Connection conn = null;
	private static Vector<String> result = new Vector<String>();
	
	public static Connection getConn(){
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe","asdf","1234");
		} catch (Exception e) {
			System.out.println("연결오류");
		}
		return conn;
	}
	public static ResultSet sendQuery(String query){
		if(conn == null)getConn();
		try {
			pstmt = conn.prepareStatement(query);
			res = pstmt.executeQuery();
		} catch (Exception e) {
			System.out.println("쿼리문 오류");
		}
		return res;
	}
	public static Vector<String> sendQueryVecter(String query,int length){
		sendQuery(query);
		try {
			res.next();
			for(int i = 0 ; i <length;i++) {
				result.add(res.getString(i+1));
			}
			res.close();
		} catch (Exception e) {}
		return result;
	}
	public static String sendQuery(String query,int mode){
		sendQuery(query);
		try {
			res.next();
			return res.getString(mode);
		} catch (Exception e) {}
		return "";
	}
	
}
