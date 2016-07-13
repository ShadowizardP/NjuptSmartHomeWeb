package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import bean.CameraSnaps;
import dao.CameraSnapDao;

/**
 * Servlet implementation class UploadImage
 */
//@WebServlet("/UploadImage")
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = this.getServletContext().getRealPath("/images");
        
        File f = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!f.exists() && !f.isDirectory()) {
            System.out.println(savePath+"目录不存在，需要创建");
            //创建目录
            f.mkdir();
        }
		
		 //实例化上传组件
	    SmartUpload upload = new SmartUpload();
	    upload.setCharset("UTF-8");
	    //初始化上传组件
	    upload.initialize(this.getServletConfig(), request, response);
	    //开始上传
	    
		try {
			upload.upload();
		} catch (SmartUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    //获取上传的文件列表对象
//	    Files f = upload.getFiles();
	    //获取文件对象
	    com.jspsmart.upload.File file =upload.getFiles().getFile(0);
	    //去掉文件后缀
//	    String ext = file.getFileExt();
	    //判断文件类型是否是jpg格式jpg,gif,bmp,png,JPG,GIF,BMP,PNG
//	     if (!(ext.equals("jpg")) && !(ext.equals("gif")) && !(ext.equals("bmp")) && !(ext.equals("png")) && !(ext.equals("JPG")) && !(ext.equals("GIF")) && !(ext.equals("BMP")) && !(ext.equals("PNG"))) {
//	            out.println("<script type='text/javascript'>alert('文件类型错误');location.replace('upLoadPhoto.jsp');</script>");
//	            return;
//	     }
//	    JSONObject returnObj = new JSONObject();
	     
	    //满足条件进行文件的上传uploadImages在webRoot文件夹下的一个目录
	    try {
			file.saveAs("/images/" + file.getFileName());
//			returnObj.put("success", true);
			
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			returnObj.put("success", false);
		}
	    String img = "/images/" + file.getFileName(); //保存到数据库的路径 
	    String oTime = request.getParameter("time");
	    String flag = request.getParameter("flag");
//	    System.out.println(oTime);
	    String time = oTime.substring(1, 5) + "-" + oTime.substring(5, 7) + "-" + oTime.substring(7, 9) + " " + oTime.substring(9, 11) + ":" + oTime.substring(11, 13) + ":" + oTime.substring(13, 15); 
//		System.out.println(time);
		Timestamp datetime = Timestamp.valueOf(time);
	    
		CameraSnapDao csDao = new CameraSnapDao();
	    CameraSnaps cameraSnaps = csDao.searchCameraSnapByDatetime(datetime);
	    if (flag.equals("0")) {
			cameraSnaps = new CameraSnaps();
			cameraSnaps.setDatetime(datetime);
			cameraSnaps.setCameraSnaps(img);
		}
	    else
	    {
	    	cameraSnaps.setCameraSnaps(cameraSnaps.getCameraSnaps() + ";" + img);
	    }
	    if (flag.equals("0"))
	    {
	    	csDao.saveCameraSnaps(cameraSnaps);
	    }
	    else {
			csDao.updateCameraSnaps(cameraSnaps);
		}
		
	    
//	    response.setCharacterEncoding("UTF-8");
//	    response.setContentType("application/json; charset=utf-8");
//	    PrintWriter out = null;
//	    try {
//	        out = response.getWriter();
//	        out.write(returnObj.toString());
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    } finally {
//	        if (out != null) {
//	            out.close();
//	        }
//	    }
	    
//	    response.sendRedirect("test.jsp");
		
		
	    
	}
	
	 


}
