package com.info.web.controller.buss;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.info.common.util.StringUtil;
import com.info.domain.TFiles;
import com.info.service.BussFileService;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussFileController extends BaseController{

	@Autowired
	BussFileService fileService;
	
	@RequestMapping(value = "/Buss/File/{action}")
	public void ActionServlet(@PathVariable("action") Integer action,
			HttpServletResponse response,HttpServletRequest request){
		CurrentResponse=response;
		StringBuilder str=new StringBuilder();		
		switch(action){
		case 1:
			str.append(UploadFiles(request));
			break;
		case 2:
			str.append(getFlashSession(request));
			break;
		case 3:
			str.append(delete());
			break;
		case 4:
			str.append(getFiles());
		}
		this.writeJsonString(str.toString());
	}
	
	private String getFlashSession(HttpServletRequest request){
		ResultMessage rm=new ResultMessage();
		String sessionID=request.getSession().getId();
		rm.setSuccess(true);
		rm.setRoot(sessionID);
		return this.getJsonFromObj(rm);
	}
	
	private String UploadFiles(HttpServletRequest request){
		boolean result=true;
		int FId=getInt("FId",0);
		String url=getString("url","");
		int TypeId=getInt("TypeId",0);
		String Type=getString("Type","");
		ResultMessage rm=new ResultMessage();
		List<TFiles> entitys=new ArrayList<TFiles>();
		String base = this.getServletContext().getRealPath("")+url;
		java.io.File dir=new java.io.File(base);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String rename="";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<?> ttList = multipartRequest.getFiles("Filedata");
			for (Object object : ttList) {
				CommonsMultipartFile mfile = (CommonsMultipartFile) object;
				InputStream stream = mfile.getInputStream();
				String fileNames = mfile.getOriginalFilename();
				rename=StringUtil.getUUID().replaceAll("-", "")+"."+FilenameUtils.getExtension(fileNames);
				String fileNameFull = base  + rename;
				OutputStream bos = new FileOutputStream(fileNameFull);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead);
				}
				
				
				TFiles entity=new TFiles();
				entity.setFFileName(FilenameUtils.getBaseName(mfile.getOriginalFilename()));
				entity.setFPath(url+rename);
				entity.setFTypeId(FId);
				entity.setFSize(mfile.getSize());
				entity.setFContentType(FilenameUtils.getExtension(mfile.getOriginalFilename()));
				entity.setFType(Type);
				entity.setFTypeId(TypeId);
				entity.setFDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				TFiles entityFile=fileService.saveFiles(entity);
				if(entityFile!=null){
					entitys.add(entity);
				}
				bos.close();
				stream.close();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			result=false;
		}
		rm.setSuccess(result);
		rm.setRoot(entitys);
		return this.getJsonFromObj(rm);
	}
	
	
	private String getFiles(){
		int FTypeId=getInt("FTypeId",0);
		String FType=getString("FType","");
		ResultMessage rm=new ResultMessage();
		List<TFiles> list= fileService.getFiles(FTypeId,FType);
		if(list!=null && list.size()>0){
			rm.setRoot(list);
			rm.setSuccess(true);
			rm.setMessage("数据获取成功！");
		}else{
			rm.setSuccess(false);
			rm.setMessage("数据获取失败！");
		}
		return getJsonFromObj(rm);
	}
	
	
	private String delete(){
		int FId=getInt("FId",0);
		String url=getString("FPath","");
		String path = this.getServletContext().getRealPath("")+url;
		ResultMessage rm=new ResultMessage();
		if(fileService.deleteFile(FId)){
			java.io.File existFile=new java.io.File(path);
			if(existFile.exists()){
				existFile.delete();
			}
			rm.setSuccess(true);
			rm.setMessage("图片已删除！");
		}else{
			rm.setSuccess(true);
			rm.setMessage("图片删除失败！");
		}
		
		return this.getJsonFromObj(rm);
	}
}
