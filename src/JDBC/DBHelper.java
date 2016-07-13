package JDBC;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {

	Properties pro = new Properties();
	public DBHelper(){
		try {
			//this.getClass().getClassLoader().getResourceAsStream("db.properties")�Ǵ���ĸ�·����ȡ�ļ�
			InputStream ins = this.getClass().getClassLoader().getResourceAsStream("db.properties");
//			System.out.println("�������ǣ�"+ins);
			pro.load(ins);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Connection getConnection() {
//		String url="jdbc:mysql://localhost:3306/smarthomedb?user=root&password=root";
//		Connection con = null;
//		try {
//			con = DriverManager.getConnection(url);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return con;
		Connection conn = null;
		try {
			Class.forName(pro.getProperty("driver"));
			String url = pro.getProperty("url");
			String username =  pro.getProperty("username");
			String password = pro.getProperty("password");
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}