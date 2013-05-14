package com.info.web.controller.buss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.info.common.util.SystemCurrentUser;
import com.info.domain.TConstructionData;
import com.info.domain.ViewCodeTree;
import com.info.service.BussConstructionService;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.controller.util.BaseController;
@Scope("prototype")
@Controller
public class BussConstructionController extends BaseController {
	@Autowired
	BussConstructionService constructionService;
	
	ResultMessage message;
	public String result = "";
	
	@RequestMapping(value = "/Buss/ConstructionService/{action}")
	public void getDataService(@PathVariable("action") Integer action,
			HttpServletResponse response)
			throws Exception {
		CurrentResponse = response;
		String Result = null;
		message = new ResultMessage();		
		switch (action) {
		case 1:		
			Result = SaveOrUpdate();
			break;	
		case 2:		
			Result = getConstructionByID();
			break;		
		case 3:		
			Result = delete();
			break;	
		case 4:		
			Result = getConstructions();
			break;
		case 5:		
			Result = getUserUnitStation();
			break;
		case 6:		
			Result = setPublicOrHide();
			break;
		}
		// 输出响应json串
		writeJsonString(Result);
	}

	private String setPublicOrHide() {
		Integer id = getInt("FId", 0);
		Integer FPublicType = getInt("FPublicType", 0);
		if(id > 0){
			TConstructionData construction = constructionService.getConstructionByID(id);
			if(null != construction){
				construction.setFPublicType(FPublicType);
				constructionService.update(construction);
				message.setSuccess(true);
			}else{
				message.setSuccess(false);
			}
		}
		return getJsonFromObj(message);
	}
	//获取用户所在组(开发组或者技术负责人的话授予权限)
	private String getUserUnitStation() {
		int station = SystemCurrentUser.getCurrentUser().getUnitStation();
		if(SystemCurrentUser.getCurrentUser().getUnitStation()==1 || SystemCurrentUser.getCurrentUser().getUnitStation()==1003){
			message.setSuccess(true);
		}else{
			message.setSuccess(false);
		}
		return getJsonFromObj(message);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/GetTree/ConstructionTree/{pid}")
	public void GetConstructionTree(@PathVariable("pid") Integer pid,HttpServletResponse response) {
		CurrentResponse = response;
		String treeJson = ""; 
		List<ViewCodeTree> objfirstlist = new ArrayList();
		List<ViewCodeTree> list = constructionService.GetConstructionTree(pid);
		// 构造树		
		for (ViewCodeTree obj : list) {
			if (obj.getFkTreeCodeTypeId().toString().equals("0")) {
				objfirstlist.add(obj);
			}
		}
		List tree = buildConstructionTree(list,objfirstlist);
        treeJson= getJsonFromArray(tree);
        writeJsonString(treeJson);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List buildConstructionTree(List<ViewCodeTree> objList,
			List<ViewCodeTree> objparentList) {
		List list = new ArrayList();
		for (int i = 0; i < objparentList.size(); i++) {
			Map<String, Object> map = new HashMap();
			ViewCodeTree obj = (ViewCodeTree) objparentList.get(i);
			// 1、为叶子节点
			if (obj.getIsleft()) {
				map.put("id", obj.getFId().toString());
				map.put("text", obj.getFCodeText());
				map.put("attributes", obj.getIsleft());
				map.put("iconCls", "icon-100");
				list.add(map);
			} else { // 2、有下级节点
				map.put("id", obj.getFId().toString());
				map.put("text", obj.getFCodeText());
				map.put("attributes", obj.getIsleft());
				map.put("iconCls", "icon-200");
				map.put("children",buildConstructionTree(objList,findChildCode(objList, obj.getFId())));
				list.add(map);
			}
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<ViewCodeTree> findChildCode(List<ViewCodeTree> objList,
			Integer id) {
		List<ViewCodeTree> objlist = new ArrayList();
		for (ViewCodeTree obj : objList) {
			if (obj.getFParentId() == id)
				objlist.add(obj);
		}
		return objlist;
	}
	
	private String getConstructions() {
		EasyDataGrid ResultJson= new EasyDataGrid();
		Integer type = getInt("type", 0);
		Integer personid = getInt("personid", 0);
		Integer startIndex = getInt("startIndex", 1);
		Integer pageSize = getInt("pageSize", 10);
		List<TConstructionData> list = constructionService.getConstructions(type, personid, startIndex, pageSize);
		if(list.size() > 0){
			ResultJson.setRows(list);
			ResultJson.setTotal(Long.valueOf(list.size()));
		}else{
			ResultJson.setRows(list);
			ResultJson.setTotal(Long.valueOf(0));
		}
		return getJsonFromObj(ResultJson);
	}

	private String delete() {
		Integer id = getInt("FId", 0);
		if(id > 0 ){
			if(constructionService.delete(id)){
				message.setSuccess(true);
			}else{
				message.setSuccess(false);
			}
		}
		return getJsonFromObj(message);
	}

	private String getConstructionByID() {
		Integer id = getInt("FId", 0);
		if(id > 0){
			TConstructionData construction = constructionService.getConstructionByID(id);
			if(null != construction){
				message.setRoot(construction);
				message.setSuccess(true);
			}else{
				message.setRoot(null);
				message.setSuccess(false);
			}
		}
		return getJsonFromObj(message);
	}

	private String SaveOrUpdate() {
		Integer FId = getInt("FId", 0);
		String FTitle = getString("FTitle", "");
		String FDate = getString("FDate", "");
		Integer FPersonId = getInt("FPersonId", 0);
		String FPersonName = getString("FPersonName", "");
		Integer FType = getInt("FType", 0);
		String FTypeName = getString("FTypeName", "");	
		Integer FPublicType = getInt("FPublicType", 0);
		String FContent = getString("FContent", "");
		if(FId <= 0){
			TConstructionData construction =  new TConstructionData();
			construction.setFTitle(FTitle);
			construction.setFDate(FDate);
			construction.setFPersonId(FPersonId);
			construction.setFPersonName(FPersonName);
			construction.setFType(FType);
			construction.setFTypeName(FTypeName);
			construction.setFPublicType(FPublicType);
			construction.setFContent(FContent);
			construction = constructionService.save(construction);
			if(null != construction){
				message.setSuccess(true);
				message.setRoot(construction);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setRoot(null);
				message.setTotalProperty(0);
			}
		}else{
			TConstructionData construction =  constructionService.getConstructionByID(FId);
			construction.setFTitle(FTitle);
			construction.setFDate(FDate);
			construction.setFPersonId(FPersonId);
			construction.setFPersonName(FPersonName);
			construction.setFType(FType);
			construction.setFTypeName(FTypeName);
			construction.setFPublicType(FPublicType);
			construction.setFContent(FContent);
			construction = constructionService.update(construction);
			if(null != construction){
				message.setSuccess(true);
				message.setRoot(construction);
				message.setTotalProperty(1);
			}else{
				message.setSuccess(false);
				message.setRoot(null);
				message.setTotalProperty(0);
			}
		}
		return getJsonFromObj(message);
	}
}
