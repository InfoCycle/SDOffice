//$(document).bind("contextmenu",function(){return false;});
		$(document).bind("selectstart",function(){return false;});  
		function  addDivClass(){
			$("#divDisable").addClass("div-disabled");
		}
		function showLoadings(){
			jQuery('#MainDiv').showLoading({ 'addClass': 'loading-indicator-bars'});
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
		} else{
			setActiveStyleSheet("papersother.css");
		}
		function setActiveStyleSheet(filename) {
			document.write("<link href=\"..\/..\/css\/"+filename+"\" type=\"text\/css\" rel=\"stylesheet\">");
		}
		function closeJBox(){
			$.jBox.close(true);
		}		 
		function MessageInfo(Title,Msg){
			$.messager.show({
				title:Title,
				msg:Msg,
				showType:'fade',
				timeout:2000,
				style:{
					right:'',
					bottom:''
				}
			});
		}
		var IFrame='<iframe src="{0}" style="width:100%;height:98%;border-width: 0px;overflow-x:hidden;overflow-y:hidden"/>';
		var action;
		var processId;
		var activeId;
		var formPKID;
		var state;
		var userobj;
		var alinkId="";
		var textareaID="";
		var saveok=false;
		function todom(dom){
			var body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');
			body.animate({scrollTop: $(dom).offset().top}, 500);
		}

		/**  
	    判断输入框中输入的日期格式是否为 yyyy-mm-dd   或yyyy-m-d
	  */  
	function isDate(dateString){
	  var flag=false;		
	  if(dateString.trim()=="") return flag;
	  //年月日正则表达式
	  var r=dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	        if(r==null){
	        	//MessageInfo("系统提示","请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例    如：2008-08-08\n\r");
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
		
		function PostReport(id) {						
			var entitys={
				 userId:$('#userID').val(),
				 userName:$('#userName').val(),
				 ID:id
			 };
			var entityJSON= Base64.encode($.toJSON(entitys));
			var win = $(IFrame.format('/html/module/controls/PostPAS.html?entitys=' +entityJSON));
			$.lightbox(win, {
				modal  : true,
				width   : 560,
				height  : 385
			 });			
			return false;
		}
		function SelectTask() {
			var Parameter={
			    	ISHISTORY: 0
				};
			var ParameterJSON= Base64.encode($.toJSON(Parameter));				
			var win = $(IFrame.format('/html/module/public/SelectTask.html?Parameter='+ParameterJSON));
				$.lightbox(win, {
					modal  : true,
					width   : 560,
					height  : 385
			   });			
			return false;			
		}		
		function WriteOpinion(formid,tablename,updatefield,userid) {
			//判断是否已签收
			if($("#isAcceptance").val()=="0")
			{
				$.messager.alert('系统提示','请先签收！再作其他操作','info');								 
				return false;
			}
			var url="/html/module/public/OpinionBoxEasy.html"+"?formid="+formid+"&tablename="+tablename+"&updatefield="+updatefield+"&userid="+userid;
			var win = $(IFrame.format(url));
				$.lightbox(win, {
					modal  : true,
					width   : 650,
					height  : 398,
					onClose : function(){
						window.location.reload();
					}
			   });
			return false;
		}
		function FUN_CuiBan(){
			if($("#OpinionMessageCuiBan").val() == '')
			{
				$.messager.alert('系统提示','催办意见不能为空，请填写催办意见！','info');
				return false;
			}
			$.post("/Buss/TaskService/10", {
						"AboveActId" : $("#activeId").val(),
						"Remark" : $("#OpinionMessageCuiBan").val().trim()
					}, function(data) {
						if(data.success){
							$("#divcuiban").dialog('close');
							$.messager.alert('系统提示','催办成功。','info');
						}							
						else
							$.messager.alert('系统提示','催办失败！请重试.','info');
			}, "json");
		}
		function BLQK(processId){
			var href="../wf/HandleStatus.html?processId="+processId;
			parent.NavigateUrl('wf_handle_'+processId,'办理情况',href);
			return false;
		}
		function getTaskInfo(taskid,taskname,taskservertype,tasknumber,deptmgrid){
			$.LightBoxObject.close();			
			$("#fkTaskId").attr("value",taskid);
			$("#FKTaskName").attr("value",taskname);
			$("#FServiceCategory").combotree('setValue',taskservertype);
			$("#FDeptMgrId").attr("value",deptmgrid);
			//获得编号
			
			$.post("/GetNumber/FunForNumber/", {
				"year": new Date().getFullYear(),
				"taskname": tasknumber.substring(0,tasknumber.length-11), 
				"fid": $("#formPKID").val(),
				"numbercode": "XB"
					}, function(data) {
						if (data.success) {
							$("#FNumbers").attr("value",data.Number);
						}else
						{															
							//$.messager.alert('系统提示','编号生成失败，请重试!','error');
							return false;
						}
			}, "json");
		}
		function ToPart(entitys){
			$.post("/Buss/MMReportService/6", {
				"ProcessId" :  $("#processId").val(),
				"AcceptUserId" : entitys.acceptUserId,
				"AboveActId" :  $("#activeId").val(),
				"Remark" : entitys.remark,
				"formPKID": $("#formPKID").val(),
			    "PostType": 1,
			    }, function(data) {
					 if (data.success) {
						 $.LightBoxObject.close();													
						 MessageInfo('系统提示', '已成功提交到相关部门。');
						 setButtonState("#aT_Post,#aT_PostGM,#aT_QSH,#aB_Post,#aB_PostGM,#aB_QSH",false);
						 setButtonState("#aDeptLink",true);
					 }else
					 {				
						 $.LightBoxObject.close();						 
						 MessageInfo('系统提示', '提交异常，请检查网络重试或联系系统管理员！');
						 return false;
				     }
			}, "json");
		}
		 function fn_loadCode(){
			  $('#FProgressId').combobox({
						url:'/GetCode/Query/',  
						valueField:'FId',  
						textField:'FCodeText',
						onSelect: function(record){
							$('#FProgress').val($('#FProgressId').combobox('getText'));
						},
						onBeforeLoad: function(param){
							param.action = "getAppCode";
							param.CodeTypeId = 365;
							param.State = 1;
						}
			   });
			   $('#FServiceCategory').combotree ({
						url: "/GetCode/CodeTreeExt/2/",
							panelWidth: 251,
							cascadeCheck:true
			   });
			   $('#FServiceCategory').combotree('disable');
		  }					
		  function fn_loadData(){
			  if (action == 0) {
					$.post("/Buss/MMReportService/3", {
						"ID" : formPKID,
						"processId0": processId,
						"activeId": activeId
					}, function(data) {						
						if(data.success){
							$("#FId").attr("value", data.Id);
							$("#formPKID").attr("value",data.Id);
							setFormButtonState($("#unitStation").val().toString(),$("#action").val().toString(),$("#fState").val().toString());
							hideLoadings();
						}else
						{							
							MessageInfo('系统提示', '重大事项报告初始化失败，请检查网络后重试或联系系统管理员！');
							hideLoadings();
							return false;
						}
					}, "json");
				} else if (action == 1) {
					$.post("/Buss/MMReportService/1", {
						"ID" : formPKID,
						"processId0": processId,
						"activeId": activeId
					}, function(data) {
						if (data.success) {
							//加载相关部门意见
							getRelatedDetpOpinion(formPKID);							
							SetFormValue(data, "Edit");	
							hideLoadings();
						} else {							
							MessageInfo('系统提示', '重大事项报告获取失败，请检查网络或联系系统管理员！');
							hideLoadings();
							return false;
						}
					}, "json");
				} else if (action == 2) {
					$.post("/Buss/MMReportService/1", {
						"ID" : formPKID,
						"processId0": processId,
						"activeId": activeId
					}, function(data) {
						if (data.success) {
							//加载相关部门意见
							getRelatedDetpOpinion(formPKID);							
							SetFormValue(data, "View");	
							hideLoadings();
						} else {							
							MessageInfo('系统提示', '重大事项报告获取失败，请检查网络或联系系统管理员！');
							hideLoadings();
							return false;
						}
					}, "json");
				}
		  }
		  function CreateDivOtherDept(id,deptId,Name,Opinion,index,size,userId){
				alinkId="alinkId";
				textareaID="FXGDeptOpinion";
				$("<table>")
				.attr("id","tableOtherDept"+id)					
				.css("valign","top")
				.css("margin-top","0px")
				.css("cellpadding","0px")
				.css("cellspacing","0px")
				.css("margin-left","0px")							
				.css("margin-right","0px")
				.css("margin-buttom","0px")
				.appendTo($("#divOtherDept"));
				$("<tr>").attr("id","tableOtherTR01"+id).appendTo($("#tableOtherDept"+id));
				/*根据浏览器类型设置td的宽度*/
				var tdwidth=101;
				if($.browser.msie){
					if($.browser.version<=8){
						tdwidth=101;
					}else				
						tdwidth=101;
				} else if($.browser.mozilla)
					tdwidth=97;
				if(index+1==size){
					$("<td>")
					.attr("id","tableOtherTD01"+id)				
					.width(tdwidth)
					.css("border-width","0px 0px 0px 0px")
					.html(Name+"处理意见")
					.appendTo($("#tableOtherTR01"+id));

					$("<td>")
					.attr("id","tableOtherTD02"+id)
					.width(694)						
					.css("border-width","0px 0px 0px 1px")
					.html("")
					.appendTo($("#tableOtherTR01"+id));
				}else{						
					$("<td>")
					.attr("id","tableOtherTD01"+id)				
					.width(tdwidth)						
					.css("border-width","0px 0px 1px 0px")
					.html(Name+"处理意见")
					.appendTo($("#tableOtherTR01"+id));

					$("<td>")
					.attr("id","tableOtherTD02"+id)				
					.width(694)						
					.css("border-width","0px 0px 1px 1px")
					.html("")
					.appendTo($("#tableOtherTR01"+id));
				}
				textareaID+=deptId;
				alinkId+=deptId.toString()+userId.toString();
               var $temp= $("<div><a id='"+alinkId+"' rel='#'>&lt;&lt;请填写意见 &gt;&gt;</a></div>"+						
					"<textarea cols='79' rows='8'  readonly style='margin-left:8px;margin-top:3px;' "+" id='"+textareaID+"' name='"+textareaID+"' </textarea></br>"						
				);
               
				$temp.appendTo($("#tableOtherTD02"+id));				
				document.getElementById(textareaID).innerHTML=Opinion;
				//加超级链接点击事件
				$("#"+alinkId).click(function() {										
					WriteOpinion(id,'T_RelatedDetpOpinion','F_Dept_Opinion',1);
				});					
				$("#"+alinkId).hide();
				if($("#state").val().toString()=="1")
				{
					var tempid="#alinkId"+$("#userOrgID").val().toString()+$("#userID").val().toString();							
						$(tempid).show();
				}
		   }			  
		  //////////////////////////////////////////////////////////////////////
		  //设置元素可见否
		   function setButtonState(id,state){
				if(state)
					$(id).show();
				else
					$(id).hide();
			}
		  //设置元素是否可用
		   function setElementState(id,state){			
				if(state)
					$(id).attr({readonly:false});
				else
					$(id).attr({readonly:true});							
			}
			function setFormButtonState(us,iaction,istate){				
				setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_Complete,#aT_CuiBan,#aT_PostGM",false);
				setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_Complete,#aB_CuiBan,#aB_PostGM",false);
				setButtonState("#aDeptLink,#aTgmgrLink",false);	
				setElementState("#FNumbers,#FKTaskName,#FEakerName,#FMattersBriefly,#FApplicationSupport,#FQpopionion,#FDeptPrgOpinion,#FTgmgrOpinion",false);
				$("#getFXBNumbers,#butnSelectEU").hide();
				//setButtonState("#aT_BLQK,#aB_BLQK",true);
				switch(iaction){
					case "0":{
						setButtonState("#aT_OK,#aT_Post,#aB_OK,#aB_Post",true);
						setElementState("#FMattersBriefly,#FApplicationSupport,#FQpopionion",true);
						$("#butnSelectEU").show();
						break;
					}
					case "1":{
						switch(istate){
							case "0":{	//起草
								setButtonState("#aT_OK,#aT_Post,#aB_OK,#aB_Post",true);
								setElementState("#FMattersBriefly,#FApplicationSupport,#FQpopionion",true);
								$("#butnSelectEU").show();
								break;
							}
							case "1":{	//正在办理								
								//当前办理人不是登录者时处理
								if($("#userID").val() == $("#fAcceptUser").val()){									
									switch($("#FCurrentStep").val().toString()){
										case "10":
											setButtonState("#aT_OK,#aT_Post,#aB_OK,#aB_Post",true);	
											setElementState("#FMattersBriefly,#FApplicationSupport,#FQpopionion",true);
											$("#butnSelectEU").show();
											break;
										case "20":
											setButtonState("#aT_Post,#aT_PostGM,#aT_QSH,#aB_Post,#aB_PostGM,#aB_QSH",true);
											setButtonState("#aDeptLink",true);
											$('#FProgressId').combobox('disable');
											break;
										case "30":
											setButtonState("#aT_Complete,#aT_QSH,#aB_Complete,#aB_QSH",true);
											$('#FProgressId').combobox('disable');
											break;
										case "40":
											setButtonState("#aT_Complete,#aT_QSH,#aB_Complete,#aB_QSH",true);
											setButtonState("#aTgmgrLink",true);	
											$('#FProgressId').combobox('disable');
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
									switch($("#FCurrentStep").val().toString()){
										case "10":
											setButtonState("#aT_BLQK,#aB_BLQK",true);	
											break;
										case "20":
											setButtonState("#aT_BLQK,#aB_BLQK",true);
											break;
										case "30":
											setButtonState("#aT_BLQK,#aB_BLQK",true);
											break;
										case "40":
											setButtonState("#aT_BLQK,#aB_BLQK",true);
											break;
									}
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
				}
				if($("#isAcceptance").val()=="1") {//已签收
					setButtonState("#aT_QSH,#aB_QSH",false);
				}
		  }		   

			function getRelatedDetpOpinion(id){
				$.post("/Buss/MMReportService/5",{"ID":formPKID},function(data){
					if (data.success) {
						var size=data.totalProperty;							
						 $.each( data.root, function(index, record){
							  CreateDivOtherDept(record.FId,record.FDeptId,record.FDeptName,record.FDeptOpinion,index,size,record.FUserId);								 
						 });
						 if($.browser.msie){
							 $("#content").height($("#content").height() +data.totalProperty*130);
						} else if($.browser.mozilla){
							 $("#content").height($("#content").height() +data.totalProperty*260);
						} 
						 
					} else {
						return false;
					}
				},"json");
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
						[{/*title:'类型',*/field:'FStyle',width:66,align:'center',
							formatter: function(value,row,index){
								if(value=="1")
									return "办理情况";
								else if(value=="2")
									return "催办情况";
								else if(value=="3")
									return "打回情况";
								else if(value=="-1")
									return "";
								else if(value=="-2")
									return "";
								else if(value=="-3")
									return "";
							}},
						{/*title:'发送人员',*/field:'sendUser',width:60,hidden:false},
						{/*title:'发送时间',*/field:'FSendTime',width:131,align:'center'},
						{/*title:'接收人员',*/field:'acceptUser',width:60,align:'center'},
						{/*title:'接收时间',*/field:'FAcceptTime',width:131,align:'center'},
						{/*title:'办理状态',*/field:'FStateText',width:70,align:'center'},
						{/*title:'完成时间',*/field:'completeTime',width:131,align:'center'},
						{/*title:'备注',*/field:'FRemark',width:150,align:'center'}						
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
						if(row.FStyle=="-1" || row.FStyle=="-2" || row.FStyle=="-3")
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
		function SetFormValue(data, type) {					
			$("#FId").attr("value", data.root[0].FId);
			//设置表单值
			$("#fkTaskId").attr("value",data.root[0].fkTaskId); 
			$("#FKTaskName").attr("value",data.root[0].FTaskName);
			$("#FCurrentStep").attr("value", data.root[0].FCurrentStep);
			$("#FLastStep").attr("value", data.root[0].FLastStep);
			$("#FDeptMgrId").attr("value",data.root[0].FDeptMgrId);
			if(data.root[0].FServiceCategory!=0)
			    $('#FServiceCategory').combotree('setValue', data.root[0].FServiceCategory);
			$('#FServiceCategory').combotree('disable');
					
			if(data.root[0].FEakerId==""){
				$("#FEakerId").attr("value",$("#userID").val());
				$("#FEakerName").attr("value",$("#userName").val());								
			}else{
				$("#FEakerId").attr("value",data.root[0].FEakerId);
				$("#FEakerName").attr("value",data.root[0].FEakerName);
			}
			
			$("#FNumbers").attr("value",data.root[0].FNumbers);
			$('#FProgress').val(data.root[0].FProgress);
			if(data.root[0].FProgressId!=0)
			    $('#FProgressId').combobox('setValue', data.root[0].FProgressId);
			$("#FMattersBriefly").attr("value",data.root[0].FMattersBriefly);
			$("#FApplicationSupport").attr("value",data.root[0].FApplicationSupport);
			$("#FQpopionion").attr("value",data.root[0].FQpopionion);
			$("#FDeptPrgOpinion").attr("value",data.root[0].FDeptPrgOpinion);
			$("#FTgmgrOpinion").attr("value",data.root[0].FTgmgrOpinion);
			setFormButtonState($("#unitStation").val().toString(),$("#action").val().toString(),$("#fState").val().toString());
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
			$("#FDepartmentName").attr({readonly : 'true'});
			$("#FEakerName").attr({readonly:'true'});
			$("#FDeptPrgOpinion").attr({readonly:'true'});
			$("#FTgmgrOpinion").attr({readonly:'true'});

			$("#FNumbers").attr({readonly : 'true'});
			$("#FKTaskName").attr({readonly:'true'});
			$("#FServiceCategory").attr({readonly:'true'});			
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
					if($("#FEakerId").val()==""){
						$("#FEakerId").attr("value",$("#userID").val());
						$("#FEakerName").attr("value",$("#userName").val());									
					}
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
		function ValidateUser(activeId){
			$.ajax({
				url:'/Buss/CommonService/1',
				type:'POST',
				data:{"activeId":activeId},
				dataType: 'json',
				timeout: 5000,
				error: fn_error,				
				success: fn_success
			});
		}
		
		$(document).ready(function() {			
			  ValidateUser(Request.QueryString("activeId"));			
			  //form post code
			  var options = {
								target : '#output2',
								beforeSubmit : showRequest,
								success : showResponse,
								url : '/Buss/MMReportService/2',
								type : 'post',
								dataType : 'json'
							};
							function showRequest(formData, jqForm, options) {
								//if (!$("#formreport").validationEngine('validate')) {
								//	return false;
								//}
							}
							// post-submit callback 
							function showResponse(responseText, statusText,
									xhr, $form) {
								if (statusText == "success") {
									if (responseText.success) {
										$("#FId").attr("value", responseText.Id);																				
										MessageInfo('系统提示', '重大事项报告,保存成功,<br/>下一步请提交到部门.');
										saveok=true;
									} else{
										MessageInfo('系统提示', '重大事项报告保存失败,请检查网络重试或联系系统管理员！');
										saveok=false;
									}
								}
				}			
				function FUN_Save() {
					var issave=false;
					issave=$("#FNumbers").validData("编号不能为空！",0,"",0);
					issave= issave && $("#FKTaskName").validData("任务不能为空！",0,"",0);
					issave= issave && $("#FProgressId").validData("项目实施进度不能为空！",1,"combotree",0);
					issave= issave && $("#FMattersBriefly").validData("事项简述不能为空！",0,"",1000);
					issave= issave && $("#FApplicationSupport").validData("申请的支持不能为空！",0,"",1000);
					issave= issave && $("#FQpopionion").validData("拟处理意见不能为空！",0,"",1000);
						if(issave)
						{
									var Id = $('input[name=FId]').fieldValue();
									$("#FEakerId").attr("value",$("#userID").val());
									$("#FEakerName").attr("value",$("#userName").val());
									if (Id == "" || Id != null) {									
										var form = $("#formreport");
										form.ajaxSubmit(options);
										return false;
									}
						}
					}
				function FUN_Post() {
					PostReport($('input[name=FId]').fieldValue());
				}							
				$("#aT_Post,#aB_Post").click(function() {
					//若部门填写完意见提交
					if($("#FCurrentStep").val()=="20"){
						//判断是否已签收
						if($("#isAcceptance").val()=="0")
						{
							$.messager.alert('系统提示','请先签收！再作其他操作','info');								 
							return false;
						}
						//判读部门意见是否已填写。
						if($("#FDeptPrgOpinion").val()==""){
							$.messager.alert('系统提示','请填写部门意见，再提交到相关部门。','info');								 
							return false;
						}
						FUN_Post();
					}else
					{
						if($("#FCurrentStep").val()=="10" || $("#action").val()=="0")
							{								
								if(!saveok){
									$.messager.alert('系统提示','请先保存后,再做此提交操作！。','info');								 
									return false;
								}
								if($("#FDeptMgrId").val()=="0")
								{
									$.messager.alert('系统提示','任务书还未下达成功。请选择任务书。','info');								 
									return false;
								}
								$.messager.confirm('系统提示', '您确定要提交到部门,征求意见吗？', function(r){
								    if (r){
										showLoadings();
										//根据项目所在部门，提交到部门经理
										$.post("/Buss/MMReportService/10", {
											"ProcessId" :  $("#processId").val(),
											"AcceptUserId" : $("#FDeptMgrId").val(),
											"AboveActId" :  $("#activeId").val(),
											"Remark" : "请部门经理填写意见",
											"formPKID": $("#formPKID").val()
										    }, function(data) {
												 if (data.success) {
													 setButtonState("#aT_OK,#aT_Post,#aT_PostGM,#aB_OK,#aB_Post,#aB_PostGM",false);
													 setButtonState("#aT_CuiBan,#aB_CuiBan",true);
													 hideLoadings();
													 MessageInfo('系统提示', '已成功提交部门。请注意办理情况。');
												 }else
												 {
													 hideLoadings();
													 MessageInfo('系统提示', '提交异常，请检查网络重试或联系系统管理员！');
													 return false;
											     }
										}, "json");
									}
									
								});
						}
						//相关部门意见都填写好后自动提交到总经理
					}
				});
				$("#aT_PostGM,#aB_PostGM").click(function(){
					//判断是否已签收
					if($("#isAcceptance").val()=="0")
					{
						$.messager.alert('系统提示','请先签收！再作其他操作','info');								 
						return false;
					}
					//判读部门意见是否已填写。
					if($("#FDeptPrgOpinion").val()==""){
						$.messager.alert('系统提示','请填写部门意见，再提交到总经理。','info');								 
						return false;
					}
					$.messager.confirm('系统提示', '您确定没有相关部门意见,直接提交到总经理吗？', function(r){
						if (r){
							showLoadings();
							//根据项目所在部门，提交到部门经理
							$.post("/Buss/MMReportService/12", {
								"ProcessId" :  $("#processId").val(),								
								"AboveActId" :  $("#activeId").val(),
								"Remark" : "请总经理填写意见",
								"formPKID": $("#formPKID").val()
							    }, function(data) {
									 if (data.success) {
										 setButtonState("#aT_OK,#aT_Post,#aT_PostGM,#aB_OK,#aB_Post,#aB_PostGM",false);
										 setButtonState("#aT_CuiBan,#aB_CuiBan",true);
										 hideLoadings();
										 MessageInfo('系统提示', '已成功提交总经理。请注意办理情况。');
									 }else
									 {
										 hideLoadings();
										 MessageInfo('系统提示', '提交异常，请检查网络重试或联系系统管理员！');
										 return false;
								     }
							}, "json");							
						}
					});
				});
				$("#aT_OK,#aB_OK").click(function() {
					FUN_Save();
				});
							 
				
				$("#aTgmgrLink").click(function() {
					var formId=$("#FId").val();					
					WriteOpinion(formId,'T_MajorMattersReport','F_TgmgrOpinion',1);
				});
				$("#aDeptLink").click(function() {
					var formId=$("#FId").val();					
					WriteOpinion(formId,'T_MajorMattersReport','F_DeptPrgOpinion',1);
				});
				$("#aT_BLQK,#aB_BLQK").click(function() {
					BLQK($("#processId").val());
				});
				$("#aT_CuiBan,#aB_CuiBan").click(function() {
					//BLQK($("#processId").val());							 
					$("#divcuiban").dialog('open');
					$("#divcuiban").dialog('center');
				});
				$("#aT_QSH,#aB_QSH").click(function() {					
					$.messager.confirm('系统提示', '您确定要签收吗？', function(r){
						if (r){
							var Id = $("#FId").val();
							$.post("/Buss/MMReportService/8", {
									"AboveActId" : activeId,
									"Remark":""
		   						}, function(data) {
		   							if (data.success){		   										   								
		   								setButtonState("#aT_QSH,#aB_QSH",false);		   								
										$("#isAcceptance").val("1");
										$.messager.alert('系统提示','签收成功。','info');
		   							}
		   							else
		   								$.messager.alert('系统提示','签收失败！请检查网络重试或联系系统管理员！','info');
							}, "json");							
						}
					});
				});
				$("#aT_Complete,#aB_Complete").click(function(){
					//判断是否已签收
					if($("#isAcceptance").val()=="0")
					{
						$.messager.alert('系统提示','请先签收！再作其他操作','info');								 
						return false;
					}
					var url="/Buss/MMReportService/7";
					//办理完成
					if($("#FCurrentStep").val()=="40")
					{
						url="/Buss/MMReportService/11";
						//判读总经理意见是否已填写。
						if($("#FTgmgrOpinion").val()==""){
							$.messager.alert('系统提示','请填写总经理意见。','info');								 
							return false;
						}
					}
					$.messager.confirm('系统提示', '您确认是否要办理完成?', function(r){
						if (r){
							showLoadings();
							$.post(url, {
								"ProcessId" :  Request.QueryString("processId"),											
								"AboveActId" :  Request.QueryString("activeId"),											
								"formPKID":  Request.QueryString("formPKID")							
								}, function(data) {
								if (data.success) {												
									$("#aT_Complete,#aB_Complete").hide();
									hideLoadings();
									MessageInfo('系统提示', '办理完成');
								}else
								{									
									hideLoadings();
									MessageInfo('系统提示', '办理完成失败，请检查网络重试或联系系统管理员！');	
									return false;
								}
							}, "json");										
						}
					 });
				});
				$("#getFXBNumbers").click(function() {					
					 if($("#FKTaskName").val()=="")
					 {
						 $.messager.alert('系统提示','任务名称不能为空!请选择任务。','error');
						 return true;
					 }					
				     $.post("/GetNumber/ForNumberRule/", {
						  "Code": "XB",
						  "Title": $("#FKTaskName").val(),
						  "isHaveSub": 0
					 }, function(data) {
						  if (data.success) {
								 $("#FNumbers").attr("value",data.Number);
								 $.messager.alert('系统提示','编号生成成功!','info');
						  }else
						  {															
							     $.messager.alert('系统提示','编号生成失败，请重试!','error');
								 return false;
						  }
					 }, "json");
				 });				  
		});	