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

import com.info.domain.TAppCode;
import com.info.domain.TreeNode;
import com.info.domain.ViewAppOrgTree;
import com.info.domain.ViewClientTree;
import com.info.domain.ViewCodeTree;
import com.info.domain.ViewGroupUser;
import com.info.domain.ViewTask;
import com.info.service.BussGetCodeService;
import com.info.service.BussNumberRuleService;
import com.info.common.util.JsonUtils;
import com.info.web.ComboxData;
import com.info.web.EasyDataGrid;
import com.info.web.ResultMessage;
import com.info.web.jsonData;
import com.info.web.controller.util.BaseController;

@Scope("prototype")
@Controller
public class BussGetCodeController extends BaseController {
	@Autowired
	BussGetCodeService service;

	@Autowired
	BussNumberRuleService bnrService;
	
	private static final String SUCCESS_STRING = "{\"success\":true}";
	private static final String FAIL_STRING = "{\"success\":false}";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/GetCode/Query/")
	public void queryall(HttpServletResponse response) {
		CurrentResponse = response;
		String result = "";		
		//ResultMessage message = new ResultMessage();
		// 取得'POST'参数
		String commandString = getString("action", "");
		// 获取系统代码
		if (commandString.equals("getAppCode")) {
			Integer fkCodeTypeIdString = getInt("CodeTypeId", 0);
			Integer FStateString = getInt("State", 0);
			List<TAppCode> objlist = service.GetListAppCode(fkCodeTypeIdString,
					FStateString);
			if (objlist != null && objlist.size() > 0) {				
				result = JsonUtils.toJSONString(objlist);							
			}else {
				result = JsonUtils.toJSONString(null);
			}
		}
		if (commandString.equals("getAppCodeNew")) {
			ComboxData comboxData=new ComboxData();
			jsonData jd=new jsonData();
			Integer fkCodeTypeIdString = getInt("CodeTypeId", 0);
			Integer FStateString = getInt("State", 0);
			List<TAppCode> objlist = service.GetListAppCode(fkCodeTypeIdString,
					FStateString);
			if (objlist != null && objlist.size() > 0) {				
				comboxData.setSuccess(true);				
				jd.setList(objlist);
				jd.setTotal(objlist.size());
				jd.setPageNo(1);
				jd.setPageSize(objlist.size());
				jd.setPageCount(1);
				comboxData.setData(jd);
				result = getJsonFromObj(comboxData);
			}else {
				comboxData.setSuccess(false);			 		
				comboxData.setData(null);
				result = getJsonFromObj(comboxData);
			}
		}
		/////MenuCode
		if(commandString.equals("getAppCodeToMenu")){
			Integer fkCodeTypeIdString = getInt("CodeTypeId", 0);
			Integer FStateString = getInt("State", 0);
			List<TAppCode> objlist = service.GetListAppCode(fkCodeTypeIdString,
					FStateString);
			 
			List list = new ArrayList();
			if (null!=objlist && objlist.size() > 0) {
				
				for(int i=0;i<objlist.size();i++) {
					Map<String, Object> map = new HashMap();
					map.put("id", objlist.get(i).getFId());
					map.put("text", objlist.get(i).getFCodeText());
					map.put("href", "#");
					map.put("iconCls", "icon-code01");
					list.add(map);
				}
				result = getJsonFromArray(list);						
			}else {
				result = getJsonFromArray(null);
			}
		}
		if(commandString.equals("getAppTreeCodeToMenu")){
			 Integer id=getInt("id", -1);
			 Integer isMulti=getInt("isMulti", 0);
			 List objlist= service.getCodeTreeDataToMenu(id.toString(),isMulti==1 ?true:false);
			 result = getJsonFromArray(objlist);
		}
		writeJsonString(result);
	}
	@RequestMapping(value = "/GetCode/GroupUser/")
	public void getUserData(HttpServletResponse response) {	
		CurrentResponse =response;
		message = new ResultMessage();
		Integer fOrgId= getInt("FOrgId", 0);
		String Station = getString("Station", "ALL");
		List<ViewGroupUser> users=null;		 
		users = service.getUserByOrgId(fOrgId,Station);		 
		message.setSuccess(true);
		message.setRoot(users);
		message.setTotalProperty(users.size());
		writeJsonString(getJsonFromObj(message));
	}
	
	@RequestMapping(value = "/GetCode/ClientTree/{pid}")
	public void queryEntrustUnitTreeFoPID(@PathVariable("pid") Integer pid,HttpServletResponse response) {
		CurrentResponse = response;
		String result = "[";
		// ResultMessage message = new ResultMessage();		
		List<ViewClientTree> objlist = service.GetListClientForPID(pid);
		// 构造树		
		String iconCls="";	
		for (int i = 0; i < objlist.size(); i++) {
			iconCls="";
			if(objlist.get(i).getIsleft())
				if(objlist.get(i).getIsClient()==1)
				   iconCls=",iconCls:\"isClient-iconCls\"";
			result += "{id:'" + objlist.get(i).getFId().toString()
					+ "',parent:'"
					+ objlist.get(i).getFParentId().toString() + "',text:'"
					+ objlist.get(i).getFName() + "',leaf:" + 
					(objlist.get(i).getIsleft() ? "true": "false")+
					",ShortName:'"+objlist.get(i).getFShortName()+
					"',isClient:'"+objlist.get(i).getIsClient()+"',FClientId:"+
					objlist.get(i).getFClientId().toString()+iconCls+
					(i<objlist.size()-1 ? "},":"}");
		}		
		result += "]";
		writeJsonString(result);
	}
	@RequestMapping(value = "/GetCode/AppOrgTree/{pid}")
	public void queryAppOrgTreeFoPID(@PathVariable("pid") Integer pid,HttpServletResponse response) {
		CurrentResponse = response;
		String result = "[";
		// ResultMessage message = new ResultMessage();		
		List<ViewAppOrgTree> objlist = service.GetListAppOrgForPID(pid);
		// 构造树				
		for (int i = 0; i < objlist.size(); i++) {					
			result += "{id:'" + objlist.get(i).getFId().toString()
					+ "',parent:'"
					+ objlist.get(i).getFParentId().toString() + "',text:'"
					+ objlist.get(i).getFName() + "',leaf:" + 
					(objlist.get(i).getIsleft() ? "true": "false")+					
					(i<objlist.size()-1 ? "},":"}");
		}		
		result += "]";
		writeJsonString(result);
	}
	/**
	 * 
	 * @Description	: 任务树
	 * @Author		: jibb
	 * @Date		: 2013-04-17 11-25
	 * @param pid   
	 * @param response
	 */
	@RequestMapping(value = "/GetCode/TaskTree/{pid}")
	public void queryTaskTreeFoPID(@PathVariable("pid") Integer pid,HttpServletResponse response) {
		CurrentResponse = response;
		/**
		 * isHistory:默认为0，获取不是历史任务，1为历史任务,2为所有任务
		 */
		Integer isHistory=getInt("ISHISTORY", 0);
		String result = "[";
		// ResultMessage message = new ResultMessage();		
		List<ViewTask> objlist = service.GetListTaskTreeForPID(pid,isHistory);
		// 构造树		
		String iconCls="";
		for (int i = 0; i < objlist.size(); i++) {
			iconCls="";
			if(objlist.get(i).getIsleft())			    
				if(objlist.get(i).getIsTask()==1)
				   iconCls=",iconCls:\"isTask-iconCls\"";
			if(isHistory==2){
			    result += "{id:'" + objlist.get(i).getFId().toString()
                        + "',parent:'"+ objlist.get(i).getFParentId().toString() 
                        + "',text:'"+ objlist.get(i).getFName() 
                        + "',leaf:" + (objlist.get(i).getIsleft() ? "true": "false")
                        +",IsTask:'"+ objlist.get(i).getIsTask()
                        +"',FTaskId:"+ objlist.get(i).getFTaskId().toString()
                        +",FTaskNumbers:'"+objlist.get(i).getFTaskNumbers()
                        +"',FDeptMgrId:'"+objlist.get(i).getFDeptMgrId()
                        +"',FServiceCategory:'"+objlist.get(i).getFServiceCategory()+"'"+iconCls
                        +(i<objlist.size()-1 ? "},":"}");
			    continue;
			}
			if(objlist.get(i).getIsTask()==0){
			    result += "{id:'" + objlist.get(i).getFId().toString()
                + "',parent:'"+ objlist.get(i).getFParentId().toString() 
                + "',text:'"+ objlist.get(i).getFName() 
                + "',leaf:" + (objlist.get(i).getIsleft() ? "true": "false")
                +",IsTask:'"+ objlist.get(i).getIsTask()
                +"',FTaskId:"+ objlist.get(i).getFTaskId().toString()
                +",FTaskNumbers:'"+objlist.get(i).getFTaskNumbers()
                +"',FDeptMgrId:'"+objlist.get(i).getFDeptMgrId()
                +"',FServiceCategory:'"+objlist.get(i).getFServiceCategory()+"'"+iconCls
                +(i<objlist.size()-1 ? "},":"}");
			}else if(objlist.get(i).getFIshistory()==isHistory)
    			{
        			result += "{id:'" + objlist.get(i).getFId().toString()
        					+ "',parent:'"+ objlist.get(i).getFParentId().toString() 
        					+ "',text:'"+ objlist.get(i).getFName() 
        					+ "',leaf:" + (objlist.get(i).getIsleft() ? "true": "false")
        					+",IsTask:'"+ objlist.get(i).getIsTask()
        					+"',FTaskId:"+ objlist.get(i).getFTaskId().toString()
        					+",FTaskNumbers:'"+objlist.get(i).getFTaskNumbers()
        					+"',FDeptMgrId:'"+objlist.get(i).getFDeptMgrId()
        					+"',FServiceCategory:'"+objlist.get(i).getFServiceCategory()+"'"+iconCls
        					+(i<objlist.size()-1 ? "},":"}");
    			}
		}		
		result += "]";
		writeJsonString(result);
	}
	
	
	////////////////////EasyUI Tree ////////////////////////////////////////////////////////////////////////////
	
	//一次加载
	@SuppressWarnings("rawtypes")
    @RequestMapping(value = "/GetCode/CodeTreeExt/{id}/")
	public void getCodeTreeData(@PathVariable("id") Integer id,HttpServletResponse response) {
		CurrentResponse = response;		      
        String treeJson = ""; 
        List objlist= service.getCodeTreeData(id.toString());
        treeJson= getJsonFromArray(objlist);
        writeJsonString(treeJson);
    }
	//组织机构用户树,一次加载
	@SuppressWarnings("rawtypes")
    @RequestMapping(value = "/GetCode/OrgUserTreeExt/{id}/")
	public void getOrgUserTreeData(@PathVariable("id") Integer id,HttpServletResponse response) {
		CurrentResponse = response;		      
        String treeJson = "";
        String deptIdString= getString("DeptId", "-1");
        List objlist= service.getOrgUserTreeData(id.toString(),deptIdString);
        treeJson= getJsonFromArray(objlist);
        writeJsonString(treeJson);
    }
	//组织机构用户树 TasyUITreeGrid
	@SuppressWarnings("rawtypes")
    @RequestMapping(value = "/GetCode/OrgUserTreeEasyTree/")
	public void getOrgUserForEasyTree(HttpServletResponse response) {	    
        CurrentResponse = response;
        EasyDataGrid ResultJson= new EasyDataGrid();  
        String deptid=getString("DEPTID", "-1");
        List objlist= service.getOrgUserTreeDataEasy(deptid);
        ResultJson.setRows(objlist);
        ResultJson.setTotal((long)objlist.size());//Long.valueOf(service.getQueryTotalCount(currentUserId)));//Long.valueOf(objList.size()));                  
        writeJsonString(getJsonFromObj(ResultJson));
        //treeJson= getJsonFromArray(objlist);
        //writeJsonString(treeJson);
    }
	
	public  boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
	
	
    /** 
     * 异步加载模块树 
     *  
     * @return 
     */
	@RequestMapping(value = "/GetCode/CodeTree/{id}/")
	public void queryCodeTree(@PathVariable("id") Integer id,HttpServletResponse response) {
		CurrentResponse = response;
		//String result = "[";
        // 获取ID 
        //String id = getString("id", "-1");//this.getString("id");
        String roottext = getString("RootText", "根目录");
        TreeNode treeNode = null; 
        String treeJson = ""; 
        // 如果是空，就默认根目录 
        if (isBlank(id.toString())) { 
            treeNode = new TreeNode("0", roottext);//模块根目录"); 
            List<TreeNode> treeChildren = queryAjaxTreeChildren(treeNode); 
            treeNode.setChildren(treeChildren); 
            treeJson = getJsonFromObj(treeNode);//new JsonObject(treeNode).toString(); 
        } else { 
            // 加载子节点 
            treeNode = new TreeNode(id.toString(), ""); 
            List<TreeNode> treeChildren = queryAjaxTreeChildren(treeNode); 
            treeJson = getJsonFromArray(treeChildren);//new JsonArray(treeChildren).toString(); 
            if (!isBlank(treeJson) && treeJson.length() > 2) { 
                treeJson = treeJson.substring(1); 
                treeJson = treeJson.substring(0, treeJson.length() - 1); 
            } 
        }
        writeJsonString("["+treeJson+"]");//Action.SUCCESS; 
    }
    /** 
     * 查询父节点下的子节点 
     *  
     * @return 
     */ 
    private List<TreeNode> queryTreeChildren(TreeNode pNode) {
    	//获得数据        
    	//List<Hashtable> treeData = service.getCodeTreeToHashtable(Integer.parseInt(pNode.getId()));
    	List<ViewCodeTree> treeData = service.getCodeTree(Integer.parseInt(pNode.getId()));
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>(); 
        if (treeData != null && treeData.size() > 0) { 
            for (ViewCodeTree ht : treeData) { 
                String id = ht.getFId().toString(); 
                String text = (String) ht.getFCodeText(); 
                TreeNode childNode = new TreeNode(id, text); 
                treeNodeList.add(childNode); 
            } 
        } 
        return treeNodeList; 
    } 
     
    /** 
     * 查询父节点下的子节点,并且查询子节点的子节点，如果有子节点就将子节点设置成state:closed 
     *  
     * @return 
     */ 
    private List<TreeNode> queryAjaxTreeChildren(TreeNode pNode) {    	
        //List<Hashtable> treeData = service.getCodeTreeToHashtable(Integer.parseInt(pNode.getId())); 
    	List<ViewCodeTree> treeData = service.getCodeTree(Integer.parseInt(pNode.getId()));
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>(); 
        if (treeData != null && treeData.size() > 0) {
        	try {						
	            for (ViewCodeTree ht : treeData) { 
	            	String id = ht.getFId().toString(); 
	                String text = (String) ht.getFCodeText(); 
	                TreeNode childNode = new TreeNode(id, text); 
	                // 查询子节点的是否有子节点 
	                List<TreeNode> childNodeChildren = queryTreeChildren(childNode); 
	                if (childNodeChildren.size() > 0) { 
	                    childNode.setState(TreeNode.STATE_CLOSED); 
	                } 
	                treeNodeList.add(childNode); 
	            } 
        	} catch (Exception e) {
				// TODO: handle exception
			}
        } 
        return treeNodeList; 
    } 
	////////////////////EasyUI Tree ////////////////////////////////////////////////////////////////////////////
    
    /**
     * 
     * @function: 通过类型获得编号
     * @data: 2013-1-5下午6:04:11
     * @author jibinbin
     * @param response
     *
     */
    @RequestMapping(value = "/GetNumber/ForNumberRule/")
	public void GetNumberForCode(HttpServletResponse response) {
		CurrentResponse = response;
		//bnrService
		String codeString=getString("Code", "");
		String titleString=getString("Title", "");
		Integer isHaveSub=getInt("isHaveSub", 0); //默认为无子项 ,1有子项,0为无子项
		String resultTempString=bnrService.getBussNumber(codeString, titleString, (isHaveSub==1 ? true:false));
		if (resultTempString=="-1")
			result = FAIL_STRING;
		else {			
			result = SUCCESS_STRING + ",\"Number\":\"" + resultTempString + "\"}";
		}
		result = result.replace("},", ",");
        //writeJsonString("["+result+"]");
		writeJsonString(result);
    }
    
    /**
     * 获得编号
     * @param response
     */
    @RequestMapping(value = "/GetNumber/FunForNumber/")
	public void GetNumberForExt(HttpServletResponse response) {
		CurrentResponse = response;
		//bnrService
		String year=getString("year", "");
		String taskname=getString("taskname", "");
		Integer fid=getInt("fid", -1);
		String numbercode=getString("numbercode", ""); 
		String resultTempString=bnrService.getBussNumberForFun(year, taskname, numbercode,fid);
		if (resultTempString=="-1")
			result = FAIL_STRING;
		else {			
			result = SUCCESS_STRING + ",\"Number\":\"" + resultTempString + "\"}";
		}
		result = result.replace("},", ",");
        //writeJsonString("["+result+"]");
		writeJsonString(result);
    }
    
}
