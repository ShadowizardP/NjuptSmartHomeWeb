package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Temperature;
import dao.TemperatureDao;;

/**
 * Servlet implementation class GetTemperatureListWithPoint
 */
//@WebServlet("/GetTemperatureListWithPoint")
public class GetTemperatureListWithPoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTemperatureListWithPoint() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String oPoint = request.getParameter("point");
		int point = Integer.parseInt(oPoint);
		TemperatureDao tDao = new TemperatureDao();
		List<Temperature> temperatureList = tDao.searchTemperatureWithPoint(point);
		response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String jsonStr = "{\"data\":[";
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
		jsonStr += "]}";
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
