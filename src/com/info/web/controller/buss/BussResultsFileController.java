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

import com.info.common.util.SystemCurrentUser;
import com.info.domain.Result;
import com.info.domain.TAccessories;
import com.info.domain.TProjectResultsFile;
import com.info.service.BussResultsFileService;
import com.info.web.CurrentUser;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussResultsFileController extends BaseController {
	
	@Autowired
	BussResultsFileService service;
	
	@RequestMapping(value = "/Buss/ResultsLoadFileService/{action}")
	public void getDataService(@PathVariable("action") Integer action,//@PathVariable("sessionid") String sessionid,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
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
    		    Result = IsLoadFiles();
    		    break;
    		case 5:
                Result = getResultsFileListForTaskID();
                break;
		}
		writeJsonString(Result);
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
		String order=getString("", "asc");
		Integer page=getInt("page", 1);
		Integer rows=getInt("rows", 10);		
		Integer fkCheckReviewId= getInt("fkCheckReviewId", -1);
		List<TProjectResultsFile> objList = service.GetProjectResultsFileList(fkCheckReviewId,order,page,rows);
		    ResultJson.setRows(objList);
		
		ResultJson.setTotal(Long.valueOf(objList.size()));		      	    
		return getJsonFromObj(ResultJson);		   		
	}
	private String getResultsFileListForTaskID(){       
        EasyDataGrid ResultJson= new EasyDataGrid();
        String order=getString("", "asc");
        Integer page=getInt("page", 1);
        Integer rows=getInt("rows", 10);        
        Integer taskid= getInt("TaskId", -1);
        List<TProjectResultsFile> objList = service.GetProjectResultsFileListForTaskId(taskid,order,page,rows);
            ResultJson.setRows(objList);
        
        ResultJson.setTotal(Long.valueOf(objList.size()));                  
        return getJsonFromObj(ResultJson);              
    }
	/**
	 * 
	 * @Description	: 判断文件是否已上传
	 * @Author		: jibb
	 * @Date		: 2013-03-26 18-31
	 * @return
	 */
	private String IsLoadFiles(){
	    String SubmitMaterials=getString("SubmitMaterials", "");
	    Integer ID=getInt("ID", -1);
	    Result resultObj = new Result();
	    boolean flag=false;
	    resultObj.setId(ID);
        resultObj.setSuccess(false);
	    List<TProjectResultsFile> pRFList=service.GetProjectResultsFileList(ID, "", 1, 10);
	    String[] TempSMArray=SubmitMaterials.split(",");
	    if(null!=pRFList){
	        resultObj.setId(ID);	       
	        resultObj.setMsg("");
	        for (String string : TempSMArray) {
	            boolean isFind=false;
	            for(int i=0;i<pRFList.size();i++){
	                String temprft=pRFList.get(i).getFResultsFileType(); 
                    if(string.equals(temprft))
                    {
                        isFind=true;
                        flag=true;
                        break;
                    }
                }
	            if(!isFind){
	              flag=flag&&isFind;
	              resultObj.setMsg(resultObj.getMsg()+"<br/>["+string+"]类型的文件未上传,请上传此类文件!"); 
	            }
            }
	        resultObj.setSuccess(flag);
	    }
	   return getJsonFromObj(resultObj);
	}
	
	private  String loadFile(HttpServletRequest request) throws Exception {
		boolean result=true;
		ResultMessage rm=new ResultMessage();
		Integer FId;
		String id; 
		Integer fkCheckReviewId; 
		String FResultsFileType;
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
				FId=Integer.parseInt((request.getParameter("FId")==null ?-1:request.getParameter("FId")).toString());
				id=request.getParameter("id"); 
				fkCheckReviewId=Integer.parseInt((request.getParameter("fkCheckReviewId")==""?-1:request.getParameter("fkCheckReviewId")).toString()); 
				FResultsFileType= java.net.URLDecoder.decode(
						(request.getParameter("FResultsFileType") ==""?"":request.getParameter("FResultsFileType"))
						,"UTF-8");//new String(request.getParameter("FResultsFileType").getBytes("ISO8859-1"), "UTF-8");
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
				
			    CurrentUser objUser=SystemCurrentUser.getCurrentUser();
				TProjectResultsFile objFile=new TProjectResultsFile();
				objFile.setFId(FId);
				objFile.setFkCheckReviewId(fkCheckReviewId);
				objFile.setFResultsFileType(FResultsFileType);
				objFile.setFFileName(FFileName);				
				objFile.setFFileType(FFileType);
				objFile.setFSize(Double.valueOf(FSize));
				
				objFile.setFState(FState);
				objFile.setFLoadUserId(objUser.getUserID());
				objFile.setFLoadUserName(objUser.getUserName());
				objFile.setFLoadDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
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
	
	@RequestMapping(value = "/Buss/ResultsLoadFileService/LoadFile/{fkid}")
	public void downLoad(@PathVariable("fkid") Integer fkid,HttpServletResponse response, HttpServletRequest request) throws Exception {
		TAccessories AccessoriesObj=new TAccessories();			
		try {
			AccessoriesObj = service.getAccessories(fkid);
			TProjectResultsFile ProjectResultsFileObj= service.getProjectResultsFile(fkid);
			
			byte[] fileByte=AccessoriesObj.getFAccessories();
			if(fileByte.length>0){
				
				//判断浏览器 
				String agent = request.getHeader("USER-AGENT"); 
				if (null != agent && -1 != agent.indexOf("MSIE")){//IE 
					// 设置文件头，文件名称或编码格式 
					response.setHeader("Content-Disposition", "attachment;filename=\"" + java.net.URLEncoder.encode(ProjectResultsFileObj.getFFileName(), "UTF-8") + "\"");
					//response.addHeader("Content-Disposition", "attachment;filename="+ new String((ProjectResultsFileObj.getFFileName()).getBytes("GBK"), "ISO-8859-1"));
				}else{//firefox 
					response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(ProjectResultsFileObj.getFFileName().getBytes("UTF-8"),"iso-8859-1") + "\""); 
				} 
				
				//response.addHeader("Content-Disposition", "attachment;filename="+ new String((ProjectResultsFileObj.getFFileName()).getBytes("GBK"), "ISO-8859-1"));
	            //response.addHeader("Content-Transfer-Encoding", "binary");  
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
	@RequestMapping(value = "/Buss/ResultsLoadFileService/DeleteFile/{fkid}")
    public void DeleteResultFileForId(@PathVariable("fkid") Integer fkid,HttpServletResponse response, HttpServletRequest request) throws Exception {
        TAccessories AccessoriesObj=new TAccessories(); 
        CurrentResponse = response;
        boolean flag=false;
        Result resultObj = new Result();
        try {
            AccessoriesObj = service.getAccessories(fkid);
            //TProjectResultsFile ProjectResultsFileObj= service.getProjectResultsFile(fkid);
            if(null!=AccessoriesObj)
            {
                //
                flag=service.DeleteAccessories(AccessoriesObj.getFId());
                flag=service.DeleteProjectResultsFile(fkid);
            }
            resultObj.setId(0);
            resultObj.setSuccess(flag);
            resultObj.setMsg("附件删除成功。");
        } catch (Exception e) {
            //e.printStackTrace();
            writeJsonString(getJsonFromObj(resultObj));
        }
        writeJsonString(getJsonFromObj(resultObj));
        
    }
	@RequestMapping(value = "/Buss/ResultsLoadFileService/GetFiles/{fkid}")
	public void getFileForId(@PathVariable("fkid") Integer fkid,HttpServletResponse response, HttpServletRequest request) throws Exception {
		TAccessories AccessoriesObj=new TAccessories();			
		try {
			AccessoriesObj = service.getAccessories(fkid);
			TProjectResultsFile ProjectResultsFileObj= service.getProjectResultsFile(fkid);
			
			byte[] fileByte=AccessoriesObj.getFAccessories();
			if(fileByte.length>0){
				
				response.addHeader("Content-Disposition", "attachment;filename="+ new String((ProjectResultsFileObj.getFFileName()).getBytes("GBK"), "ISO-8859-1"));
	            //response.addHeader("Content-Transfer-Encoding", "binary");
				response.setHeader("Cache-Control","no-store, max-age=0, no-cache, must-revalidate");
				response.addHeader("Cache-Control", "post-check=0, pre-check=0");
				response.setHeader("Pragma", "no-cache");
	            response.setContentType("image/jpeg");//"application/octet-stream");//MIME类型             
	            OutputStream outs = response.getOutputStream();  
	            outs.write(fileByte);  
	            outs.flush();  
	            outs.close();
	            return;	             
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
