package com.info.web.controller.buss;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.antlr.gunit.swingui.parsers.StGUnitParser.output_return;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.Var;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sun.text.normalizer.UTF16;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock.Limit;
import com.info.domain.TProjectChapterEg;
import com.info.domain.TProjectSealsBft;
import com.info.domain.TTask;
import com.info.domain.ViewProcessProjectSeal;
import com.info.service.BussSealManagementService;
import com.info.service.WfProcessUtils;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
/**
 * @ClassName	: BussSealManagementController   
 * @Description	: TODO(这里用一句话描述这个类的作用)   
 * @Author		: chunlei
 * @Date		: 2013-1-19 上午12:03:28   
 *
 */
@Controller
public class BussSealManagementController extends BaseController {
	@Autowired
	BussSealManagementService sealService;
	
	
	ResultMessage message;
	EasyDataGrid dataGrid;
	String result="";
	
	/////////////////////用章图片浏览/////////////////////
	@RequestMapping(value="/buss/loadimage2/{action}")
	public void loadImage2(@PathVariable("action")Integer action,HttpServletResponse response){
		int FId=action;
		TProjectChapterEg chapterEg=sealService.getCHapterById(FId);
		//response.addHeader("Content-Disposition", "attachment;filename="+ new String((chapterEg.get).getBytes("GBK"), "ISO-8859-1"));
		CurrentResponse = response;
		writeByte(chapterEg.getFProcessingImages());
	}
	
	@RequestMapping(value="/buss/loadimage1/{action}")
	public void loadImage(@PathVariable("action")Integer action,HttpServletResponse response){
		int FId=action;
		TProjectChapterEg chapterEg=sealService.getCHapterById(FId);
		CurrentResponse = response;
		writeByte(chapterEg.getFPrintPictures());
	}
	/////////////////////用章申领登记图片上传///////////////
	@RequestMapping(value="/buss/upimage/{action}")
	public void sealImage(@PathVariable("action")Integer action,HttpServletResponse response,HttpServletRequest request) throws Exception{
		CurrentResponse=response;
		String result=null;
		switch (action) {
		case 1:
			result=upImage(request);
			break;
		case 2:
			result=getFlashSession(request);
			break;
		}
		writeJsonString(result);
	}

	//获取falsh上传的Session
	 private String getFlashSession(HttpServletRequest request) {
		ResultMessage rm=new ResultMessage();
		String sessionId=request.getSession().getId();
		rm.setSuccess(true);
		rm.setRoot(sessionId);
		return getJsonFromObj(rm);
	}
	 /**
	  * 
	  * @Description	: 上传文件的图片的方法
	  * @Author		: chunlei
	  * @Date		: 2013-1-19 上午12:02:04
	  * @param request
	  * @return
	  * @throws Exception
	  */
	private String upImage(HttpServletRequest request)throws Exception {
		ResultMessage message=new ResultMessage();
		boolean result=true;
		 Integer FId;
		 String url=getString("url", "");
		 String rename="";
		 String fileType=".";
		 int size;
		 String base=this.getServletContext().getRealPath("")+url;
		 
		try {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
			List<?> files=multipartHttpServletRequest.getFiles("file");
			for (Object object : files) {
				FId=Integer.parseInt(request.getParameter("FId"));
				String dd=request.getParameter("itype").toString();
				boolean type=Boolean.parseBoolean(dd);
				size=getInt("Size", 0);
				CommonsMultipartFile mFile=(CommonsMultipartFile)object;
				InputStream input=mFile.getInputStream();
				String fileNames=mFile.getOriginalFilename();
				rename=FilenameUtils.getExtension(fileNames);
				fileType+=rename;
				String fileNamesFull=base+rename;
				
				OutputStream output=new FileOutputStream(fileNamesFull);
				int bytesRead=0;
				byte[] buffer=new byte[size];
				while ((bytesRead=input.read(buffer,0,size))!=-1) {
					output.write(buffer,0,bytesRead);
				}
				result=sealService.uppictures(FId, buffer,type,fileType);
				output.close();
				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
		}
		message.setSuccess(result);
		return getJsonFromObj(message);
	}

	////////// //////////用章申领登记/////// //////////
	@RequestMapping(value="/buss/chapter/{action}")
	public void sealChapter(@PathVariable("action")Integer action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		dataGrid=new EasyDataGrid();
		switch (action) {
		case 0:
			result=addChapter(getChapter());
			break;
		case 1:
			result=updateChapter(getChapter());
			break;
		case 2:
			result=deleteChapter(getChapter());
			break;
		case 3:
			int start=getInt("page", 0);
			int limit=getInt("rows", 0);
			if(start>1){
				start*=limit;
				start-=limit;
			}else {
				start=0;
			}
			result=getChapters(start,limit,getString("FSealName", ""),
					getString("FRecipientsPeople", ""),
					getString("FRecipientsDate", ""),
					getString("FReturnDate", ""));
			break;
		case 4:
			result=getChapte(getInt("FId", -1));
			break;
		case 5:
			result=getSealr();
			break;
		}	
		writeJsonString(result);
	}
	/**
	 * 
	 * @Description	: 返回可以领用的证章
	 * @Author		: chunlei
	 * @Date		: 2013-03-21 17-26
	 * @return
	 */
	private String getSealr() {
	    String st="[";
	    List<ViewProcessProjectSeal> list =sealService.getProcessProjectSeals();
	    for (ViewProcessProjectSeal viewProcessProjectSeal : list) {
		st+="{\"id\":"+viewProcessProjectSeal.getFId()+",\"text\":\""+viewProcessProjectSeal.getFSealContent()+"\"},";
	    }
	    st=st.substring(0, st.length()-1);
	    st+="]";
	    return st;
	}

	/**
	 * 根据Id返回申领登记
	 * @param id
	 * @return
	 */
	private String getChapte(int id) {
		try {
			TProjectChapterEg chapter=sealService.getCHapterById(id);
			if (chapter.equals(null)) {
				message.setMessage("加载完成!");
				message.setSuccess(true);
			}else {
				message.setMessage("未找到数据!") ;
				message.setSuccess(false);
			}
		} catch (Exception e) {
			message.setMessage("系统错误,请与管理员联系");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	/**
	 * @Description	: 返回数据列表
	 * @Author		: chunlei
	 * @Date		: 2013-1-19 上午12:04:54
	 * @param start 起始位置
	 * @param limit 结束位置
	 * @param sealName 用章申请名
	 * @param people 申请人
	 * @param recDate 申请时间
	 * @param returnDate 归还时间
	 * @return
	 */
	private String getChapters(int start,int limit,String sealName, String people, String recDate,
			String returnDate) {
		List<TProjectChapterEg> list=sealService.getChapters(start,limit,sealName, people, recDate, returnDate);		
		dataGrid.setRows(list);
		dataGrid.setTotal(sealService.getCountByChapter(sealName, people, recDate, returnDate));
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 删除用章申领登记
	 * @param chapter
	 * @return
	 */
	private String deleteChapter(TProjectChapterEg chapter) {
		if (sealService.deleteChapter(chapter)) {
			message.setMessage("删除成功！");
			message.setSuccess(true);
		} else {
			message.setMessage("删除失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 修改用章申领登记
	 * @param chapter
	 * @return
	 */
	private String updateChapter(TProjectChapterEg chapter) {
		if (sealService.updateChapter(chapter)) {
			message.setMessage("修改成功！");
			message.setSuccess(true);
		} else {
			message.setMessage("修改失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 添加用章申领登记
	 * @param chapter
	 * @return
	 */
	private String addChapter(TProjectChapterEg chapter) {
		if (sealService.addChapter(chapter)) {
			message.setMessage("保存成功！");
			message.setSuccess(true);
		} else {
			message.setMessage("保存失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}

	/**
	 * 获取页面申领信息
	 * @return
	 */
	 private TProjectChapterEg getChapter() {
		TProjectChapterEg chapter=new TProjectChapterEg();
		chapter.setFId(getInt("FId",-1));
		chapter.setFHandleMan(getString("FHandleMan", ""));
		chapter.setFHandleTime(getString("FHandleTime", ""));
		chapter.setFNote(getString("FNote", ""));
		chapter.setFRecipientsDate(getString("FRecipientsDate", ""));
		chapter.setFRecipientsPeople(getString("FRecipientsPeople", ""));
		chapter.setFReturnDate(getString("FReturnDate", ""));
		chapter.setFSealName(getString("FSealName", ""));
		chapter.setFSealId(getInt("FSealId", -1));
		return chapter;
	}
	
	/**
	 * @Description	: 用章申请控制
	 * @Author		: chunlei
	 * @Date		: 2013-1-19 上午12:00:10
	 * @param action
	 * @param response
	 */
	@RequestMapping(value="/buss/seal/{action}")
	public void sealbid(@PathVariable("action")Integer action,HttpServletResponse response){
		CurrentResponse=response;
		message=new ResultMessage();
		dataGrid=new EasyDataGrid();
		switch (action) {
		case 0:
			result=addSeal(getSeal());
			break;
		case 1:
			result=updateSeal(getSeal());
			break;
		case 2:
			result=deleteSeal(getSeal());
			break;
		case 3:
			result=getSealById(getInt("FId", -1));
			break;
		case 4:
		    	//esayui的datagrid分页发送的请求为page（第几页），rows（每页的条数）所以要经过以下处理
			int start=getInt("page", 0);
			int limit=getInt("rows", 0);
			if(start>1){
				start*=limit;
				start-=limit;
			}else {
				start=0;
			}
			result=getTask(start,limit,getString("taskName", ""));
			break;
		case 5:
			result=setActiveComplet(getInt("activeId", -1));
			break;
		case 6:
			result=setPost(getInt("processId", -1) ,getInt("aboveActId",-1), getInt("userId", -1),getString("remark", ""));
			break;
		case 7:
			result=cancelSeal(getInt("FId", -1),getInt("processId", -1),getInt("activeId", -1));
			break;
		case 8:
			result=activeReturn(getInt("activeId", -1), getString("remark", ""));
			break;
		case 9:
			result=activeUrge(getInt("activeId", -1),getString("remark", ""));
			break;
		case 10:
		    result=getAccept(getInt("activeId", -1));
		    break;
		case 11:
		    result=setActiveAcceptTime(getInt("activeId", -1));
		    break;
		case 12:
		    result=setProsessTitle(getInt("processId", -1),getInt("fromPKID", -1),getString("title", "项目用章申请"));
		    break;
		}
		writeJsonString(result);
	}
	/**
	 * 
	 * @Description	:设置标题
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 18-35
	 * @param processId
	 * @param fromPKID
	 * @param title
	 * @return
	 */
	private String setProsessTitle(int processId, int fromPKID, String title) {
	    if(sealService.setProsessTitle(processId, fromPKID, title)){
		message.setSuccess(true);
	    }else {
		message.setMessage("设置标题时出错");
		message.setSuccess(false);
	    }
	    return getJsonFromObj(message);
	}

	/**
	 * 
	 * @Description	: 设置任务接收时间
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 11-33
	 * @param activeId
	 * @return
	 */
	private String setActiveAcceptTime(int activeId) {
	    try {
		sealService.setActiveAcceptTime(activeId);
		message.setMessage("接收成功！");
		message.setSuccess(true);
	    } catch (Exception e) {
		message.setMessage("接收失败！");
		message.setSuccess(true);
	    }
	    return getJsonFromObj(message);
	}

	/**
	 * 
	 * @Description	: 获取任务的办理情况(主要获得当前办理人Id)
	 * @Author		: chunlei
	 * @Date		: 2013-1-19 上午12:06:53
	 * @param activeId
	 * @return
	 */
	private String getAccept(int activeId) {
	    return getJsonFromObj(sealService.getTWfProccessActive(activeId));
	}
	/**
	 * 
	 * @Description	: 催办方法
	 * @Author		: chunlei
	 * @Date		: 2013-03-20 11-29
	 * @param activeId
	 * @param remark
	 * @return
	 */
	private String activeUrge(int activeId, String remark) {
		if(sealService.activeUrge(activeId, remark)){
			message.setSuccess(true);
			message.setMessage("催办成功！");
		}else {
			message.setMessage("催办失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}

	/**
	 * 提交设置
	 * @param processId 
	 * @param aboveActId
	 * @param userId
	 * @param remark 说明
	 * @return
	 */
	private String setPost(int processId, int aboveActId, int userId, String remark) {
		if (sealService.addProcessActiveItem(processId, userId, aboveActId, remark)) {
			message.setMessage("提交成功！");
			message.setSuccess(true);
		} else {
			message.setMessage("提交失败！请与管理员联系");
			message.setSuccess(false);
		}
		
		return getJsonFromObj(message);
	}

	/**
	 * 设置任务完成
	 * @param activeId
	 * @return
	 */
	private String setActiveComplet(int activeId) {
		if (sealService.setActiveComplet(activeId)) {
			message.setSuccess(true);
			message.setMessage("任务完成！");
		} else {
			message.setMessage("提交失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}

	/**
	 * 
	 * @Description	: 返回任务的列表（任务选择方式改变后没使用）
	 * @Author		: chunlei
	 * @Date		: 2013-1-19 上午12:04:04
	 * @param start
	 * @param limit
	 * @param taskName
	 * @return
	 */
	private String getTask(int start, int limit, String taskName) {
		//dataGrid是easyUi的数据格式
		try {
			List<TTask> list = sealService.getTasks(start, limit, taskName);
			dataGrid.setRows(list);
			dataGrid.setTotal(sealService.getTaskCount(start, limit, taskName));
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return getJsonFromObj(dataGrid);
	}
	/**
	 * 根据Id返回刻章申报
	 * @param FId
	 * @return
	 */
	private String getSealById(int FId) {
		TProjectSealsBft seal=sealService.geTProjectSealsBftById(FId);
		if (!seal.equals(null)) {
			message.setSuccess(true);
			message.setMessage("加载成功！");
			message.setRoot(seal);
		} else {
			message.setSuccess(false);
			message.setMessage("系统错误！请联系管理员..");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 打回方法
	 * @param activeId
	 * @param remark
	 * @return
	 */
	private String activeReturn(int activeId,String remark) {
		if (sealService.activeReturn(activeId, remark)){
			message.setSuccess(true);
			message.setMessage("打回成功!");
		}else {
			message.setMessage("打回失败！请与管理员联系");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 撤销项目用章申请
	 * @param FId
	 * @param processId
	 * @param activeId
	 * @return
	 */
	private String cancelSeal(int FId,int processId, int activeId) {
		if(sealService.cancelSeal(processId, activeId)){
			if (FId>0) {
				if (sealService.deleteSeal(FId)) {
					message.setSuccess(true);
					message.setMessage("撤销成功！");
				}else{
					message.setSuccess(false);
					message.setMessage("撤销失败！");
				}
			}else{
				message.setSuccess(true);
				message.setMessage("撤销成功！");
			}
		}else{
			message.setSuccess(false);
			message.setMessage("撤销失败！");
		}
		return getJsonFromObj(message);
	}
	/**
	 * 删除刻章申报
	 * @param seal
	 * @return
	 */
	private String deleteSeal(TProjectSealsBft seal) {
		if (sealService.deleteSeal(seal)) {
			message.setMessage("删除成功！");
			message.setSuccess(true);
		} else {
			message.setMessage("删除失败！");
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}
	/**
	 * 修改刻章申报
	 * @param seal
	 * @return
	 */
	private String updateSeal(TProjectSealsBft seal) {
	    try {
		setProsessTitle(getInt("processId", -1), getInt("fromPKID", 0), getString("title", "项目用章申报"));
		if(sealService.updateSeal(seal)!=null){
			message.setMessage("保存成功！");
			message.setSuccess(true);
		}else {
			message.setMessage("保存失败！");
			message.setSuccess(false);
		}
	    } catch (Exception e) {
		message.setMessage("系统错误！请与管理员联系");
		message.setSuccess(false);
	    }
		return getJsonFromObj(seal);
	}

	/**
	 * 添加刻章申办信息
	 * @param seal
	 * @return
	 */
	private String addSeal(TProjectSealsBft seal) {
	    try {
		seal=sealService.addSeal(seal);
		if (seal!=null) {
		    setProsessTitle(getInt("processId", -1), seal.getFId(), getString("title", "项目用章申报"));
		    message.setRoot(seal);
			message.setMessage("添加成功！");
			message.setSuccess(true);
		} else {
			message.setMessage("添加失败！");
			message.setSuccess(false);
		}
		setProsessTitle(getInt("processId", -1), seal.getFId(), getString("title", "项目用章申报"));
		
	    } catch (Exception e) {
		message.setMessage("系统错误！请与管理员联系");
		message.setSuccess(false);
	    }
	    return getJsonFromObj(message);
	}
	/**
	 * 获取页面值
	 * @return
	 */
	private TProjectSealsBft getSeal() {
		TProjectSealsBft seal=new TProjectSealsBft();
		seal.setFBidForReason(getString("FBidForReason", ""));
		seal.setFDeptOpinion(getString("FDeptOpinion", ""));
		seal.setFId(getInt("FId", -1));
		seal.setFkTaskId(getInt("FKTaskId", -1));
		seal.setFNumbers(getString("FNumbers", ""));
		seal.setFProductionAco(getString("FProductionACO", ""));
		seal.setFSealContent(getString("FSealContent", ""));
		seal.setFTheApplicant(getString("FTheApplicant", ""));
		seal.setFTheApplicantTime(getString("FTheApplicantTime", ""));
		seal.setFTheGeneralMo(getString("FTheGeneralMO", ""));
		seal.setFTheApplicantId(getInt("FTheApplicantId", -1));
		return seal;
	}
}
