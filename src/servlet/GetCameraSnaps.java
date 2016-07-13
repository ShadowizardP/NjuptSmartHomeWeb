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

import bean.CameraSnaps;
import dao.CameraSnapDao;

/**
 * Servlet implementation class GetCameraSnaps
 */
//@WebServlet("/GetCameraSnaps")
public class GetCameraSnaps extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCameraSnaps() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		CameraSnapDao cDao = new CameraSnapDao();
		List<CameraSnaps> cameraSnapList = cDao.searchCameraSnap();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String jsonStr = "{\"data\":[";
		for(int i = 0 ; i < cameraSnapList.size() ; i++)
		{
			if(i != 0)
			{
				jsonStr += ",";
			}
			jsonStr += "{\"cameraSnaps\":\"" + cameraSnapList.get(i).getCameraSnaps() + "\",";
			jsonStr += "\"time\":\"" + sdf.format(cameraSnapList.get(i).getDatetime()) + "\"}";
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
