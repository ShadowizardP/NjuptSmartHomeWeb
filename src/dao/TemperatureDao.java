package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Temperature;
import JDBC.DBHelper;

public class TemperatureDao {
	public List<Temperature> searchTemperature()
	{
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		List<Temperature> temperatureList = new ArrayList<Temperature>();
		
		PreparedStatement pst;
		try {
			String sql = "select temperature,datetime,period,point from temperature";
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				Temperature temperature = new Temperature();
				temperature.setTemperature(rs.getDouble("temperature"));
				temperature.setDatetime(rs.getTimestamp("datetime"));
				temperature.setPeriod(rs.getString("period"));
				temperature.setPoint(rs.getInt("point"));
				temperatureList.add(temperature);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temperatureList;
		
	}
	
	public Temperature searchTemperatureByPeriod(String period)
	{
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		Temperature temperature = new Temperature();
		
		PreparedStatement pst;
		try {
			String sql = "select temperature,datetime,period,point from temperature where period=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, period);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				temperature.setTemperature(rs.getDouble("temperature"));
				temperature.setDatetime(rs.getTimestamp("datetime"));
				temperature.setPeriod(rs.getString("period"));
				temperature.setPoint(rs.getInt("point"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temperature;
		
	}
	
	public List<Temperature> searchTemperatureWithPoint(int point)
	{
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		List<Temperature> temperatureList = new ArrayList<Temperature>();
		
		PreparedStatement pst;
		try {
			String sql = "select temperature,datetime,period,point from temperature where point=? order by datetime desc limit 10";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, point);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				Temperature temperature = new Temperature();
				temperature.setTemperature(rs.getDouble("temperature"));
				temperature.setDatetime(rs.getTimestamp("datetime"));
				temperature.setPeriod(rs.getString("period"));
				temperature.setPoint(rs.getInt("point"));
				temperatureList.add(temperature);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temperatureList;
		
	}
	
	public List<Temperature> searchLastTemperature()
	{
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		List<Temperature> temperatureList = new ArrayList<Temperature>();
		
		PreparedStatement pst;
		try {

			String sql = "select temperature,datetime,point from temperature where datetime in (select max(datetime) from temperature group by point) order by point asc";
//			String sql = "select temperature,datetime from temperature where datetime=(select max(datetime) from temperature)";
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				Temperature temperature = new Temperature();
				temperature.setTemperature(rs.getDouble("temperature"));
				temperature.setDatetime(rs.getTimestamp("datetime"));
				temperature.setPoint(rs.getInt("point"));
				temperatureList.add(temperature);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temperatureList;
		
	}
	
	public int saveTemperature(Temperature temperature){
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		String sql = "insert into temperature(temperature,datetime,period,point) values(?,?,?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setDouble(1, temperature.getTemperature());
			pst.setTimestamp(2, temperature.getDatetime());
			pst.setString(3,temperature.getPeriod());
			pst.setInt(4,temperature.getPoint());
			
			int i  = pst.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return 0;
	}
	
//	public int updateFlower(Flower flower){
//		DBHelper db = new DBHelper();
//		Connection conn = db.getConnection();
//		String sql = "update flower set fname=?,type=?,inf=?,meaning=?,price=?,amount=? where fno=?";
//		try {
//			PreparedStatement pst = conn.prepareStatement(sql);
//			
//			pst.setString(1, flower.getFname());
//			pst.setString(2, flower.getType());
//			pst.setString(3, flower.getInf());
//			pst.setString(4, flower.getMeaning());
//			pst.setFloat(5, flower.getPrice());
//			pst.setInt(6, flower.getAmount());
//			pst.setInt(7, flower.getFno());
//			
//			int i  = pst.executeUpdate();
//			return i;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//		}
//		return 0;
//	}
//	
//	public int updateFlowerPic(String fno,String img){
//		DBHelper db = new DBHelper();
//		Connection conn = db.getConnection();
//		String sql = "update flower set img=? where fno=?";
//		try {
//			PreparedStatement pst = conn.prepareStatement(sql);
//			
//			
//			pst.setString(1, img);
//			pst.setString(2, fno);
//			
//			int i  = pst.executeUpdate();
//			return i;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//		}
//		return 0;
//	}
	
	public void deleteTemperature(String period){
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		String sql = "delete from temperature where period=?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, period);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
