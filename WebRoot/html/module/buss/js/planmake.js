//$(document).bind("contextmenu",function(){return false;});
	$(document).bind("selectstart",function(){return false;});
	
  	function showLoadings(){
		jQuery('#MainDiv').showLoading({ 'addClass': 'loading-indicator-bars'});
	}
  	function  addDivClass(){
		$("#divDisable").addClass("div-disabled");
	}
	function hideLoadings(){
		jQuery('#MainDiv').hideLoading();
	}
	showLoadings();	 
	if($.browser.msie){
			if($.browser.version<=8){
				setActiveStyleSheet("papers.css");
			}else				
				setActiveStyleSheet("papersother.css");
		} else if($.browser.mozilla){
			setActiveStyleSheet("papersother.css");
		}else
		setActiveStyleSheet("papersother.css");

	function setActiveStyleSheet(filename) {
		document.write("<link href=\"..\/..\/css\/"+filename+"\" type=\"text\/css\" rel=\"stylesheet\">");
	}
	
	var IFrame='<iframe src="{0}" style="width:100%;height:98%;border-width: 0px;overflow-x:hidden;overflow-y:hidden"/>';
	var action;
	var processId;
	var activeId;
	var formPKID;
	var state;
	var userobj;
	var oTablePT =null;
	function MessageInfo(Title,Msg){
			$.messager.show({
				title:Title,
				msg:Msg,
				showType:'fade',
				timeout:3000,
				style:{
					right:'',
					bottom:''
				}
			});
		}
	function todom(dom){
		var body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');
		body.animate({scrollTop: $(dom).offset().top}, 500);
	}
	/**  
	    判断输入框中输入的日期格式是否为 yyyy-mm-dd   或yyyy-m-d
	  */  
	function isDate(dateString){
	  var flag=false;		
	  if(dateString.trim()=="") 
	  {
		  //MessageInfo("系统提示","请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例    如：2008-08-08\n\r");
	      return flag;
	  }
	  //年月日正则表达式
	  var r=dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	        if(r==null){
	        	MessageInfo("系统提示","请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例    如：2008-08-08\n\r");
	        	flag=false;
	        }else
	        	flag=true;
	   return flag;
	  }
	function getEasyValue(obj,type){
		var value;
		switch (type) {
			case "combo":
				value=obj.combo('getValue');
				break;
			case "combobox":
				value=obj.combobox('getValue');
				break;
			case "combotree":
				value=obj.combotree('getValue');
				break;
			case "numberbox":
				value=obj.numberbox('getValue');
				break;
			case "datebox":
				value=obj.datebox('getValue');
				break;
			case "datetimebox":
				value=obj.datetimebox('getValue');
				if(!isDate(value))
					value="";				
				break;
		}
		return value;
	}
	function setEasyRequired(obj,type){
		switch (type) {
			case "combo":
				obj.combo({required:true});
				break;
			case "combobox":
				obj.combobox({required:true});
				break;
			case "combotree":
				obj.combotree({required:true});
				break;
			case "numberbox":
				obj.numberbox({required:true});
				break;
			case "datebox":
				obj.datebox({required:true});
				break;
			case "datetimebox":
				obj.datetimebox({required:true});
				break;
		}
	}
	$.fn.extend({        
	   validData :function(msg,isShowMsg,easytype,maxChar){   //msg 例如：'不能为空！请仔细填写。'		
		   var obj=$(this);
	   	   var value;
	   	   if(isShowMsg==1){
	   			if(easytype!="")
		   			value=	getEasyValue(obj,easytype); 
	   			else
	   				value= obj.val();	   			
		   		if(value=="" || value==null)
				   {	if(easytype=="datetimebox")
					   		MessageInfo("系统提示",msg+",请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例    如：2008-08-08\n\r");
				   		else{
				   			MessageInfo('系统提示',msg);
				   		}
						if(easytype!="")
							setEasyRequired(obj,easytype);						
					    return false;
				   }else{
					    if(maxChar>0){
						    if(value.length>maxChar){
						    	MessageInfo('系统提示',msg+"或长度超过"+maxChar);
								return false;
						    }else
						    	return true;
					    }else
					    	return true;
				   }
	   	   }else
	   		{
				   if(trim(obj.val())=="" || obj.val()=="该输入项为必输项！")
				   {
						obj.addClass("classerror");
					    obj.val("该输入项为必输项！");									
					    obj.focus(function(){
							obj.removeClass("classerror");
					    });							
						todom(this);
					    return false;
				   }else{						   
						   if(maxChar>0){
							    if(obj.val().length>maxChar){
							    	MessageInfo('系统提示',msg+"或长度超过"+maxChar);
									return false;
							    }else
							    	return true;
						   
						   }else
						    	return true;
					   }
	   		}
	   	   
		}				
	});
	function PostTask(id) {
		var entitys={
	    		userId:$('#userID').val(),
	    		userName:$('#userName').val(),
	    		ID:id
			};
			var entityJSON= Base64.encode($.toJSON(entitys));
	    	var win = $(IFrame.format('/html/module/controls/PostTask.html?entitys=' +entityJSON));
			$.lightbox(win, {
			    modal  : true,
				width   : 539,
			    height  : 320
		   }); 			
		return false;
	}
	function WriteOpinion(formid,tablename,updatefield,userid) {
		var url="/html/module/public/OpinionBoxEasy.html"+"?formid="+formid+"&tablename="+tablename+"&updatefield="+updatefield+"&userid="+userid;
		var win  = $(IFrame.format(url));
		$.lightbox(win,{
			modal: true,
			width: 648,
			height: 365,
			onClose : function(){				
				window.location.reload();
			}
		});
		return false;
	}
	var postStyle=1;
	function ToPart(entitys){
					if(postStyle==1){
						info="提交";
					}
					if(postStyle==2){
						info="审批";
					}
					$.post("/Buss/TaskPlanService/11", {
								"ProcessId" :  Request.QueryString("processId"),
								"AcceptUserId" : entitys.acceptUserId,
								"AboveActId" :  Request.QueryString("activeId"),
								"Remark" :entitys.remark,
								"formPKID":  Request.QueryString("formPKID"),								
							}, function(data) {
								if(data.success){
									$.LightBoxObject.close();
									setButtonState("#aT_OK,#aT_BJ,#aT_Post,#aT_Approval,#aT_CuiBan,#aB_OK,#aB_BJ,#aB_Post,#aB_Approval,#aB_CuiBan",false);
									$("#FDeptOpinions").attr("readonly","true");
									setButtonState("#aDeptLink",false);			
									setButtonState("#addPerson,#DelPerson",false);
									setButtonState("#aDeptLink",false);
									MessageInfo('系统提示', info+'成功。');
								}else{
									$.LightBoxObject.close();
									MessageInfo('系统提示', '失败！');
								}
						}, "json");
		}
		function BLQK(processId){
			var href="../wf/HandleStatus.html?processId="+processId;
			parent.NavigateUrl('wf_handle_'+processId,'办理情况',href);
			return false;
		}
		function setButtonState(id,state){
			if(state)
				$(id).show();
			else
				$(id).hide();
		}
		function setFormButtonState(us,iaction,istate){
			setButtonState("#aT_OK,#aT_QSH,#aT_BJ,#aT_Post,#aT_COPY,#aT_ApprovalY,#aT_ApprovalN,#aT_CuiBan,#aT_READ",false);
			setButtonState("#aB_OK,#aB_QSH,#aB_BJ,#aB_Post,#aB_COPY,#aB_ApprovalY,#aB_ApprovalN,#aB_CuiBan,#aB_READ",false);
			setButtonState("#tbool",false);	
			$("#FDeptOpinions").attr("readonly","true");
			$("#FOther").attr("readonly","true");
			setButtonState("#aDeptLink",false);
			switch(iaction){
				case "0":{
					//setButtonState("#aT_OK,#aT_QSH,#aT_BJ,#aT_Post,#aT_COPY,#aT_Approval,#aT_CuiBan,#aB_OK,#aB_QSH,#aB_BJ,#aB_Post,#aB_COPY,#aB_Approval,#aB_CuiBan",true);
					setButtonState("#aT_OK,#aB_OK",true);
					setButtonState("#tbool",true);	
					$("#FDeptOpinions").attr("FOther","false");
					break;
				}
				case "1":{
					switch(istate){
						case "0":{	//起草
							setButtonState("#aT_OK,#aB_OK",true);
							setButtonState("#tbool",true);
							$("#FOther").attr("readonly","false");
							break;
						}
						case "1":{	//正在办理								
							//当前办理人不是登录者时处理
							if($("#userID").val() == $("#fAcceptUser").val()){								
								switch($("#FCurrentStep").val().toString()){
									case "10":
										setButtonState("#aT_OK,#aT_Post,#aB_OK,#aB_Post",true);
										setButtonState("#tbool",true);
										$("#FOther").attr("readonly","false");
										break;
									case "20":										
										$("#FDeptOpinions").attr("readonly","true");
										setButtonState("#aDeptLink",true);
										setButtonState("#aT_QSH,#aT_ApprovalY,#aT_ApprovalN,#aB_QSH,#aB_ApprovalY,#aB_ApprovalN",true);										
										break;
									case "21":	//通过									
										setButtonState("#aT_QSH,#aT_COPY,#aB_QSH,#aB_COPY",true);										
										break;
									case "22":	//不通过									
										setButtonState("#aT_OK,#aT_Post,#aB_OK,#aB_Post,#aT_QSH,#aB_QSH",true);
										setButtonState("#tbool",true);
										break;
									case "30":
										setButtonState("#aT_QSH,#aT_BACKTO,#aT_ADDPLAN,#aB_QSH,#aB_BACKTO,#aB_ADDPLAN",true);
										break;
									case "40":
										setButtonState("#aT_BLQK,#aB_BLQK",true);
										break;
									case "50":
										setButtonState("#aT_QSH,#aT_READ,#aB_QSH,#aB_READ",true);
										break;
								}
							}else
								{
									setButtonState("#aT_CuiBan,#aB_CuiBan",true);
								}
							break;
						}
						case "2":{	//业务退回
							if($("#userID").val() == $("#fAcceptUser").val()){
								setButtonState("#aT_BLQK,#aB_BLQK",true);
							}else{
									setButtonState("#aT_CuiBan,#aB_CuiBan",true);
								}
							break;
						}
						case "3":{	//业务终止
							setButtonState("#aT_BLQK,#aB_BLQK",true);
							break;
						}
						case "4":{	//办理完成
							setButtonState("#aT_BLQK,#aB_BLQK",true);
							break;
						}
						case "5":{	//已归档
							setButtonState("#aT_BLQK,#aB_BLQK",true);
							break;
						}											
					}
					break;
				}
				case "2":{
					setButtonState("#aT_BLQK,#aB_BLQK",true);
					setButtonState("#aT_QSH,#aB_QSH",false);
					break;
				}
				case "3":{
					setButtonState("#aT_READ,#aB_READ",true);
					break;
				}
			}
			if($("#isAcceptance").val()=="1") {//已签收
				setButtonState("#aT_QSH,#aB_QSH",false);
			}
		}
		function fn_loadteamperson(taskplanid){
			$("#teamperson").datagrid({				
				width: '98%',
				showHeader:true,
				nowrap:false,//false设置数据自动换行
				//height: 151,
				columns:[
					[{title:'FId',field:'FId',width:60,hidden:true},
					 	{title:'姓名',field:'FPersonnelName',width:110,align:'center'},
						{title:'工作内容',field:'FJobContent',width:302},
						{title:'担任职务',field:'FAsPosition',width:141,align:'center'},
						{title:'联系电话',field:'FContactPhone',width:143,align:'center'}				
					]],
				url: "/Buss/TaskPlanService/searchPlanPerson/"+taskplanid,				
				rownumbers:true,fit:true,singleSelect:true,toolbar:'#tbool',
				//pagination:true,
				onSelect: function(index,row){				
					$("#mgttableperson").show();
					$("#PFId").attr("value",row.FId);
					$("#PfkImplementPlanId").attr("value",row.FkImplementPlanId);
					$("#PFPersonnelId").combotree('setValue',row.FPersonnelId);
					$("#PFPersonnelName").attr("value",row.FPersonnelName);
					$("#PFJobContent").attr("value",row.FJobContent);
					$("#PFAsPosition").attr("value",row.FAsPosition);
					$("#PFContactPhone").attr("value",row.FContactPhone);
				},
				onLoadSuccess:function(){
				  
				},
				rowStyler:function(index,row){						
				}
			});	
		}
		function getPersonTotal(){			
			return $("#teamperson").datagrid('getData').total;
		}
		
		function fn_initpage(){								
			action = Request.QueryString("action");
			processId = Request.QueryString("processId");
			activeId = Request.QueryString("activeId");
			formPKID = Request.QueryString("formPKID");
			state = Request.QueryString("state");
			$("#action").attr("value",action);
			$("#processId").attr("value",processId);
			$("#activeId").attr("value",activeId);
			$("#formPKID").attr("value",formPKID);
			$("#state").attr("value",state);
			$("#FDeptOpinionTime").datetimebox({disabled:true});
			var options = {
		            required: "true",
		            missingMessage: "该项为必填项！格式如：2013-01-01",
		            formatter: formater
		     };
			$("#FCollectDataTime").datetimebox(options);
			$("#FProcessImnTime").datetimebox(options);
			$("#FSubmitRewTime").datetimebox(options);
			$("#FIssueResultsTime").datetimebox(options);
			
		}
		//格式化日期  
		function formater(date)  
		{  
		    return (date.getFullYear()+"-"+(parseInt(date.getMonth())+1) +"-"+date.getDate()).replace(/\b(\w)\b/g, '0$1');  
		}  
		function fn_loadCode(){
			 $('#PFPersonnelId').combotree ({								
					url: "/GetCode/OrgUserTreeExt/0/",
					panelWidth: 251,
					cascadeCheck:false,							
					required:false,
					onBeforeSelect:function(node){
						if(!$(this).tree('isLeaf',node.target)){
							return false;
						}
					},
					onClick:function(node){
						if(!$(this).tree('isLeaf',node.target)){
							//$('#FProjMgrId').combo('showPanel');
							MessageInfo('系统提示','部门不能选择！');
						}
					}
			 });
		 }
		function LoadPersonTeam(pid){					
			oTablePT=$('#personTeam').dataTable( {
				"sScrollX": "98%",   //表格的宽度 
				"sScrollY": "151px",
				"bPaginate": false,
				//"sScrollXInner": "101%",   //表格的内容宽度
				"bProcessing": true,  //是否启用进度显示
				"bScrollCollapse": true,
				"bLengthChange": false,  //每页显示的记录数
				"bFilter": false, //搜索栏
				"bSort": false, //是否支持排序功能
				"bInfo": false, //页脚信息
				"bDestory": true,
				"bRetrieve": true,
				"bAutoWidth": false,  //自适应宽度
				"sPaginationType": "full_numbers",//two_button or full_numbers
				"bStateSave": true, 
				"bServerSide": true,  //是否启用服务器处理数据源
				"sServerMethod": "POST",
				"sAjaxSource": "/Buss/TaskPlanService/searchPlanPerson/"+pid,
				"bDeferRender": true,
				"aoColumns": [							
					{ "mData": "FId","bVisible": false,"fnRender":function(oObj){
						return oObj.aData.FId;//+'<a rel="#" id="deletePerson">'+"删除"+'</a>';
					}},
					{ "mData": "fkImplementPlanId","sWidth":"6px","bVisible": false},
					{ "mData": "FPersonnelId","sWidth":"6px","bVisible": false},
					{ "mData": "FPersonnelName"},
					{ "mData": "FJobContent"},
					{ "mData": "FAsPosition"},
					{ "mData": "FContactPhone"}
				],/*
				"aoColumnDefs":[
					{ "bVisible": false, "aTargets": [1] },
					{ "bVisible": false, "aTargets": [2] }
				],*/
			    "bJQueryUI": false
		  });													 
	  	}
		function fn_loadData(){
			 if (action == 0) {
					$.post("/Buss/TaskPlanService/3", {
						 "TaskID" : 0
					 }, function(data) {
						 SetFormValue(data, "New");
						 hideLoadings();
					 }, "json");
				 } else if (action == 1 ||  action == 3) { //办理					
					 $.post("/Buss/TaskPlanService/2", {
						  "ID" : formPKID,
						  "activeId": activeId
				       }, function(data) {
							 if (data.success) {
								 fn_loadteamperson(formPKID);
								 SetFormValue(data, "Edit");
								 hideLoadings();
							 } else {
								 $.messager.alert('系统提示','任务计划获取失败！检查网络状况是否正常,再重试.','error');
								 hideLoadings();
								 return false;
							 }
					 }, "json");
				 } else if (action == 2) {
					// addDivClass();
					 $.post("/Buss/TaskPlanService/2", {
						  "ID" : formPKID,
						  "activeId": activeId
				       }, function(data) {
							 if (data.success) {
								 fn_loadteamperson(formPKID);
								 SetFormValue(data, "View");
								 hideLoadings();
							 } else {
								 MessageInfo('系统提示', '任务计划获取失败，请检查网络或联系系统管理员！');
								 hideLoadings();
								 return false;
							 }
					 }, "json");					 
				 }
		}
		function loadBLQK(processId)
		{
			setTimeout( function(){loadqkTable() } , 3000);
			function loadqkTable(){
				$("#qkTable").datagrid({
					title:'办理情况(近4次)',
					width: '98%',
					showHeader:false,
					nowrap:false,//false设置数据自动换行
					columns:[
						[{field:'FStyle',width:66,align:'center',
							formatter: function(value,row,index){
								if(value=="1")
									return "办理情况";
								else if(value=="2")
									return "催办情况";
								else if(value=="3")
									return "打回情况";
								else if(value=="4")
									return "抄送情况";
								else if(value=="-1")
									return "";
								else if(value=="-2")
									return "";
								else if(value=="-3")
									return "";
								else if(value=="-4")
									return "";
							}},
						{field:'sendUser',width:60,hidden:false},
						{field:'FSendTime',width:131,align:'center'},
						{field:'acceptUser',width:60,align:'center'},
						{field:'FAcceptTime',width:131,align:'center'},
						{field:'FStateText',width:70,align:'center'},
						{field:'completeTime',width:131,align:'center'},
						{field:'FRemark',width:130,align:'center'}						
						]],
					url: '/Buss/CommonOpinionService/6/?processId='+processId+"&toprecord="+4,
					rownumbers:false,fit:true,singleSelect:true,
					//pagination:true,
					onSelect: function(index,row){				
						$("#FId").attr("value",row.FId);
						$("#FContentT").text(row.FContent);
					},
					onLoadSuccess:function(){
					  //所有列进行合并操作
					  //$(this).datagrid("autoMergeCells");
					  //指定列进行合并操作
					  $(this).datagrid("autoMergeCells",['FStyle','sendUser','FStateText']);
					  mergeCellsByField("qkTable","FAcceptTime","FStateText","FStateText","completeTime","FRemark");
					},
					rowStyler:function(index,row){
						if(row.FStyle=="-1" || row.FStyle=="-2" || row.FStyle=="-3" || row.FStyle=="-4")
							return 'background-color: #fafafa;'+
									  'background: -webkit-linear-gradient(top,#fdfdfd 0,#f5f5f5 100%);'+
									  'background: -moz-linear-gradient(top,#fdfdfd 0,#f5f5f5 100%);'+
									  'background: -o-linear-gradient(top,#fdfdfd 0,#f5f5f5 100%);'+
									  'background: linear-gradient(to bottom,#fdfdfd 0,#f5f5f5 100%);'+
									  'background-repeat: repeat-x;'+
									  'filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#fdfdfd,endColorstr=#f5f5f5,GradientType=0);'+
									  'color:blue;font-weight:bold;';
					}
				});
			}
		}
		function fn_error(){
			MessageInfo('加载数据错误！请重试。', '系统提示');
		}
		function fn_success(data){
			if(data){
				if (data.success) {			
					$("#userID").attr("value",data.root.userID);
					$("#userName").attr("value",data.root.userName);
					$("#userOrgID").attr("value",data.root.userOrgID);
					$("#unitStation").attr("value",data.root.unitStation);
					$("#fAcceptUser").attr("value",data.root.fAcceptUser);
					$("#fState").attr("value",data.root.fState);
					$("#isAcceptance").attr("value",data.root.isAcceptance);
					fn_initpage();
					fn_loadCode();
					fn_loadData();
					loadBLQK(processId);
				}else
				{
						userobj=null;					
						return false;
				}
			}
		}
		function SetFormValue(data, type) {
			$("#FId").attr("value", data.root[0].FId);
			$("#FCurrentStep").attr("value", data.root[0].FCurrentStep);
			$("#FLastStep").attr("value", data.root[0].FLastStep);			
			$("#FDeptMgrId").attr("value", data.root[0].FDeptMgrId);
			$("#FkTaskId").attr("value",data.root[0].fkTaskId);			
			$("#FPlanNumbers").attr("value",data.root[0].FPlanNumbers);
			$("#Ffwlb").text(data.root[0].FServiceCategoryName);
			$("#Frwmc").text(data.root[0].FTaskName);
			$("#Fxmjl").text(data.root[0].FProjMgrName);
			$("#Fwtdw").text(data.root[0].FEntrustUnitName);			
			$("#FPlanningPerId").attr("value",data.root[0].FPlanningPerId);
			$("#FCollectDataTime").datetimebox('setValue',data.root[0].FCollectDataTime);
			$("#FProcessImnTime").datetimebox('setValue',data.root[0].FProcessImnTime);
			$("#FSubmitRewTime").datetimebox('setValue',data.root[0].FSubmitRewTime);
			$("#FIssueResultsTime").datetimebox('setValue',data.root[0].FIssueResultsTime);
			$("#FOther").attr("value",data.root[0].FOther);
			$("#FDeptOpinions").attr('value', data.root[0].FDeptOpinion);
			$("#FDeptOpinionTime").datetimebox('setValue',data.root[0].FDeptOpinionTime);//val(data.root[0].FDeptOpinionTime);
			$("#PfkImplementPlanId").attr("value",formPKID);
			setFormButtonState($("#unitStation").val().toString(),$("#action").val().toString(),$("#fState").val().toString());
			$("#FPlanNumbers").attr({"readonly":"readonly"});
		}
		function ClearPersonValue(){
			$("#PFId").attr("value",0);
			$("#PfkImplementPlanId").attr("value",formPKID);
			$("#PFPersonnelId").attr("value",0);
			$("#PFPersonnelName").attr("value","");
			$("#PFJobContent").attr("value","");
			$("#PFAsPosition").attr("value","");
			$("#PFContactPhone").attr("value","");										
		}
		function ValidateUser(activeId){
			var type="";
			if(Request.QueryString("action")==3)
				type="1";
			$.ajax({
				url:'/Buss/CommonService/1',
				type:'POST',
				data:{"activeId":activeId,"Type":type},
				dataType: 'json',
				timeout: 3000,
				error: fn_error,				
				success: fn_success
			});
		}
		
		function FUN_POSTDEPT(){
			if($("#OpinionMessagePostDept").val() == '')
			{
				$.messager.alert('系统提示','部门意见不能为空，请填写部门意见！','info');
				return false;
			}
			$("#PostDeptbuttn").attr("disabled",true);
			$.post("/Buss/TaskPlanService/11", {
					"ProcessId" :  $("#processId").val(),
					"AcceptUserId" : $("#FDeptMgrId").val(),
					"AboveActId" :  $("#activeId").val(),
					"Remark" : $("#OpinionMessagePostDept").val(),
					"formPKID":  $("#formPKID").val(),								
				}, function(data) {
					if(data.success){
						setButtonState("#aT_OK,#aT_BJ,#aT_Post,#aT_Approval,#aT_CuiBan,#aB_OK,#aB_BJ,#aB_Post,#aB_CuiBan",false);
						setButtonState("#aT_CuiBan,#aB_CuiBan",true);
						setButtonState("#tbool",false);									
						MessageInfo('系统提示', '已成功提交到部门，请注意办理情况。');
						$("#divPostDEPT").dialog('close');
					}else{											
						$.messager.alert('系统提示','提交失败！检查网络状况是否正常,再重试.','error');
						$("#PostDeptbuttn").attr("disabled",false);
					}
			}, "json");
		}
		//催办
		function FUN_CuiBan(){
			if($("#OpinionMessageCuiBan").val() == '')
			{
				$.messager.alert('系统提示','催办意见不能为空，请填写催办意见！','info');
				return false;
			}
			$("#aCuiBanbuttn").attr("disabled",true);
			$.post("/Buss/TaskPlanService/7", {
						"AboveActId" : $("#activeId").val(),
						"Remark" : $("#OpinionMessageCuiBan").val().trim()
					}, function(data) {
						if(data.success)
							{$.messager.alert('系统提示','催办成功。请注意办理情况。','info');
							$("#divcuiban").dialog('close');}
						else{
							$.messager.alert('系统提示','催办失败！检查网络状况是否正常,再重试.','error');
							$("#aCuiBanbuttn").attr("disabled",false);
						}
			}, "json");
		}
		//通过
		function FUN_ApprovalY(){
			if($("#OpinionMessageApprovalY").val() == '')
			{
				$.messager.alert('系统提示','意见不能为空，请填写意见！','info');
				return false;
			}
			$("#aApprovalYbuttn").attr("disabled",true);
			$.post("/Buss/TaskPlanService/6", {
					"ProcessId" :  $("#processId").val(),
					"AcceptUserId" : $("#FPlanningPerId").val(),
					"AboveActId" :  $("#activeId").val(),
					"Remark" : $("#OpinionMessageApprovalY").val(),
					"formPKID":  $("#formPKID").val(),
					"isApproval": 1
				}, function(data) {
					if(data.success){
						setButtonState("#aT_OK,#aT_BJ,#aT_Post,#aT_ApprovalY,#aT_ApprovalN,#aT_CuiBan,#aB_OK,#aB_BJ,#aB_Post,#aB_CuiBan,#aB_ApprovalY,#aB_ApprovalN",false);
						setButtonState("#aT_CuiBan,#aB_CuiBan",true);													
						MessageInfo('系统提示', '审批通过，已发送到项目计划起草人，请注意办理情况。');
						$("#divApprovalY").dialog('close');
					}else{											
						$.messager.alert('系统提示','操作失败！检查网络状况是否正常,再重试.','error');
						$("#aApprovalYbuttn").attr("disabled",false);
					}
			}, "json");
		}
		//不通过
		function FUN_ApprovalN(){
			if($("#OpinionMessageApprovalN").val() == '')
			{
				$.messager.alert('系统提示','意见不能为空，请填写意见！','info');
				return false;
			}
			$("#aApprovalNbuttn").attr("disabled",true);
			$.post("/Buss/TaskPlanService/6", {
					"ProcessId" :  $("#processId").val(),
					"AcceptUserId" : $("#FPlanningPerId").val(),
					"AboveActId" :  $("#activeId").val(),
					"Remark" : $("#OpinionMessageApprovalN").val(),
					"formPKID":  $("#formPKID").val(),
					"isApproval": 0
				}, function(data) {
					if(data.success){
						setButtonState("#aT_OK,#aT_BJ,#aT_Post,#aT_ApprovalY,#aT_ApprovalN,#aT_CuiBan,#aB_OK,#aB_BJ,#aB_Post,#aB_CuiBan,#aB_ApprovalY,#aB_ApprovalN",false);
						setButtonState("#aT_CuiBan,#aB_CuiBan",true);
						MessageInfo('系统提示', '审批不通过，已发送到项目计划起草人，请注意办理情况。');
						$("#divApprovalN").dialog('close');
					}else{											
						$.messager.alert('系统提示','操作失败！检查网络状况是否正常,再重试.','error');
						$("#aApprovalNbuttn").attr("disabled",false);
					}
			}, "json");
		}
		function FUN_Copy(){
			if($("#OpinionMessageCopy").val() == '')
			{
				$.messager.alert('系统提示','抄送意见不能为空，请填写抄送意见！','info');
				return false;
			}
			$("#aCopybuttn").attr("disabled",true);
			$.messager.confirm('系统提示', '计划将抄送到生产部及总工办！是否抄送？', function(r){
				if (r){
					showLoadings();										
					$.post("/Buss/TaskPlanService/13", {
						"ProcessId" :  $("#processId").val(),											
						"AboveActId" : $("#activeId").val(),											
						"formPKID": $("#formPKID").val(),
						"Remark": $("#OpinionMessageCopy").val()
					}, function(data) {
						if (data.success) {																			
							setButtonState("#aT_COPY,#aB_COPY",false);
							$("#divcopy").dialog('close');
							MessageInfo('系统提示', data.msg);	
							hideLoadings();
						}else
						{									
							$("#aCopybuttn").attr("disabled",false);
							$.messager.alert('系统提示','抄送失败！检查网络状况是否正常,再重试.','error');
							hideLoadings();
							return false;
						}
					}, "json");										
				}
			});
		}
	$(document).ready(function() {			
			$("#mgttableperson").hide();			
			ValidateUser(Request.QueryString("activeId"));			 
			$("#ClosePerson").click(function(){
				$("#mgttableperson").hide();
			});
							var options = {
								target : '#output2',
								beforeSubmit : showRequest,
								success : showResponse,
								url : '/Buss/TaskPlanService/4',
								type : 'post',
								dataType : 'json'
							};
							function showRequest(formData, jqForm, options) {
								/*if (!$("#formplan").validationEngine('validate')) {
									return false;
								}*/
							}
							// post-submit callback 
							function showResponse(responseText, statusText,
									xhr, $form) {
								if (statusText == "success") {
									if (responseText.success) {
										$("#FId").attr("value", responseText.Id);							
										MessageInfo('系统提示', '任务计划保存成功。下一步请提交到部门征求意见。');
										setButtonState("#aT_Post,#aB_Post",true);
									} else{
										$.messager.alert('系统提示','任务计划保存失败！检查网络状况是否正常,再重试.','error');
									}
									return true;
								}
							}
							function FUN_Save() {
								var Id = $('input[name=FId]').fieldValue();
								if (Id == "" || Id != null) {									
									var form = $("#formplan");
									form.ajaxSubmit(options);
									return false;
								}
							}
							//选择方式提交
							function FUN_Post() {
								PostTask($('input[name=FId]').fieldValue());
							}							
							$("#aT_Post,#aB_Post").click(function() {
								if($("#isAcceptance").val()=="0" && $("#FCurrentStep").val()!="10")
								{
									$.messager.alert('系统提示','请先签收！再作其他操作','info');								 
									return false;
								}								
								if(getPersonTotal()>0){
									$("#divPostDEPT").dialog('open');
									$("#divPostDEPT").dialog('center');
								}
								//FUN_Post();
								else
									$.messager.alert('系统提示','项目组人员未安排！请检查。','error');
							});
														
							$("#aT_OK,#aB_OK").click(function() {
								var issave=false;
								issave=isDate($("#FCollectDataTime").datetimebox('getValue'));
								issave=issave && isDate($("#FProcessImnTime").datetimebox('getValue'));
								issave=issave && isDate($("#FSubmitRewTime").datetimebox('getValue'));
								issave=issave && isDate($("#FIssueResultsTime").datetimebox('getValue'));
								issave= issave && $("#FCollectDataTime").validData("收集资料（现场踏勘）时间不能为空！",1,"datetimebox",0);
								//issave= issave && $("#FOther").validData("其他不能为空！",1,"",5);
								issave= issave && $("#FProcessImnTime").validData("过程实施时间不能为空！",1,"datetimebox",0);
								issave= issave && $("#FSubmitRewTime").validData("提交复核时间不能为空！",1,"datetimebox",0);
								issave= issave && $("#FIssueResultsTime").validData("出具正式成果文件时间不能为空！",1,"datetimebox",0);
								if(issave)								
									FUN_Save();
							});
							$("#aT_BJ,#aB_BJ").click(function() {
								$.messager.confirm('系统提示', '您确定要办结吗？办结后任务计划将不能修改！', function(r){
									if (r){										 
										$.post("/Buss/TaskPlanService/12", {
											"ProcessId" :  Request.QueryString("processId"),											
											"AboveActId" :  Request.QueryString("activeId"),											
											"formPKID":  Request.QueryString("formPKID")							
										}, function(data) {
											if (data.success) {												
												MessageInfo('系统提示', '办结成功。请注意办理情况。');	
											}else
											{									
											    $.messager.alert('系统提示','办结失败！检查网络状况是否正常,再重试.','error');
												return false;
											}
										}, "json");										
									}
								});
							});
							
							$("#aDeptLink").click(function() {
								var formId=$("#FId").val();					
								WriteOpinion(formId,'T_ImplementPlan','F_DeptOpinion,F_DeptOpinion_Time',1);
							});
							$("#aT_BLQK,#aB_BLQK").click(function() {
								BLQK($("#processId").val());
							});
							//通过
							$("#aT_ApprovalY,#aB_ApprovalY").click(function() {
								if($("#isAcceptance").val()=="0")
								{
									$.messager.alert('系统提示','请先签收！再作其他操作','info');								 
									return false;
								}
								if($("#FDeptOpinions").val()=='')
								{
									$.messager.alert('系统提示','请先填写部门意见！','info');
									return true;
								}								
								$("#divApprovalY").dialog('open');
								$("#divApprovalY").dialog('center');
							});
							
							//不通过
							$("#aT_ApprovalN,#aB_ApprovalN").click(function() {	
								if($("#isAcceptance").val()=="0")
								{
									$.messager.alert('系统提示','请先签收！再作其他操作','info');								 
									return false;
								}
								if($("#FDeptOpinions").val()=='')
								{
									$.messager.alert('系统提示','请先填写部门意见！','error');
									return true;
								}								
								$("#divApprovalN").dialog('open');
								$("#divApprovalN").dialog('center');
							});
							$("#aT_CuiBan,#aB_CuiBan").click(function() {
								$("#divcuiban").dialog('open');
								$("#divcuiban").dialog('center');
							});
							//抄送
							$("#aT_COPY,#aB_COPY").click(function(){
								if($("#isAcceptance").val()=="0")
								{
									$.messager.alert('系统提示','请先签收！再作其他操作','info');								 
									return false;
								}
								$('#divcopy').dialog('open');
								$('#divcopy').dialog('center');
							});
							//签收
							$("#aT_QSH,#aB_QSH").click(function() {
								var isUpdateOther=0;								
								$.messager.confirm('系统提示', '您确定要签收该任务吗？', function(r){
									if (r){							
										var Id = $("#FId").val();
										$.post("/Buss/TaskPlanService/14", {
												"AboveActId" : activeId,
												"FormFId":Id,
												"isUpdateOther": isUpdateOther
					   						}, function(data) {
					   							if (data.success){
					   								$.messager.alert('系统提示','签收成功。','info');
					   								setButtonState("#aT_QSH,#aB_QSH",false);
													$("#isAcceptance").val("1");
					   							}
					   							else
					   								$.messager.alert('系统提示','签收失败！检查网络状况是否正常,再重试.','error');
										}, "json");							
									}
								});
							});	
							//已阅
							$("#aT_READ,#aB_READ").click(function() {	
								$.messager.confirm('系统提示', '您确定是否已经阅读完毕？', function(r){
									if (r){							
										var Id = $("#FId").val();
										$.post("/Buss/TaskPlanService/15", {
												ProcessId : processId,
												AboveActId : activeId,
												FormFId: Id
					   						}, function(data) {
					   							if (data.success){					   								
					   								setButtonState("#aT_READ,#aB_READ",false);
					   								$.messager.alert('系统提示','阅读成功。','info');
					   							}
					   							else
					   								$.messager.alert('系统提示','操作失败！检查网络状况是否正常,再重试.','error');
										}, "json");							
									}
								});
							});	
							
							//添加组员			
							$("#append").click(function(){
								  fn_loadCode();
									$("#mgttableperson").show();
									if(!$("#FId").val())
									{
										return false;
									}				
									ClearPersonValue();
							});
							$("#remove").click(function(){
									if(!$("#FId").val())
									{
										return false;
									}
									var srow =  $('#teamperson').datagrid("getSelected");									
									if(srow){
										$.messager.confirm('系统提示', '您确定要删除的组员记录吗？', function(r){
											if (r){
											    var id = srow.FId;
											    $.post("/Buss/TaskPlanService/9", {							 
													"TPId" : id
												   }, function(data) {
														 if (data.success) {
															  $("#mgttableperson").hide();
															  ClearPersonValue();
															  $('#teamperson').datagrid("reload");
														 } else {
															 $.messager.alert('系统提示','组员删除失败！检查网络状况是否正常,再重试.','error');
															 return false;
														 }
												}, "json");
											}
										});
									}								
									else
										MessageInfo('系统提示', '请选择要删除的组员记录。');
							});
							$("#accept").click(function(){
								    if($("#PFPersonnelId").combotree('getText')==""){
								    	$.messager.alert('系统提示','人员姓名不能为空请选择！','error');
								    	return;
								    }
								    if($("#PFJobContent").val()==""){
								    	$.messager.alert('系统提示','工作内容不能为空请填写！','error');
								    	return;
								    }
								    if($("#PFAsPosition").val()==""){
								    	$.messager.alert('系统提示','担任职务不能为空请填写！','error');
								    	return;
								    }
								    if($("#PFContactPhone").val()==""){
								    	$.messager.alert('系统提示','联系方式不能为空请填写！','error');
								    	return;
								    }
									$.post("/Buss/TaskPlanService/8", {
										  //"ID" : $.request.queryString["ID"]
											"PFId" : $("#PFId").val(),
											"PFkImplementPlanId" : $("#PfkImplementPlanId").val(),
											"PFPersonnelId" :  $("#PFPersonnelId").combotree('getValue'),
											"PFPersonnelName" : $("#PFPersonnelId").combotree('getText'),
											"PFJobContent" : $("#PFJobContent").val(),
											"PFAsPosition" : $("#PFAsPosition").val(),
											"PFContactPhone" : $("#PFContactPhone").val()
									       }, function(data) {
												 if (data.success) {
													 $('#teamperson').datagrid("reload");
												 } else {
													 $.messager.alert('系统提示','组员保存失败！检查网络状况是否正常,再重试.','error');
													 return false;
												 }
									}, "json");
									$("#mgttableperson").hide();
							});
							$("#reject").click(function(){
								 $('#teamperson').datagrid("reload");
							});
		});
		/* Get the rows which are currently selected */
		 function fnGetSelected( oTableLocal )
		 {
			 return oTableLocal.$('tr.row_selected');
		 }