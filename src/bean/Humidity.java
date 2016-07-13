package bean;

import java.sql.Timestamp;

public class Humidity {
	private double humidity;
	private Timestamp datetime;
	private String period;
	private int point;
	
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public Timestamp getDatetime() {
		return datetime;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}


}