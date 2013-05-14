package com.info.web.controller.buss;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.SystemCurrentUser;
import com.info.domain.TAppGroup;
import com.info.domain.TAppUser;
import com.info.domain.TEfficiencyWage;
import com.info.domain.TExtractionDetailIs;
import com.info.domain.TExtractionDetailOp;
import com.info.domain.TExtractionDetailPm;
import com.info.domain.TFiles;
import com.info.domain.ViewTaskPersonnel;
import com.info.domain.ViewTaskPm;
import com.info.domain.ViewTaskQuery;
import com.info.domain.ViewWagePage;
import com.info.service.BussEfficiencyWageService;
import com.info.service.BussFileService;
import com.info.service.WfProcessUtils;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;

@Controller
public class BussEfficiencyWageController extends BaseController{

	@Autowired
	BussEfficiencyWageService service;
	
	@Autowired
	BussFileService fileService;
	
	@Autowired
	WfProcessUtils processService;
	
	@RequestMapping(value = "/Buss/Wage/{action}")
	public void ActionServlet(@PathVariable("action") Integer action,
			HttpServletResponse response,HttpServletRequest request){
		CurrentResponse=response;
		StringBuilder str=new StringBuilder();		
		switch(action){
		case 1:
			str.append(getWageById());
			break;
		case 2:
			str.append(getParamer());
			break;
		case 3:
			str.append(getList());
			break;
		case 4:
			str.append(getQuery());
			break;
		case 5:
			str.append(getFiles());
			break;
		case 6:
			str.append(deleteAll());
			break;
		case 7:
			str.append(editPass());
			break;
		case 8:
			str.append(wageSend());
			break;
		case 9:
			str.append(pressUrge());
			break;
		case 10:
			str.append(WageToExcel());
			break;
		case 11:
			str.append(editBellwether());
			break;
		case 12:
			str.append(getstationID());
			break;
		case 13:
			str.append(existPM());
			break;
		}
		this.writeJsonString(str.toString());
	}
	
	/**
	 * 根据效益ID，获取效益工资数据集（效益工资，项目经理，内部员工，外协人员），并把数据进行系列化，以JSON形式返回
	 * @return
	 */
	private String getWageById(){
		ResultMessage rm=new ResultMessage();
		Integer Id=getInt("FId",0);
		try{
		ViewTaskQuery entityWage=service.getTask(Id);
		List<ViewTaskPm> entityPM=service.getProjectManage(Id);
		List<ViewTaskPersonnel> entityInside=service.getProjectPersonnelByType(Id, 1);
		List<ViewTaskPersonnel> entityOutside=service.getProjectPersonnelByType(Id, 0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entitys", entityWage);
		map.put("entityPM", entityPM);
		map.put("entityInside", entityInside);
		map.put("entityOutside", entityOutside);
		
		rm.setRoot(map);
		rm.setSuccess(true);
		rm.setMessage("数据获取成功！");
	}catch(Exception ex){
		rm.setSuccess(false);
		rm.setMessage("数据获取失败！");
	}
	return getJsonFromObj(rm);
	}
	

	/**
	 * 接收客户端传进来的数据，进行保存或者编辑（效益工资JSON，项目经理JSON，内部员工JSON，外协人员JSON），并把JSON字符串，进行实体化
	 * @return
	 */
	@SuppressWarnings({"unchecked" })
	private String getParamer(){
		int ProcessID=getInt("ProcessID",0);
		String ProcessTitle=getString("ProcessTitle","");
		String wageJSON= getString("entitys","");
		String pmJSON=getString("entitysPm", "");
		String insideJSON=getString("entitysInside","");
		String outsideJSON=getString("entityOutside","");
		ResultMessage rm=new ResultMessage();
		try{
			TEfficiencyWage entityWage= (TEfficiencyWage)this.getObjectFromJson(wageJSON, TEfficiencyWage.class);
			if(entityWage!=null){
				if(entityWage.getFkTaskId()!=null){
					//项目经理
					List<TExtractionDetailPm> entityP=(List<TExtractionDetailPm>)this.getArrayFromJson(pmJSON,TExtractionDetailPm.class);
					//内部员工
					List<TExtractionDetailIs> entityInside=(List<TExtractionDetailIs>)this.getArrayFromJson(insideJSON,TExtractionDetailIs.class);
					//外协人员
					List<TExtractionDetailOp> entityOutside=(List<TExtractionDetailOp>)this.getArrayFromJson(outsideJSON,TExtractionDetailOp.class);
					
					int wageID=service.saveorupdate(ProcessID,ProcessTitle,entityWage, entityP, entityInside, entityOutside);
					
					if(wageID>0){
						rm.setSuccess(true);
						rm.setMessage("数据保存成功！");
						rm.setRoot(wageID);
					}else{
						rm.setSuccess(false);
						rm.setMessage("数据异常，数据处理失败！");
					}
				}else{
					rm.setSuccess(false);
					rm.setMessage("项目信息不全，请完善项目信息！");
				}
				
			}else{
				rm.setSuccess(false);
				rm.setMessage("网络异常，数据处理失败！");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return getJsonFromObj(rm);
	}
	
	/**
	 * 根据查询条件，获取效益工资数据集，如不带条件，则返回全部数据
	 * @return
	 */
	private String getList(){
		String FContractNumbers=getString("FContractNumbers","");
		String FNumber=getString("FNumber","");
		String FTaskName=getString("FTaskName","");
		String FEntrustUnitName=getString("FEntrustUnitName","");
		List<ViewWagePage> list= service.getWageList(FNumber,FTaskName,FEntrustUnitName,FContractNumbers);
		ResultMessage rm=new ResultMessage();
		if(list!=null && list.size()>=0){
			int count=service.getWageCount(FNumber,FTaskName,FEntrustUnitName,FContractNumbers);
			rm.setRoot(list);
			rm.setSuccess(true);
			rm.setTotalProperty(count);
			rm.setMessage("查询成功！");
		}else{
			rm.setSuccess(false);
			rm.setTotalProperty(0);
			rm.setMessage("查询失败！");
		}
		
		return getJsonFromObj(rm);
	}
	
	/**
	 * 根据效益ID，获取效益工资信息（效益工资，项目经理，内部员工，外协人员），并把数据进行系列化，以JSON形式返回
	 * @return
	 */
	private String getQuery(){
		int wId=getInt("wId",0);
		int activeId=getInt("activeId",0);
		ResultMessage rm=new ResultMessage();
		try{
			processService.setActiveAcceptUser(activeId, SystemCurrentUser.getCurrentUser().getUserID());
			processService.setActiveAcceptTime(activeId);
			ViewWagePage entityWage=service.getWagePage(wId);
			List<TExtractionDetailPm> entityPM=service.getPM(wId);
			List<TExtractionDetailIs> entityInside=service.getInside(wId);
			List<TExtractionDetailOp> entityOutside=service.getOutside(wId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("entitys", entityWage);
			map.put("entityPM", entityPM);
			map.put("entityInside", entityInside);
			map.put("entityOutside", entityOutside);
			rm.setRoot(map);
			rm.setSuccess(true);
			rm.setMessage("数据获取成功！");
		}catch(Exception ex){
			rm.setSuccess(false);
			rm.setMessage("数据获取失败！");
		}
		return getJsonFromObj(rm);
	}
	
	/**
	 * 根据效益工资ID和类型，获取文件信息
	 * @return
	 */
	private String getFiles(){
		int FTypeId=getInt("FTypeId",0);
		ResultMessage rm=new ResultMessage();
		List<TFiles> list= fileService.getFiles(FTypeId,"wage");
		if(list!=null && list.size()>0){
			rm.setRoot(list);
			rm.setSuccess(true);
			rm.setMessage("数据获取成功！");
		}else{
			rm.setSuccess(false);
			rm.setRoot(new ArrayList<TFiles>());
			rm.setMessage("数据获取失败！");
		}
		return getJsonFromObj(rm);
	}
	
	/**
	 * 根据效益工资，撤销所有的操作
	 * @return
	 */
	private String deleteAll(){
		int wId=getInt("wId",0);
		int ProcessID=getInt("ProcessID",0);
		ResultMessage rm=new ResultMessage();
		boolean entity= service.deleteAll(wId,ProcessID);
		if(entity){
			rm.setSuccess(true);
			rm.setMessage("撤销成功！");
		}else{
			rm.setSuccess(false);
			rm.setMessage("业务不能撤销！");
		}
		return getJsonFromObj(rm);
	}
	
	/**
	 * 效益工资打回
	 * @return
	 */
	private String editPass(){
		int activeId=getInt("activeId",0);
		String detail=getString("detail","");
		ResultMessage rm=new ResultMessage();
		boolean entity= service.editPass(activeId, detail);
		if(entity){
			rm.setSuccess(true);
			rm.setMessage("打回成功！");
		}else{
			rm.setSuccess(false);
			rm.setMessage("打回失败！");
		}
		return getJsonFromObj(rm);
	}
	
	private String existPM(){
		int taskId=getInt("taskId",0);
		int userId=getInt("userId",0);
		boolean currentPM= service.getMostlyPM(taskId, userId);
		ResultMessage rm=new ResultMessage();
		if(currentPM){
			rm.setSuccess(true);
			rm.setMessage("获取项目经理成功！");	
		}else{
			rm.setSuccess(false);
			rm.setMessage("项目经理才能进行业务上报！");	
		}
		return getJsonFromObj(rm);
	}
	
	/**
	 * 效益工资信息上报
	 * @return
	 */
	private String wageSend(){
		ResultMessage rm=new ResultMessage();
		List<Integer> stationIds=new ArrayList<Integer>();
		List<String> str=new ArrayList<String>();
		try{
			int count=0;
			int processId=getInt("processId",0);
			int aboveActId=getInt("activeId",0);
			String remark=getString("remark","");
			String Ids=getString("Ids","");
			int FTypeId=getInt("primaryId",0);
			int userId=getInt("userId",0);
			String orgID=getString("orgId","");
			List<TAppUser> entitys=new ArrayList<TAppUser>();
			List<TFiles> list= fileService.getFiles(FTypeId,"wage");
			if(list!=null && list.size()>0){
				List<TAppUser>  entity1=service.getAll(Ids);
				List<TAppUser>  entity2=service.getUserByStationID(orgID, userId);
				entitys.addAll(entity1);
				entitys.addAll(entity2);
				for(TAppUser entity :entitys){
					 int c=processService.addProcessActiveItemByStation(processId, entity.getFUnitStation(),entity.getFId(), aboveActId, remark);
					 if(c>0){
						  count++;
						  stationIds.add(entity.getFUnitStation());
					  }
				}
				
				List<TAppGroup> groups=service.getAppGroups(StringUtils.join(stationIds,","));
				for(TAppGroup group :groups){
					str.add(group.getFName());
				}
				processService.setProcessCurrentUser(processId,userId);
				rm.setSuccess(true);
				rm.setMessage("已上报"+count+"数据，信息已发送到"+StringUtils.join(str,"、")+"！");
				}else{
			    rm.setSuccess(false);
				rm.setMessage("上报失败,请上传效益工资附件！");	
			}	
		}catch(Exception ex){
			rm.setSuccess(true);
			rm.setMessage("上报失败！");
		}
		
		return getJsonFromObj(rm);
	}
	
	/**
	 * 效益工资催办
	 * @return
	 */
	private String pressUrge(){
		int activeId=getInt("activeId",0);
		String detail=getString("detail","");
		ResultMessage rm=new ResultMessage();
		boolean entity= processService.activeUrge(activeId, detail);
		if(entity){
			rm.setSuccess(true);
			rm.setMessage("催办成功！");
		}else{
			rm.setSuccess(false);
			rm.setMessage("催办失败！");
		}
		return getJsonFromObj(rm);
	}
	
	/**
	 * 导出效益工资人员签字单的Excel
	 * @return
	 */
	private String WageToExcel(){
		ResultMessage rm=new ResultMessage();
		try{
		String path = this.getServletContext().getRealPath("/userfiles/excel/EfficiencyWage.xls");
		int Id=getInt("wId",0);
		String title=getString("title","");
		File file =new File(path);
		Workbook w = Workbook.getWorkbook(file);
		WritableWorkbook wbook = Workbook.createWorkbook(file, w);
		WritableSheet wsheet = wbook.getSheet(0);
		for(int i=3;i<200;i++){
			wsheet.removeRow(i);
		}
		
		Label label = new Label(0, 0, title);
		wsheet.addCell(label);
		//List<ViewTaskPersonnel> entityInside=service.getProjectPersonnelByType(Id, 1);
		//List<ViewTaskPersonnel> entityOutside=service.getProjectPersonnelByType(Id, 0);
		List<TExtractionDetailIs> entityInside=service.getInside(Id);
		List<TExtractionDetailOp> entityOutside=service.getOutside(Id);
		for(int i=0;i<entityInside.size();i++){
			Label contentCell = new Label(0, i+3, entityInside.get(i).getFIsname());
			wsheet.addCell(contentCell);
			
			contentCell= new Label(1, i+3, entityInside.get(i).getFIsworkRatio());
			wsheet.addCell(contentCell);
			
			contentCell= new Label(2, i+3, entityInside.get(i).getFIsshouldTotal().toString());
			wsheet.addCell(contentCell);
			
			contentCell= new Label(3, i+3, entityInside.get(i).getFIsadjustedTotal().toString());
			wsheet.addCell(contentCell);
			
			contentCell= new Label(4, i+3, entityInside.get(i).getFIsrealWage().toString());
			wsheet.addCell(contentCell);
			
			contentCell= new Label(5, i+3, entityInside.get(i).getFIsretainedWage().toString());
			wsheet.addCell(contentCell);
			
		}
		
		for(int i=0;i<entityOutside.size();i++){
			Label contentCell = new Label(7, i+3, entityOutside.get(i).getFOutsourcingName());
			wsheet.addCell(contentCell);
			
			contentCell = new Label(8, i+3, entityOutside.get(i).getFOutsourcingWorkRatio());
			wsheet.addCell(contentCell);
			
			contentCell = new Label(9, i+3, entityOutside.get(i).getFOutsourcingExtractionWage().toString());
			wsheet.addCell(contentCell);
		}
		
		wbook.write();
		wbook.close();
		rm.setSuccess(true);
		rm.setRoot("/userfiles/excel/EfficiencyWage.xls");
		
		} catch (Exception e) {
			rm.setSuccess(false);
			rm.setMessage("导出异常！");
		}	
		return this.getJsonFromObj(rm);
	}
	
	private String editBellwether(){
		int wId=getInt("wId",0);
		int processId=getInt("processId",0);
		int activeId=getInt("activeId",0);
		int acceptUserId=getInt("acceptUserId",0);
		int userId=getInt("userId",0);
		String columnName=getString("key","");
		String columnValue=getString("value","");
		boolean generalManager=Boolean.parseBoolean(getString("isSeal","false"));
		ResultMessage rm=new ResultMessage();
		String message="";
		try{
			service.editBellwether("set"+columnName, wId, columnValue);
			//判断是否是总经理
			if(generalManager){
				//判断业务是否完成
				if(processService.activeComplet(activeId)){
					processService.setProcessState(processId, 5); //进行归档
				}
			 }else{
				 //项目经理签字，认定是业务完成
				 processService.setActiveState(activeId, 4);//办理完成
				 processService.setProcessCurrentUser(processId, userId);
				 
				 List<String> arr=service.existSignature(wId);
				 if(arr.size()>0){
					 message="签字成功,正等待"+StringUtils.join(arr,"、")+"签字！";
				 }else{
					 processService.addProcessActiveItem(processId, acceptUserId, activeId, "信息已通过财务部、综合部、部门经理、生成副总审批，请总经理在次进行审批！"); 
					 message="签字成功,信息已发送到总经理，等待总经理审批！";
				 }				 
			 }
			rm.setSuccess(true);
			rm.setRoot(columnValue);
			rm.setMessage(generalManager==true?"信息已进行归档，业务结束！":message);
			rm.setTotalProperty(generalManager==true?5:0);
		}catch(Exception ex){
			rm.setSuccess(false);
			rm.setRoot(columnValue);
			rm.setMessage(generalManager==true?"业务未完成，信息不能归档！":"数据异常，签字失败！");
			rm.setTotalProperty(0);
		}
		return this.getJsonFromObj(rm);
	}
	
	/**
	 * 获取部门经理
	 * @return
	 */
	private String getstationID(){
		String orgID=getString("orgId","");
		int taskId=getInt("taskId",0);
		ResultMessage rm=new ResultMessage();
		List<ViewTaskPm> list=service.getMostlyPMByTaskID(taskId);
		if(list!=null && list.size()>0){
			List<TAppUser> entitys= service.getUserByStationID(orgID, list.get(0).getFPmId());
			if(entitys.size()>0){
				rm.setSuccess(true);
				rm.setRoot(entitys.get(0).getFUnitStation());
				rm.setMessage("数据获取成功！");
			}else{
				rm.setSuccess(false);
				rm.setRoot(0);
				rm.setMessage("数据获取失败！");
			}
		}
		
		return this.getJsonFromObj(rm);
	}
}
