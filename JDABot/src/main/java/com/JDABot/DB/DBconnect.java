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
//			pstmt.close();
		} catch (Exception e) {
			System.out.println("쿼리문 오류 : "+e.getMessage());
		}
		return res;
	}
	public static Vector<String> sendQueryVecter(String query,int length){
		sendQuery(query);
		try {
			if(res.next()) {}else return null;
			for(int i = 0 ; i <length;i++) {
				String temp = res.getString(i+1);
				result.add(temp==null?"":temp);
			}
			res.close();
		} catch (Exception e) {}
		return result;
	}
	public static String sendQuery(String query,int mode){
		sendQuery(query);
		try {
			if(res.next()) return null;
			String temp = res.getString(mode);
			return temp==null?"":temp;
		} catch (Exception e) {}
		return "";
	}
	
}
