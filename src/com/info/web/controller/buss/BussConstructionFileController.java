package com.info.web.controller.buss;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.info.domain.TConstructionAccessories;
import com.info.domain.TConstructionFile;
import com.info.service.BussConstructionFileService;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
@Controller
public class BussConstructionFileController extends BaseController {
	@Autowired
	BussConstructionFileService service;
	
	ResultMessage message;
	@RequestMapping(value = "/Buss/ConstructionFileService/{action}")
	public void getDataService(@PathVariable("action") Integer action,//@PathVariable("sessionid") String sessionid,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();
		switch (action) {
		case 1:
			Result = loadFile(request);
			break;
		case 2:
			Result = getFlashSession(request);
			break;
		case 3:
			Result = getResultsFileList();
			break;
		case 4:
			Result = deleteFile();
			break;
		case 5://视频的添加附件方法
		    Result=videoInsert();
		    break;
		}
		writeJsonString(Result);
	}
	
	private String deleteFile() {
		Integer id = getInt("FId", 0);
		if( id > 0){
			if(service.Delete(id)){
				message.setSuccess(true);
			}else{
				message.setSuccess(false);
			}
		}
		return getJsonFromObj(message);
	}

	private String getFlashSession(HttpServletRequest request){
		ResultMessage rm=new ResultMessage();
		String sessionID=request.getSession().getId();
		rm.setSuccess(true);
		rm.setRoot(sessionID);
		return this.getJsonFromObj(rm);
	}
	
	private String getResultsFileList(){		
		EasyDataGrid ResultJson= new EasyDataGrid();
		Integer page=getInt("page", 1);
		Integer rows=getInt("rows", 10);		
		Integer fkId= getInt("fkId", -1);
		List<TConstructionFile> objList = service.GetProjectResultsFileList(fkId, page, rows);
		if(objList.size() > 0){
			ResultJson.setRows(objList);
			ResultJson.setTotal(Long.valueOf(objList.size()));
		}else{
			ResultJson.setRows(objList);
			ResultJson.setTotal(Long.valueOf(0));
		}      	    
		return getJsonFromObj(ResultJson);		   		
	}
	/**
	 * 
	 * @Description	: 视频的添加方法
	 * @Author		: chunlei
	 * @Date		: 2013-03-22 11-46
	 * @return
	 */
	private String videoInsert() {
	    TConstructionFile file=new TConstructionFile();
	    file.setFFileName(getString("FFileName", ""));
	    file.setFFileSize(Double.parseDouble(getString("FFileSize", "0.0"))*1024);
	    file.setFFileType(getString("FFileType", ""));
	    file.setFkId(getInt("fkId", -1));
	    file.setFLoadDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	    file.setFState(getString("FState", "-4"));
	    file.setFFilePath(getString("FFilePath", ""));
	    try {
		if (service.intsert(file)) {
			message.setMessage("文件保存成功!");
			message.setSuccess(true);
		    }else {
			message.setMessage("保存失败！");
			message.setSuccess(false);
		    }
	    } catch (Exception e) {
		message.setMessage("系统错误！");
		message.setSuccess(false);
	    }
	    return getJsonFromObj(message);
	}
	//文件上传
	private  String loadFile(HttpServletRequest request) throws Exception {
		boolean result=true;
		ResultMessage rm=new ResultMessage();
		String tempString;
		Integer FId;
		String id; 
		Integer fkId; 
		String FFileName; 
		String FFileType; 
		String FSize; 
		String FProgress; 
		String FState;
		String rename="";
		String url=getString("url","");
		String base = this.getServletContext().getRealPath("")+url;	
		//List<TProjectResultsFile> entityFiles=new ArrayList<TProjectResultsFile>();
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<?> ttList = multipartRequest.getFiles("file");
			for (Object object : ttList) {
				tempString =request.getParameter("fileName");
				FId=Integer.parseInt((request.getParameter("FId")==null ?-1:request.getParameter("FId")).toString());
				id=request.getParameter("id"); 
				fkId=Integer.parseInt((request.getParameter("fkId")==""?-1:request.getParameter("fkId")).toString()); 
				//new String(request.getParameter("FResultsFileType").getBytes("ISO8859-1"), "UTF-8");
				//URLDecoder.decode(request.getParameter("FResultsFileType"),"UTF-8");
				FFileName= java.net.URLDecoder.decode(request.getParameter("FFileName").toString(),"UTF-8");//new String(request.getParameter("FFileName").getBytes("ISO8859-1"), "UTF-8"); //request.getParameter("FFileName"); 
				FFileType=request.getParameter("FFileType"); 
				FSize=request.getParameter("FSize"); 
				FProgress=request.getParameter("FProgress"); 
				FState="-4";//request.getParameter("FState");
				
				CommonsMultipartFile mfile = (CommonsMultipartFile) object;
				InputStream stream = mfile.getInputStream();
				String fileNames = mfile.getOriginalFilename();
				rename=FilenameUtils.getExtension(fileNames);				
				String fileNameFull = base  + rename;
				OutputStream bos = new FileOutputStream(fileNameFull);
				int bytesRead = 0;
				byte[] buffer = new byte[Integer.parseInt(FSize)];
				while ((bytesRead = stream.read(buffer, 0, Integer.parseInt(FSize))) != -1) {
					bos.write(buffer, 0, bytesRead);
				}				
				
			    TConstructionFile objFile=new TConstructionFile();
				objFile.setFId(FId);
				objFile.setFkId(fkId);
				objFile.setFFileName(FFileName);				
				objFile.setFFileType(FFileType);
				objFile.setFFileSize(Double.valueOf(FSize));
				objFile.setFState(FState);
				objFile.setFLoadDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				boolean success=service.Insert(objFile,buffer);
				result= success;				
				bos.close();
				stream.close();
			}			 
		}catch(Exception ex){
			ex.printStackTrace();
			result=false;
		}		
		rm.setSuccess(result);
		rm.setRoot(null);
		return this.getJsonFromObj(rm);	
	}
	
	@RequestMapping(value = "/Buss/ConstructionFileService/LoadFile/{fkid}")
	public void downLoad(@PathVariable("fkid") Integer fkid,HttpServletResponse response, HttpServletRequest request) throws Exception {
		TConstructionAccessories AccessoriesObj=new TConstructionAccessories();			
		try {
			AccessoriesObj = service.getAccessories(fkid);
			TConstructionFile ProjectResultsFileObj= service.getProjectResultsFile(fkid);
			byte[] fileByte=AccessoriesObj.getFAccessories();
			if(fileByte.length>0){				
				response.addHeader("Content-Disposition", "attachment;filename="+ new String((ProjectResultsFileObj.getFFileName()).getBytes("GBK"), "ISO-8859-1"));
	            response.addHeader("Content-Transfer-Encoding", "binary");  
	            response.setContentType("application/octet-stream");//MIME类型             
	            OutputStream outs = response.getOutputStream();  
	            outs.write(fileByte);  
	            outs.flush();  
	            outs.close(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		}
}
