package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Temperature;
import bean.Humidity;
import dao.TemperatureDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import dao.HumidityDao;

/**
 * Servlet implementation class GetTemperatureAndHumidity
 */
//@WebServlet("/GetTemperatureAndHumidity")
public class GetTemperatureAndHumidity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTemperatureAndHumidity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		TemperatureDao tDao = new TemperatureDao();
		List<Temperature> temperatureList = tDao.searchLastTemperature();
		HumidityDao hDao = new HumidityDao();
		List<Humidity> humidityList = hDao.searchLastHumidity();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String jsonStr = "{\"temperature\":[";
		for(int i = 0 ; i < temperatureList.size() ; i++)
		{
			if(i != 0)
			{
				jsonStr += ",";
			}
			jsonStr += "{\"point\":" + Integer.toString(temperatureList.get(i).getPoint()) + ",";
			jsonStr += "\"temperature\":" + Double.toString(temperatureList.get(i).getTemperature()) + ",";
			jsonStr += "\"time\":\"" + sdf.format(temperatureList.get(i).getDatetime()) + "\"}";
		}
		jsonStr += "],\"humidity\":[";
		for(int i = 0 ; i < humidityList.size() ; i++)
		{
			if(i != 0)
			{
				jsonStr += ",";
			}
			jsonStr += "{\"point\":" + Integer.toString(humidityList.get(i).getPoint()) + ",";
			jsonStr += "\"humidity\":" + Double.toString(humidityList.get(i).getHumidity()) + ",";
			jsonStr += "\"time\":\"" + sdf.format(humidityList.get(i).getDatetime()) + "\"}";
		}
		jsonStr += "]}";
		
		
		
//		 JSONObject jsonObject = new JSONObject();  
//	        jsonObject.put("temperature", tem);  
//	          
//	        JSONArray jsonArray = new JSONArray();  
//	        jsonArray.add(jsonObject);  
//	        System.out.println(jsonArray);  
//	        PrintWriter out = response.getWriter();  
//	        out.write(jsonArray.toString());  
	        
	        PrintWriter out = null;
	        try {
	            out = response.getWriter();
	            out.write(jsonStr);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (out != null) {
	                out.close();
	            }
	        }
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
