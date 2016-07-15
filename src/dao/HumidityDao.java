package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Humidity;
import JDBC.DBHelper;

public class HumidityDao {
	public List<Humidity> searchHumidity()
	{
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		List<Humidity> humidityList = new ArrayList<Humidity>();
		
		PreparedStatement pst;
		try {
			String sql = "select humidity,datetime,period,point from humidity";
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				Humidity humidity = new Humidity();
				humidity.setHumidity(rs.getDouble("humidity"));
				humidity.setDatetime(rs.getTimestamp("datetime"));
				humidity.setPeriod(rs.getString("period"));
				humidity.setPoint(rs.getInt("point"));
				humidityList.add(humidity);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return humidityList;
		
	}
	
	public Humidity searchHumidityByPeriod(String period)
	{
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		Humidity humidity = new Humidity();
		
		PreparedStatement pst;
		try {
			String sql = "select humidity,datetime,period,point from humidity where period=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, period);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				humidity.setHumidity(rs.getDouble("humidity"));
				humidity.setDatetime(rs.getTimestamp("datetime"));
				humidity.setPeriod(rs.getString("period"));
				humidity.setPoint(rs.getInt("point"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return humidity;
		
	}
	
	public List<Humidity> searchHumidityWithPoint(int point)
	{
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		List<Humidity> humidityList = new ArrayList<Humidity>();
		
		PreparedStatement pst;
		try {
			String sql = "select humidity,datetime,period,point from humidity where point=? order by datetime desc limit 10";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, point);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				Humidity humidity = new Humidity();
				humidity.setHumidity(rs.getDouble("humidity"));
				humidity.setDatetime(rs.getTimestamp("datetime"));
				humidity.setPeriod(rs.getString("period"));
				humidity.setPoint(rs.getInt("point"));
				humidityList.add(humidity);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return humidityList;
		
	}
	
	public List<Humidity> searchLastHumidity()
	{
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		List<Humidity> humidityList = new ArrayList<Humidity>();
		
		PreparedStatement pst;
		try {

//			String sql = "select humidity,datetime,point from humidity where datetime in (select max(datetime) from humidity group by point) order by point asc";
//			String sql = "select max(datetime)datetime, point,humidity from humidity group by point";
			String sql = "select a.* from humidity a,(select point,max(datetime) datetime from humidity group by point) b where a.datetime = b.datetime and a.point = b.point order by a.point asc";
//			select a.* from table a,
//			(select 姓名,max(开始日期) 日期 from table group by 姓名) b where a.姓名=b.姓名 and a.开始日期=b.日期
			
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				Humidity humidity = new Humidity();
				humidity.setHumidity(rs.getDouble("humidity"));
				humidity.setDatetime(rs.getTimestamp("datetime"));
				humidity.setPoint(rs.getInt("point"));
				humidityList.add(humidity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return humidityList;
		
	}
	
	public int saveHumidity(Humidity humidity){
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		String sql = "insert into humidity(humidity,datetime,period,point) values(?,?,?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setDouble(1, humidity.getHumidity());
			pst.setTimestamp(2, humidity.getDatetime());
			pst.setString(3,humidity.getPeriod());
			pst.setInt(4,humidity.getPoint());
			
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
	
	public void deleteHumidity(String period){
		DBHelper db = new DBHelper();
		Connection conn = db.getConnection();
		String sql = "delete from humidity where period=?";
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
